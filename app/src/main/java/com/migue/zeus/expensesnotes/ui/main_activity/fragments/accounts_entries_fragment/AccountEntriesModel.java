package com.migue.zeus.expensesnotes.ui.main_activity.fragments.accounts_entries_fragment;


import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountEntriesDao;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountEntriesModel implements AccountEntriesContract.Model {
    private AccountEntriesContract.Presenter presenter;
    private final List<Date> accountEntriesDates;
    //Data to be presented in recyclerView
    private final List<ItemView> items;
    private AccountEntriesDao accountEntriesDao = AppDatabase.getInstance().accountEntriesDao();
    public static final int MAIN_HEADER_VIEW = 1;
    public static final int HEADER_VIEW = 2;
    public static final int ITEM_VIEW = 3;

    public AccountEntriesModel(AccountEntriesContract.Presenter presenter) {
        this.presenter = presenter;
        accountEntriesDates = new ArrayList<>();
        items = new ArrayList<>();
        loadAccountEntries();
    }

    private void loadAccountEntries() {
        accountEntriesDates.clear();
        accountEntriesDates.addAll(accountEntriesDao.getAccountEntriesDates());
        //items is a list containing all the data that is shown in recyclerView (3 distinct type of objects)
        items.clear();
        double total = 0;
        MainHeaderItemView mainHeader = new MainHeaderItemView();
        items.add(mainHeader);
        for (Date date : accountEntriesDates) {
            double subTotal = 0;
            HeaderItemView header = new HeaderItemView();
            items.add(header);
            for (AccountEntryWithDetails accountEntryWithDetails : accountEntriesDao.getAccountEntriesByDate(date)) {
                items.add(new AccountEntryView(accountEntryWithDetails));
                subTotal += accountEntryWithDetails.getTotal();
            }
            header.getItem().setTotal(subTotal);
            header.getItem().setDate(date);
            total += subTotal;
        }
        mainHeader.getItem().setTotal(total);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public <Model extends MyFilter> void bindHolderItem(AccountEntriesContract.HolderView<Model> holderView, int position) {
        holderView.renderItem((Model) items.get(position).getItem());
    }


    @Override
    public int getViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public void reloadItems() {
        loadAccountEntries();
    }

    @Override
    public AccountEntryWithDetails onItemClick(int position) {
        return ((AccountEntryView) items.get(position)).getItem().getAccountEntry();
    }

    public abstract class ItemView<Model extends MyFilter> {
        protected Model item;

        abstract int getViewType();

        public Model getItem() {
            return item;
        }
    }

    public class MainHeaderModel implements MyFilter {
        private double total;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        @Override
        public boolean passFilter(String filter) {
            return true;
        }
    }

    public class MainHeaderItemView extends ItemView<MainHeaderModel> {
        MainHeaderItemView() {
            item = new MainHeaderModel();
        }

        @Override
        int getViewType() {
            return MAIN_HEADER_VIEW;
        }
    }

    public class HeaderModel implements MyFilter {
        private Date date;
        private double total;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        @Override
        public boolean passFilter(String filter) {
            return true;
        }
    }

    public class HeaderItemView extends ItemView<HeaderModel> {
        HeaderItemView() {
            item = new HeaderModel();
        }

        @Override
        int getViewType() {
            return HEADER_VIEW;
        }
    }

    public class ItemModel implements MyFilter {
        private AccountEntryWithDetails accountEntryWithDetails;

        public ItemModel(AccountEntryWithDetails accountEntryWithDetails) {
            this.accountEntryWithDetails = accountEntryWithDetails;
        }

        public AccountEntryWithDetails getAccountEntry() {
            return accountEntryWithDetails;
        }

        @Override
        public boolean passFilter(String filter) {
            return accountEntryWithDetails.getAccountEntry().getName().contains(filter);
        }
    }

    public class AccountEntryView extends ItemView<ItemModel> {
        AccountEntryView(AccountEntryWithDetails accountEntryWithDetails) {
            item = new ItemModel(accountEntryWithDetails);
        }

        @Override
        int getViewType() {
            return ITEM_VIEW;
        }
    }
}

