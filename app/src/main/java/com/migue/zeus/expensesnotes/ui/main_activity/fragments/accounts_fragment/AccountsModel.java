package com.migue.zeus.expensesnotes.ui.main_activity.fragments.accounts_fragment;

import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.Finance;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountsDao;

import java.util.List;

public class AccountsModel implements AccountsContract.Model {
    private AccountsContract.Presenter presenter;
    private final List<Account> accountsList;
    private AccountsDao accountsDao = AppDatabase.getInstance().accountsDao();

    public AccountsModel(AccountsContract.Presenter presenter) {
        this.presenter = presenter;
        accountsList = accountsDao.getAllAccounts();
    }

    @Override
    public int getItemCount() {
        return accountsList.size() + 1;
    }

    @Override
    public void bindHolderData(AccountsContract.ItemHolderView itemHolderView, int position) {
        itemHolderView.showIcon(Finance.iconId);
        itemHolderView.showName(accountsList.get(position).getName());
        itemHolderView.showValue(accountsList.get(position).getValue());
    }

    @Override
    public void bindHeaderData(AccountsContract.HeaderHolderView header) {
        header.showIcon(Finance.iconId);
        header.showTotal(accountsDao.getTotal());
    }
}
