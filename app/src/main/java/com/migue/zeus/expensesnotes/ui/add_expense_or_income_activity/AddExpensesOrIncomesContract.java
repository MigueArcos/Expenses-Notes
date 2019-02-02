package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseCategory;
import com.migue.zeus.expensesnotes.data.models.ExpenseDetail;
import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;

import java.util.List;

public class AddExpensesOrIncomesContract {
    interface View{
       void showCategories(List<ExpenseCategory> expenseCategories);
       void showAccounts(List<Account> accounts);
       void showTitle(String title);
       void showDate(String date);
       void notifyExpenseCreated(long id);
    }
    interface Model {
        List<Account> getAccounts();
        List<ExpenseCategory> getExpenseCategories();
        String getExpenseTitle(Expense expense);
        String getExpenseDate(Expense expense);
        String getExpenseDate();
        long createExpense(String name, String date, long expenseCategoryId, List<ExpenseDetail> details);
        void updateExpense(ExpenseWithDetails expenseWithDetails);
    }
    interface Presenter{
        void getAccounts();
        void getExpenseCategories();
        void getExpenseTitle(Expense expense);
        void getExpenseDate(Expense expense);
        void getExpenseDate();
        void createExpense(String name, String date, long expenseCategoryId, List<ExpenseDetail> details);
        void updateExpense(ExpenseWithDetails expenseWithDetails);
    }
}
