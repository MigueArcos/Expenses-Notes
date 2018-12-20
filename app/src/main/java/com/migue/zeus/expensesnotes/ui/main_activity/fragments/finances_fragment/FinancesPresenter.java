package com.migue.zeus.expensesnotes.ui.main_activity.fragments.finances_fragment;

public class FinancesPresenter implements FinancesContract.Presenter{
    private FinancesContract.Model model;
    private FinancesContract.View view;
    public FinancesPresenter(FinancesContract.View view) {
        this.view = view;
        model = new FinancesModel(this);
    }

    @Override
    public void bindHolderData(FinancesContract.ItemView itemView, int position) {
        model.bindHolderData(itemView, position);
    }

    @Override
    public int getItemCount() {
        return model.getItemCount();
    }

    @Override
    public void bindHeaderData(FinancesContract.HeaderItemView header) {
        model.bindHeaderData(header);
    }
}
