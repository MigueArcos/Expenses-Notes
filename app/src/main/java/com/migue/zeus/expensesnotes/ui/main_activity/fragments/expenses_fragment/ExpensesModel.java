package com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment;


import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.infrastructure.dao.ExpensesDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpensesModel implements ExpensesContract.Model {
    private ExpensesContract.Presenter presenter;
    private final List<Date> expensesDates;
    //Data to be presented in recyclerView
    private final List<ListItem> items;
    private ExpensesDao expensesDao = AppDatabase.getInstance().getExpensesDao();
    public static final int MAIN_HEADER_VIEW = 1;
    public static final int HEADER_VIEW = 2;
    public static final int ITEM_VIEW = 3;
    public ExpensesModel(ExpensesContract.Presenter presenter) {
        this.presenter = presenter;
        expensesDates =  expensesDao.getExpensesDates();
        //items is a list containing all the data that is shown in recyclerView (3 distinct type of objects)
        items = new ArrayList<>();
        double total = 0;
        MainHeaderItemData mainHeader = new MainHeaderItemData();
        items.add(mainHeader);
        for (Date date : expensesDates){
            double subTotal = 0;
            HeaderItemData header = new HeaderItemData();
            items.add(header);
            for (Expense expense : expensesDao.getExpensesByDate(date)){
                items.add(new ItemData(expense));
                subTotal += expense.getValue();
            }
            header.setTotal(subTotal);
            header.setDate(date);
            total += subTotal;
        }
        mainHeader.setTotal(total);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void bindHolderData(ExpensesContract.ItemView itemView, int position) {
        itemView.renderItem(((ItemData) items.get(position)).getExpense());
    }

    @Override
    public void bindHeaderData(ExpensesContract.HeaderItemView header, int position) {
        HeaderItemData dataItem = (HeaderItemData) items.get(position);
        header.showTotal(dataItem.getTotal());
        header.showDate(dataItem.getDate());
    }

    @Override
    public void bindMainHeaderItemView(ExpensesContract.MainHeaderItemView mainHeaderItemView) {
        mainHeaderItemView.showTotal(((MainHeaderItemData)items.get(0)).getTotal());
    }

    @Override
    public int getViewType(int position) {
        return items.get(position).getViewType();
    }

    //These classes are used to manage the data used in the recyclerView, since this recycler has 3 distinct view types
    public interface ListItem{
        int getViewType();
    }

    public class ItemData implements ListItem{
        private Expense expense;

        public Expense getExpense() {
            return expense;
        }

        public void setExpense(Expense expense) {
            this.expense = expense;
        }

        public ItemData(Expense expense) {
            this.expense = expense;
        }

        @Override
        public int getViewType() {
            return ITEM_VIEW;
        }
    }
    public class HeaderItemData implements ListItem{
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
        public int getViewType() {
            return HEADER_VIEW;
        }
    }
    public class MainHeaderItemData implements ListItem{
        private double total;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        @Override
        public int getViewType() {
            return MAIN_HEADER_VIEW;
        }
    }
}
