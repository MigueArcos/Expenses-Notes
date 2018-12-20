package com.migue.zeus.expensesnotes.ui.main_activity.fragments.finances_fragment;

import com.migue.zeus.expensesnotes.data.models.Finance;

public class FinancesContract {
    public interface View {

    }
    public interface Model {
        int getItemCount();
        void bindHolderData(ItemView itemView, int position);
        void bindHeaderData(HeaderItemView header);
    }
    public interface Presenter {
        void bindHolderData(ItemView itemView, int position);
        int getItemCount();
        void bindHeaderData(HeaderItemView header);
    }
    public interface ItemView{
        void showName(String name);
        void showValue(double value);
        void showIcon(int iconId);
    }

    public interface HeaderItemView {
        void showTotal(double total);
        void showIcon(int iconId);
    }
}
