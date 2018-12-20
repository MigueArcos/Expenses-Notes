package com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.Finance;

import java.util.Date;

import static com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment.ExpensesModel.HEADER_VIEW;
import static com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment.ExpensesModel.ITEM_VIEW;
import static com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment.ExpensesModel.MAIN_HEADER_VIEW;

public class ExpensesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ExpensesContract.Presenter presenter;


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
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_header_main, parent, false);
            return new ExpenseMainHeaderViewHolder(layoutView);
        } else if (viewType == HEADER_VIEW) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_header, parent, false);
            return new ExpenseHeaderViewHolder(layoutView);
        }
        else if (viewType == ITEM_VIEW){
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
            return new ExpenseViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExpenseMainHeaderViewHolder){
            presenter.bindMainHeaderData((ExpenseMainHeaderViewHolder) holder);
        }else if(holder instanceof ExpenseHeaderViewHolder){
            presenter.bindHeaderData((ExpenseHeaderViewHolder) holder, position);
        }else if(holder instanceof ExpenseViewHolder){
            presenter.bindHolderData((ExpenseViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getViewType(position);
    }

    public void setPresenter(ExpensesContract.Presenter presenter) {
        this.presenter = presenter;
    }


    public class ExpenseViewHolder extends RecyclerView.ViewHolder implements ExpensesContract.ItemView {
        private ImageView icon;
        private TextView nameText, valueText;

        ExpenseViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            nameText = itemView.findViewById(R.id.name);
            valueText = itemView.findViewById(R.id.creation_date);
        }


        @Override
        public void renderItem(Expense expense) {
            icon.setImageResource(Finance.iconId);
            nameText.setText(expense.getName());
            valueText.setText(String.valueOf(expense.getValue()));
        }
    }

    public class ExpenseHeaderViewHolder extends RecyclerView.ViewHolder implements ExpensesContract.HeaderItemView {
        private TextView totalText;

        ExpenseHeaderViewHolder(View itemView) {
            super(itemView);
            totalText = itemView.findViewById(R.id.total);
        }

        @Override
        public void showDate(Date date) {

        }

        @Override
        public void showTotal(double total) {
            totalText.setText(String.valueOf(total));
        }
    }

    public class ExpenseMainHeaderViewHolder extends RecyclerView.ViewHolder implements ExpensesContract.MainHeaderItemView {
        private TextView totalText;
        private ImageView icon;
        ExpenseMainHeaderViewHolder(View itemView) {
            super(itemView);
            totalText = itemView.findViewById(R.id.total);
        }


        @Override
        public void showTotal(double total) {
            totalText.setText(String.valueOf(total));
        }

        @Override
        public void showIcon(int iconId) {

        }
    }
}

