package com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment;

import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyFilter;

public class AccountEntriesPresenter implements AccountEntriesContract.Presenter {
    private AccountEntriesContract.Model model;
    private AccountEntriesContract.View view;
    public AccountEntriesPresenter(AccountEntriesContract.View view, boolean shouldShowExpenses) {
        this.view = view;
        model = new AccountEntriesModel(this, shouldShowExpenses);
    }


    @Override
    public <Model extends MyFilter> void bindHolderItem(AccountEntriesContract.HolderView<Model> holderView, int position) {
        model.bindHolderItem(holderView, position);
    }

    @Override
    public int getItemCount() {
        return model.getItemCount();
    }


    @Override
    public int getViewType(int position) {
        return model.getViewType(position);
    }

    @Override
    public void reloadItems(int month, int year) {
        model.reloadItems(month, year);
        view.onItemsReloaded();
    }

    @Override
    public void onItemClick(int position) {
        AccountEntryWithDetails accountEntryWithDetails = model.getAccountEntry(position);
        view.onItemClick(position, accountEntryWithDetails);
    }

    @Override
    public void onItemLongClick(int position) {
        AccountEntryWithDetails accountEntryWithDetails = model.getAccountEntry(position);
        view.onItemLongClick(position, accountEntryWithDetails);
    }

    @Override
    public void deleteAccountEntry(int position, AccountEntryWithDetails entryWithDetails) {
        model.deleteAccountEntry(position, entryWithDetails);
        view.notifyDataChanged();
    }

}
