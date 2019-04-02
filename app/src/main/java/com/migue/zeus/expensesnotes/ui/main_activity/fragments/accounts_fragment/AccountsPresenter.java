package com.migue.zeus.expensesnotes.ui.main_activity.fragments.accounts_fragment;

public class AccountsPresenter implements AccountsContract.Presenter{
    private AccountsContract.Model model;
    private AccountsContract.View view;
    public AccountsPresenter(AccountsContract.View view) {
        this.view = view;
        model = new AccountsModel(this);
    }

    @Override
    public void bindHolderData(AccountsContract.ItemHolderView itemHolderView, int position) {
        model.bindHolderData(itemHolderView, position);
    }

    @Override
    public int getItemCount() {
        return model.getItemCount();
    }

    @Override
    public void bindHeaderData(AccountsContract.HeaderHolderView header) {
        model.bindHeaderData(header);
    }
}
