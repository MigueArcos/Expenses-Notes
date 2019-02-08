package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.AccountEntry;
import com.migue.zeus.expensesnotes.data.models.AccountEntryCategory;
import com.migue.zeus.expensesnotes.data.models.AccountEntryDetail;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAccountEntryModel implements AddAccountEntryContract.Model {
    private AddAccountEntryContract.Presenter presenter;
    private AppDatabase database;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(MyUtils.DATE_FORMAT, Locale.US);

    public AddAccountEntryModel(AddAccountEntryContract.Presenter presenter) {
        this.presenter = presenter;
        database = AppDatabase.getInstance();
    }

    @Override
    public List<Account> getAccounts() {
        return database.accountsDao().getAllAccounts();
    }

    @Override
    public List<AccountEntryCategory> getAccountEntriesCategories() {
        return database.accountEntriesCategoriesDao().getAllAccountEntriesCategories();
    }

    @Override
    public String getAccountEntryTitle(AccountEntry accountEntry) {
        return accountEntry.getName();
    }

    @Override
    public String getAccountEntryDate(AccountEntry accountEntry) {
        return dateFormat.format(accountEntry.getDate());
    }

    @Override
    public String getAccountEntryDate() {
        return dateFormat.format(new Date());
    }

    @Override
    public long createAccountEntry(String name, String date, long expenseCategoryId, List<AccountEntryDetail> details) {
        AccountEntry accountEntry = new AccountEntry();
        try {
            accountEntry.setDate(dateFormat.parse(date));
            accountEntry.setName(name);
            accountEntry.setAccountEntryCategoryId(expenseCategoryId);
            long id = database.accountEntriesDao().insert(accountEntry);
            for (AccountEntryDetail detail : details) {
                detail.setAccountEntryId(id);
                database.accountEntriesDetailsDao().insert(detail);
            }
            return id;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateAccountEntry(AccountEntryWithDetails accountEntryWithDetails) {
        database.accountEntriesDao().update(accountEntryWithDetails.getAccountEntry());
        for (AccountEntryDetail accountEntryDetail : accountEntryWithDetails.getAccountEntryDetails()){
            database.accountEntriesDetailsDao().update(accountEntryDetail);
        }
    }
}
