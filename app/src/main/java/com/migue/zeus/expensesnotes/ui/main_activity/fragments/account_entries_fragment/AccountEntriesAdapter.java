package com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.models.Finance;
import com.migue.zeus.expensesnotes.infrastructure.utils.DataObserver;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;

import static com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment.AccountEntriesModel.HEADER_VIEW;
import static com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment.AccountEntriesModel.ITEM_VIEW;
import static com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment.AccountEntriesModel.MAIN_HEADER_VIEW;

public class AccountEntriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AccountEntriesContract.Presenter presenter;
    private DataObserver dataObserver;

    public void setDataObserver(DataObserver dataObserver) {
        this.dataObserver = dataObserver;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TripItemTouchHelperCallback(this, recyclerView));
        //itemTouchHelper.attachToRecyclerView(recyclerView);
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MAIN_HEADER_VIEW) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_entry_main_header, parent, false);
            return new MainHeaderViewHolder(layoutView);
        } else if (viewType == HEADER_VIEW) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_entry_header, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == ITEM_VIEW) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_entry, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        presenter.bindHolderItem((AccountEntriesContract.HolderView<?>) holder, position);
    }

    @Override
    public int getItemCount() {
        observeData();
        return presenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getViewType(position);
    }

    public void setPresenter(AccountEntriesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void observeData() {
        if (dataObserver != null) dataObserver.observeData(presenter.getItemCount());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, AccountEntriesContract.HolderView<AccountEntriesModel.ItemModel> {
        private ImageView icon;
        private TextView nameText, valueText;

        ItemViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            nameText = itemView.findViewById(R.id.name);
            valueText = itemView.findViewById(R.id.creation_date);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.onItemClick(getAdapterPosition());
        }

        @Override
        public void renderItem(AccountEntriesModel.ItemModel itemModel) {
            icon.setImageResource(Finance.iconId);
            nameText.setText(itemModel.getAccountEntry().getAccountEntry().getName());
            valueText.setText(MyUtils.formatCurrency(itemModel.getAccountEntry().getTotal()));

        }

        @Override
        public boolean onLongClick(View v) {
            presenter.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements AccountEntriesContract.HolderView<AccountEntriesModel.HeaderModel> {
        private TextView totalText;
        private TextView dateText;

        HeaderViewHolder(View itemView) {
            super(itemView);
            totalText = itemView.findViewById(R.id.total_text);
            dateText = itemView.findViewById(R.id.date_text);
        }

        @Override
        public void renderItem(AccountEntriesModel.HeaderModel headerModel) {
            totalText.setText(MyUtils.formatCurrency(headerModel.getTotal()));
            dateText.setText(MyUtils.formatDate(headerModel.getDate()));
        }
    }

    public class MainHeaderViewHolder extends RecyclerView.ViewHolder implements AccountEntriesContract.HolderView<AccountEntriesModel.MainHeaderModel> {
        private TextView totalText;
        private ImageView icon;

        MainHeaderViewHolder(View itemView) {
            super(itemView);
            totalText = itemView.findViewById(R.id.total_text);
        }

        @Override
        public void renderItem(AccountEntriesModel.MainHeaderModel mainHeaderModel) {
            totalText.setText(MyUtils.formatCurrency(mainHeaderModel.getTotal()));
        }
    }
}

