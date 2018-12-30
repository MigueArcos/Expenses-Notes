package com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment;


import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;
import com.migue.zeus.expensesnotes.data.models.Finance;

import java.util.Date;
import java.util.List;

public class ExpensesContract {
    public interface View {

    }
    public interface Model {
        int getItemCount();
        void bindHolderData(ItemView itemView, int position);
        void bindHeaderData(HeaderItemView header, int position);
        void bindMainHeaderItemView(MainHeaderItemView mainHeaderItemView);
        int getViewType(int position);
    }
    public interface Presenter {
        void bindHolderData(ItemView itemView, int position);
        int getItemCount();
        void bindHeaderData(HeaderItemView header, int position);
        void bindMainHeaderData(MainHeaderItemView mainHeaderItemView);
        int getViewType(int position);
    }
    public interface ItemView{
        void renderItem(ExpenseWithDetails expense);
    }

    public interface HeaderItemView {
        void showDate(Date date);
        void showTotal(double total);
    }

    public interface MainHeaderItemView {
        void showTotal(double total);
        void showIcon(int iconId);
    }
}
