package com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment;


import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;

import java.util.Date;

public class ExpensesContract {

    public interface View {
        void onItemsReloaded();
        void onItemClick(int position, ExpenseWithDetails expenseWithDetails);
    }

    public interface Model {
        int getItemCount();

        void bindHolderData(ItemView itemView, int position);

        void bindHeaderData(HeaderItemView header, int position);

        void bindMainHeaderItemView(MainHeaderItemView mainHeaderItemView);

        int getViewType(int position);

        void reloadItems();

        ExpenseWithDetails onItemClick(int position);
    }

    public interface Presenter {
        void bindHolderData(ItemView itemView, int position);

        int getItemCount();

        void bindHeaderData(HeaderItemView header, int position);

        void bindMainHeaderData(MainHeaderItemView mainHeaderItemView);

        int getViewType(int position);

        void reloadItems();

        void onItemClick(int position);
    }

    public interface ItemView {
        void renderItem(ExpenseWithDetails expense);
    }

    public interface HeaderItemView {
        void showDate(Date date);

        void showTotal(String total);
    }

    public interface MainHeaderItemView {
        void showTotal(String total);

        void showIcon(int iconId);
    }
}
