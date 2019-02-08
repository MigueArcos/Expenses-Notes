package com.migue.zeus.expensesnotes.ui.main_activity.fragments.accounts_entries_fragment;

import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyFilter;

public class AccountEntriesPresenter implements AccountEntriesContract.Presenter {
    private AccountEntriesContract.Model model;
    private AccountEntriesContract.View view;

    public AccountEntriesPresenter(AccountEntriesContract.View view) {
        this.view = view;
        model = new AccountEntriesModel(this);
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
    public void reloadItems() {
        model.reloadItems();
        view.onItemsReloaded();
    }

    @Override
    public void onItemClick(int position) {
        AccountEntryWithDetails accountEntryWithDetails = model.onItemClick(position);
        view.onItemClick(position, accountEntryWithDetails);
    }
}
