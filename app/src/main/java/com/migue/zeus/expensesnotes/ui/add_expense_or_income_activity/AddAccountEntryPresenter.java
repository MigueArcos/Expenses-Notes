package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import com.migue.zeus.expensesnotes.data.models.AccountEntry;
import com.migue.zeus.expensesnotes.data.models.AccountEntryDetail;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;

import java.util.List;

public class AddAccountEntryPresenter implements AddAccountEntryContract.Presenter {
    private AddAccountEntryContract.View view;
    private AddAccountEntryContract.Model model;

    public AddAccountEntryPresenter(AddAccountEntryContract.View view) {
        this.view = view;
        model = new AddAccountEntryModel(this);
    }

    @Override
    public void getAccounts() {
        view.showAccounts(model.getAccounts());
    }

    @Override
    public void getAccountEntryCategories() {
        view.showCategories(model.getAccountEntriesCategories());
    }

    @Override
    public void getAccountEntryTitle(AccountEntry accountEntry) {
        view.showTitle(model.getAccountEntryTitle(accountEntry));
    }

    @Override
    public void getAccountEntryDate(AccountEntry accountEntry) {
        view.showDate(model.getAccountEntryDate(accountEntry));
    }

    @Override
    public void getAccountEntryDate() {
        view.showDate(model.getAccountEntryDate());
    }

    @Override
    public void createAccountEntry(String name, String date, long expenseCategoryId, List<AccountEntryDetail> details) {
        long id = model.createAccountEntry(name, date, expenseCategoryId, details);
        view.notifyAccountEntryCreated(id);
    }

    @Override
    public void updateAccountEntry(AccountEntryWithDetails accountEntryWithDetails) {
        model.updateAccountEntry(accountEntryWithDetails);
        view.notifyAccountEntryCreated(accountEntryWithDetails.getAccountEntry().getId());
    }
}
