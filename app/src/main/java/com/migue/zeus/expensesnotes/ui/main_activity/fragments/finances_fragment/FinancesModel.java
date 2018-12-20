package com.migue.zeus.expensesnotes.ui.main_activity.fragments.finances_fragment;

import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.Finance;
import com.migue.zeus.expensesnotes.infrastructure.dao.FinancesDao;

import java.util.List;

public class FinancesModel implements FinancesContract.Model {
    private FinancesContract.Presenter presenter;
    private final List<Finance> financeList;
    private FinancesDao financesDao = AppDatabase.getInstance().getFinancesDao();
    public FinancesModel(FinancesContract.Presenter presenter) {
        this.presenter = presenter;
        financeList = financesDao.getAllFinances();
    }

    @Override
    public int getItemCount() {
        return financeList.size() + 1;
    }

    @Override
    public void bindHolderData(FinancesContract.ItemView itemView, int position) {
        itemView.showIcon(Finance.iconId);
        itemView.showName(financeList.get(position).getName());
        itemView.showValue(financeList.get(position).getValue());
    }

    @Override
    public void bindHeaderData(FinancesContract.HeaderItemView header) {
        header.showIcon(Finance.iconId);
        header.showTotal(financesDao.getTotal());
    }
}
