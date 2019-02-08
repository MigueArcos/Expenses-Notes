package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.AccountEntry;
import com.migue.zeus.expensesnotes.data.models.AccountEntryCategory;
import com.migue.zeus.expensesnotes.data.models.AccountEntryDetail;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;

import java.util.List;

public class AddAccountEntryContract {
    interface View{
       void showCategories(List<AccountEntryCategory> accountEntryCategories);
       void showAccounts(List<Account> accounts);
       void showTitle(String title);
       void showDate(String date);
       void notifyAccountEntryCreated(long id);
    }
    interface Model {
        List<Account> getAccounts();
        List<AccountEntryCategory> getAccountEntriesCategories();
        String getAccountEntryTitle(AccountEntry accountEntry);
        String getAccountEntryDate(AccountEntry accountEntry);
        String getAccountEntryDate();
        long createAccountEntry(String name, String date, long expenseCategoryId, List<AccountEntryDetail> details);
        void updateAccountEntry(AccountEntryWithDetails accountEntryWithDetails);
    }
    interface Presenter{
        void getAccounts();
        void getAccountEntryCategories();
        void getAccountEntryTitle(AccountEntry accountEntry);
        void getAccountEntryDate(AccountEntry accountEntry);
        void getAccountEntryDate();
        void createAccountEntry(String name, String date, long expenseCategoryId, List<AccountEntryDetail> details);
        void updateAccountEntry(AccountEntryWithDetails accountEntryWithDetails);
    }
}
