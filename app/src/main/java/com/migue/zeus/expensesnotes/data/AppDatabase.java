package com.migue.zeus.expensesnotes.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseCategory;
import com.migue.zeus.expensesnotes.data.models.Finance;
import com.migue.zeus.expensesnotes.data.models.Icon;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountsDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.ExpensesCategoriesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.ExpensesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.FinancesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.IconsDao;
import com.migue.zeus.expensesnotes.infrastructure.utils.Converters;


@Database(entities = {Expense.class, Account.class, Icon.class, ExpenseCategory.class, Finance.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase Instance;

    public abstract ExpensesDao getExpensesDao();

    public abstract AccountsDao getAccountsDao();

    public abstract FinancesDao getFinancesDao();

    public abstract ExpensesCategoriesDao expensesCategoriesDao();

    public abstract IconsDao iconsDao();

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
                "AppDatabase.db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        PopulateDbAsync task = new PopulateDbAsync(Instance, ExpenseCategory.populateData(context), Icon.populateData(context), Finance.populateData(), Expense.populateData());
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
        PopulateDbAsync(AppDatabase db, ExpenseCategory[] categories, Icon[] icons, Finance[] finances, Expense[] expenses) {
            mDb = db;
            this.categories = categories;
            this.icons = icons;
            this.finances = finances;
            this.expenses = expenses;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDb.expensesCategoriesDao().insert(categories);
            mDb.iconsDao().insert(icons);
            mDb.getFinancesDao().insert(finances);
            mDb.getExpensesDao().insert(expenses);
            return null;
        }
    }

}
