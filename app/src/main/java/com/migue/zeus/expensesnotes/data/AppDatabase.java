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
import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseCategory;
import com.migue.zeus.expensesnotes.data.models.ExpenseDetail;
import com.migue.zeus.expensesnotes.data.models.Finance;
import com.migue.zeus.expensesnotes.data.models.Icon;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountsDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.ExpensesCategoriesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.ExpensesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.ExpensesDetailsDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.FinancesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.IconsDao;
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters;


@Database(
        entities = {Expense.class, Account.class, Icon.class, ExpenseCategory.class, Finance.class, ExpenseDetail.class},
        version = 1,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + "/ExpensesNotes.db";

    private static AppDatabase Instance;

    public abstract ExpensesDao expensesDao();

    public abstract AccountsDao accountsDao();

    public abstract FinancesDao financesDao();

    public abstract ExpensesCategoriesDao expensesCategoriesDao();

    public abstract IconsDao iconsDao();

    public abstract ExpensesDetailsDao expensesDetailsDao();

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

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                PATH)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        PopulateDbAsync task = new PopulateDbAsync(Instance, Icon.Companion.populateData(context), Account.Companion.populateData(), ExpenseCategory.Companion.populateData(context), Finance.populateData(), Expense.Companion.populateData(), ExpenseDetail.Companion.populateData());
                        task.execute();
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final Expense[] expenses;
        private final ExpenseCategory[] categories;
        private final Icon[] icons;
        private final Finance[] finances;
        private final ExpenseDetail[] expenseDetails;
        private final Account[] accounts;
        PopulateDbAsync(AppDatabase db, Icon[] icons, Account[] accounts,  ExpenseCategory[] categories, Finance[] finances, Expense[] expenses, ExpenseDetail[] expenseDetails) {
            mDb = db;
            this.icons = icons;
            this.categories = categories;
            this.finances = finances;
            this.expenses = expenses;
            this.expenseDetails = expenseDetails;
            this.accounts = accounts;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDb.iconsDao().insert(icons);
            mDb.accountsDao().insert(accounts);
            mDb.expensesCategoriesDao().insert(categories);
            mDb.financesDao().insert(finances);
            mDb.expensesDao().insert(expenses);
            mDb.expensesDetailsDao().insert(expenseDetails);
            return null;
        }
    }

}
