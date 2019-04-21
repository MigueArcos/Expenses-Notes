package com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment;


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
    private int shouldShowExpenses;
    public AccountEntriesModel(AccountEntriesContract.Presenter presenter, boolean shouldShowExpenses) {
        this.presenter = presenter;
        this.shouldShowExpenses = shouldShowExpenses ? -1 : 1;
        accountEntriesDates = new ArrayList<>();
        items = new ArrayList<>();
        loadAccountEntries();
    }

    private void loadAccountEntries() {
        accountEntriesDates.clear();
        accountEntriesDates.addAll(accountEntriesDao.getAccountEntriesDates(shouldShowExpenses));
        //items is a list containing all the data that is shown in recyclerView (3 distinct type of objects)
        items.clear();
        double total = 0;
        MainHeaderItemView mainHeader = new MainHeaderItemView();
        items.add(mainHeader);
        for (Date date : accountEntriesDates) {
            double subTotal = 0;
            HeaderItemView header = new HeaderItemView();
            items.add(header);
            int subPosition = 0;
            for (AccountEntryWithDetails accountEntryWithDetails : accountEntriesDao.getAccountEntriesByDate(date, shouldShowExpenses)) {
                items.add(new AccountEntryView(accountEntryWithDetails, ++subPosition));
                subTotal += accountEntryWithDetails.getTotal();
            }
            header.getItem().setTotal(subTotal);
            header.getItem().setDate(date);
            header.getItem().setChildsNumber(subPosition);
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
    public AccountEntryWithDetails getAccountEntry(int position) {
        return ((AccountEntryView) items.get(position)).getItem().getAccountEntry();
    }

    @Override
    public void deleteAccountEntry(int position, AccountEntryWithDetails entryWithDetails) {
        int subPosition = ((AccountEntryView) items.get(position)).getSubPosition();
        int itemChangedPosition = position - subPosition;
        HeaderModel parentEntry = ((HeaderItemView) items.get(itemChangedPosition)).getItem();
        parentEntry.setTotal(parentEntry.getTotal() - entryWithDetails.getTotal());

        MainHeaderModel mainHeaderEntry = ((MainHeaderItemView) items.get(0)).getItem();
        mainHeaderEntry.setTotal(mainHeaderEntry.getTotal() - entryWithDetails.getTotal());
        accountEntriesDao.delete(entryWithDetails.getAccountEntry());
        items.remove(position);

        //Re-Enumerate child's indexes
        parentEntry.setChildsNumber(parentEntry.getChildsNumber() - 1);
        if (parentEntry.getChildsNumber() == 0){
            items.remove(itemChangedPosition);
        }else{
            int newSubPosition = 0;
            int topIndex = itemChangedPosition + 1 + parentEntry.getChildsNumber();
            for (int i = itemChangedPosition + 1; i < topIndex; i++){
                ((AccountEntryView) items.get(i)).setSubPosition(++newSubPosition);
            }
        }
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

        private int childsNumber;
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
        public int getChildsNumber() {
            return childsNumber;
        }

        public void setChildsNumber(int childsNumber) {
            this.childsNumber = childsNumber;
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
        private int subPosition;
        AccountEntryView(AccountEntryWithDetails accountEntryWithDetails, int subPosition) {
            item = new ItemModel(accountEntryWithDetails);
            this.subPosition = subPosition;
        }
        public int getSubPosition() {
            return subPosition;
        }
        @Override
        int getViewType() {
            return ITEM_VIEW;
        }

        public void setSubPosition(int subPosition) {
            this.subPosition = subPosition;
        }
    }
}

