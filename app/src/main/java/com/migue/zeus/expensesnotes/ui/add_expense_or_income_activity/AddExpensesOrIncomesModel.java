package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseCategory;
import com.migue.zeus.expensesnotes.data.models.ExpenseDetail;
import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddExpensesOrIncomesModel implements AddExpensesOrIncomesContract.Model {
    private AddExpensesOrIncomesContract.Presenter presenter;
    private AppDatabase database;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(MyUtils.DATE_FORMAT, Locale.US);

    public AddExpensesOrIncomesModel(AddExpensesOrIncomesContract.Presenter presenter) {
        this.presenter = presenter;
        database = AppDatabase.getInstance();
    }

    @Override
    public List<Account> getAccounts() {
        return database.accountsDao().getAllAccounts();
    }

    @Override
    public List<ExpenseCategory> getExpenseCategories() {
        return database.expensesCategoriesDao().getAllExpenseCategories();
    }

    @Override
    public String getExpenseTitle(Expense expense) {
        return expense.getName();
    }

    @Override
    public String getExpenseDate(Expense expense) {
        return dateFormat.format(expense.getDate());
    }

    @Override
    public String getExpenseDate() {
        return dateFormat.format(new Date());
    }

    @Override
    public long createExpense(String name, String date, long expenseCategoryId, List<ExpenseDetail> details) {
        Expense expense = new Expense();
        try {
            expense.setDate(dateFormat.parse(date));
            expense.setName(name);
            expense.setExpenseCategoryId(expenseCategoryId);
            long id = database.expensesDao().insert(expense);
            for (ExpenseDetail detail : details) {
                detail.setExpenseId(id);
                database.expensesDetailsDao().insert(detail);
            }
            return id;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateExpense(ExpenseWithDetails expenseWithDetails) {
        database.expensesDao().update(expenseWithDetails.getExpense());
        for (ExpenseDetail expenseDetail : expenseWithDetails.getExpenseDetails()){
            database.expensesDetailsDao().update(expenseDetail);
        }
    }
}
