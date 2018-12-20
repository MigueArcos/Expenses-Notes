package com.migue.zeus.expensesnotes.ui.main_activity.fragments.finances_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.migue.zeus.expensesnotes.R;

public class FinancesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FinancesContract.Presenter presenter;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        //ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TripItemTouchHelperCallback(this, recyclerView));
        //itemTouchHelper.attachToRecyclerView(recyclerView);
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finance_header, parent, false);
            return new FinancesHeaderItemView(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_finance, parent, false);
            return new FinanceItemView(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FinancesHeaderItemView){
            presenter.bindHeaderData((FinancesHeaderItemView) holder);
        }else if(holder instanceof FinanceItemView){
            presenter.bindHolderData((FinanceItemView) holder, position - 1);
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    public void setPresenter(FinancesContract.Presenter presenter) {
        this.presenter = presenter;
    }


    public class FinanceItemView extends RecyclerView.ViewHolder implements FinancesContract.ItemView {
        private ImageView icon;
        private TextView nameText, valueText;

        public ImageView getIcon() {
            return icon;
        }

        FinanceItemView(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            nameText = itemView.findViewById(R.id.name);
            valueText = itemView.findViewById(R.id.value);
        }


        @Override
        public void showName(String name) {
            nameText.setText(name);
        }

        @Override
        public void showValue(double value) {
            valueText.setText(String.valueOf(value));
        }

        @Override
        public void showIcon(int iconId) {
            icon.setImageResource(iconId);
        }
    }

    public class FinancesHeaderItemView extends RecyclerView.ViewHolder implements FinancesContract.HeaderItemView {
        private ImageView icon;
        private TextView totalText;

        public ImageView getIcon() {
            return icon;
        }

        FinancesHeaderItemView(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            totalText = itemView.findViewById(R.id.total);
        }

        @Override
        public void showTotal(double total) {
            totalText.setText(String.valueOf(total));
        }

        @Override
        public void showIcon(int iconId) {
            icon.setImageResource(iconId);
        }
    }
}
