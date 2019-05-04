package com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment;


import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyFilter;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;

public class AccountEntriesContract {

    public interface View {
        void onItemsReloaded();

        void onItemClick(int position, AccountEntryWithDetails accountEntryWithDetails);

        void onItemLongClick(int position, AccountEntryWithDetails accountEntryWithDetails);

        void notifyItemDeleted(int position);

        void notifyItemChanged(int position);
        void notifyDataChanged();
    }

    public interface Model {
        int getItemCount();

        <T extends MyFilter>  void bindHolderItem(HolderView<T> holderView, int position);

        int getViewType(int position);

        void reloadItems(int month, int year);

        AccountEntryWithDetails getAccountEntry(int position);

        void deleteAccountEntry(int position, AccountEntryWithDetails entryWithDetails);

    }

    public interface Presenter {
        <Model extends MyFilter>  void bindHolderItem(HolderView<Model> holderView, int position);

        int getItemCount();

        int getViewType(int position);

        void reloadItems(int month, int year);

        void onItemClick(int position);

        void onItemLongClick(int position);

        void deleteAccountEntry(int position, AccountEntryWithDetails entryWithDetails);

    }

    public interface HolderView<Model extends MyFilter> {
        void renderItem(Model model);
    }

}
