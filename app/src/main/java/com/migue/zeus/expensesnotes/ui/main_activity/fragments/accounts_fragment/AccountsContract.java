package com.migue.zeus.expensesnotes.ui.main_activity.fragments.accounts_fragment;

public class AccountsContract {
    public interface View {

    }
    public interface Model {
        int getItemCount();
        void bindHolderData(ItemHolderView itemHolderView, int position);
        void bindHeaderData(HeaderHolderView header);
    }
    public interface Presenter {
        void bindHolderData(ItemHolderView itemHolderView, int position);
        int getItemCount();
        void bindHeaderData(HeaderHolderView header);
    }
    public interface ItemHolderView {
        void showName(String name);
        void showValue(double value);
        void showIcon(int iconId);
    }

    public interface HeaderHolderView {
        void showTotal(double total);
        void showIcon(int iconId);
    }
}
