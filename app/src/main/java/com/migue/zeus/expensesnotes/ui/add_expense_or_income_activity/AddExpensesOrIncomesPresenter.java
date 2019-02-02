package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseDetail;
import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;

import java.util.List;

public class AddExpensesOrIncomesPresenter implements AddExpensesOrIncomesContract.Presenter {
    private AddExpensesOrIncomesContract.View view;
    private AddExpensesOrIncomesContract.Model model;

    public AddExpensesOrIncomesPresenter(AddExpensesOrIncomesContract.View view) {
        this.view = view;
        model = new AddExpensesOrIncomesModel(this);
    }

    @Override
    public void getAccounts() {
        view.showAccounts(model.getAccounts());
    }

    @Override
    public void getExpenseCategories() {
        view.showCategories(model.getExpenseCategories());
    }

    @Override
    public void getExpenseTitle(Expense expense) {
        view.showTitle(model.getExpenseTitle(expense));
    }

    @Override
    public void getExpenseDate(Expense expense) {
        view.showDate(model.getExpenseDate(expense));
    }

    @Override
    public void getExpenseDate() {
        view.showDate(model.getExpenseDate());
    }

    @Override
    public void createExpense(String name, String date, long expenseCategoryId, List<ExpenseDetail> details) {
        long id = model.createExpense(name, date, expenseCategoryId, details);
        view.notifyExpenseCreated(id);
    }

    @Override
    public void updateExpense(ExpenseWithDetails expenseWithDetails) {
        model.updateExpense(expenseWithDetails);
        view.notifyExpenseCreated(expenseWithDetails.getExpense().getId());
    }
}
