package com.migue.zeus.expensesnotes.ui.main_activity.fragments.accounts_entries_fragment;


import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyFilter;

public class AccountEntriesContract {

    public interface View {
        void onItemsReloaded();
        void onItemClick(int position, AccountEntryWithDetails accountEntryWithDetails);
    }

    public interface Model {
        int getItemCount();

        <Model extends MyFilter> void bindHolderItem(HolderView<Model> holderView, int position);

        int getViewType(int position);

        void reloadItems();

        AccountEntryWithDetails onItemClick(int position);
    }

    public interface Presenter {
        <Model extends MyFilter> void bindHolderItem(HolderView<Model> holderView, int position);

        int getItemCount();

        int getViewType(int position);

        void reloadItems();

        void onItemClick(int position);
    }

    public interface HolderView<Model extends MyFilter> {
        void renderItem(Model model);
    }

}
