package com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment;

public class ExpensesPresenter implements ExpensesContract.Presenter{
    private ExpensesContract.Model model;
    private ExpensesContract.View view;
    public ExpensesPresenter(ExpensesContract.View view) {
        this.view = view;
        model = new ExpensesModel(this);
    }

    @Override
    public void bindHolderData(ExpensesContract.ItemView itemView, int position) {
        model.bindHolderData(itemView, position);
    }

    @Override
    public int getItemCount() {
        return model.getItemCount();
    }

    @Override
    public void bindHeaderData(ExpensesContract.HeaderItemView header, int position) {
        model.bindHeaderData(header, position);
    }

    @Override
    public void bindMainHeaderData(ExpensesContract.MainHeaderItemView mainHeaderItemView) {
        model.bindMainHeaderItemView(mainHeaderItemView);
    }

    @Override
    public int getViewType(int position) {
        return model.getViewType(position);
    }
}
