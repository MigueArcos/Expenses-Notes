package com.migue.zeus.expensesnotes.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.AccountEntry;
import com.migue.zeus.expensesnotes.data.models.AccountEntryCategory;
import com.migue.zeus.expensesnotes.data.models.AccountEntryDetail;
import com.migue.zeus.expensesnotes.data.models.Finance;
import com.migue.zeus.expensesnotes.data.models.Icon;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountEntriesCategoriesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountEntriesDetailsDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountsDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountEntriesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.FinancesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.IconsDao;
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters;


@Database(
        entities = {AccountEntry.class, Account.class, Icon.class, AccountEntryCategory.class, Finance.class, AccountEntryDetail.class},
        version = 1,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/ExpensesNotes.db";

    private static AppDatabase Instance;

    public abstract AccountEntriesDao accountEntriesDao();

    public abstract AccountsDao accountsDao();

    public abstract FinancesDao financesDao();

    public abstract AccountEntriesCategoriesDao accountEntriesCategoriesDao();

    public abstract IconsDao iconsDao();

    public abstract AccountEntriesDetailsDao accountEntriesDetailsDao();

    private Context appContext;

    public synchronized static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = buildDatabase(context.getApplicationContext());
        }
        return Instance;
    }

    public synchronized static AppDatabase getInstance() {
        return Instance;
    }
    /*It is possible to use a single trigger to manage account entries details modifications in this way:
    value = value - old.value*(IsExpense or Income) + new.value*(IsExpense or Income)*/
    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                PATH)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        db.execSQL("CREATE TRIGGER UpdateAccountOnAccountEntryDetailInsertion\n" +
                                "AFTER INSERT ON AccountEntriesDetails\n" +
                                "BEGIN\n" +
                                "    UPDATE Accounts SET Value = Value + (new.Value * (SELECT Revenue FROM AccountEntries WHERE Id = new.AccountEntryId)) WHERE Id = new.AccountId;\n" +
                                "END;");
                        db.execSQL("CREATE TRIGGER UpdateAccountOnExpenseModification\n" +
                                "AFTER UPDATE ON AccountEntriesDetails\n" +
                                "WHEN (SELECT Revenue FROM AccountEntries WHERE Id = old.AccountEntryId) = -1" +
                                "BEGIN\n" +
                                "    UPDATE Accounts SET Value = Value + old.Value - new.Value WHERE Id = new.AccountId;\n" +
                                "END;");
                        db.execSQL("CREATE TRIGGER UpdateAccountOnIncomeModification\n" +
                                "AFTER UPDATE ON AccountEntriesDetails\n" +
                                "WHEN (SELECT Revenue FROM AccountEntries WHERE Id = old.AccountEntryId) = 1" +
                                "BEGIN\n" +
                                "    UPDATE Accounts SET Value = Value - old.Value + new.Value WHERE Id = new.AccountId;\n" +
                                "END;");
                        db.execSQL("CREATE TRIGGER UpdateAccountOnAccountEntryDetailDeletion\n" +
                                "BEFORE DELETE ON AccountEntriesDetails\n" +
                                "BEGIN\n" +
                                "    UPDATE Accounts SET Value = Value + old.Value WHERE Id = old.AccountId;\n" +
                                "END;");
                        db.execSQL("CREATE TRIGGER UpdateAccountOnAccountEntryDeletion\n" +
                                "BEFORE DELETE ON AccountEntries\n" +
                                "BEGIN\n" +
                                "    DELETE FROM AccountEntriesDetails WHERE AccountEntryId = old.Id;\n" +
                                "END;");
                        PopulateDbAsync task = new PopulateDbAsync(Instance, Icon.Companion.populateData(context), Account.CREATOR.populateData(), AccountEntryCategory.CREATOR.populateData(context), Finance.populateData(), AccountEntry.CREATOR.populateData(), AccountEntryDetail.CREATOR.populateData());
                        task.execute();
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final AccountEntry[] accountEntries;
        private final AccountEntryCategory[] categories;
        private final Icon[] icons;
        private final Finance[] finances;
        private final AccountEntryDetail[] accountEntriesDetails;
        private final Account[] accounts;
        PopulateDbAsync(AppDatabase db, Icon[] icons, Account[] accounts, AccountEntryCategory[] categories, Finance[] finances, AccountEntry[] accountEntries, AccountEntryDetail[] accountEntriesDetails) {
            mDb = db;
            this.icons = icons;
            this.categories = categories;
            this.finances = finances;
            this.accountEntries = accountEntries;
            this.accountEntriesDetails = accountEntriesDetails;
            this.accounts = accounts;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDb.iconsDao().insert(icons);
            mDb.accountsDao().insert(accounts);
            mDb.accountEntriesCategoriesDao().insert(categories);
            mDb.financesDao().insert(finances);
            mDb.accountEntriesDao().insert(accountEntries);
            mDb.accountEntriesDetailsDao().insert(accountEntriesDetails);
            return null;
        }
    }

}
