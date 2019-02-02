package com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.Expense;
import com.migue.zeus.expensesnotes.data.models.ExpenseDetail;
import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;
import com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity.AddExpensesOrIncomesActivity;

import java.util.Date;
import java.util.List;

public class ExpensesFragment extends Fragment implements ExpensesContract.View{
    public static final int CALL_EXPENSES_OR_INCOMES_ACTIVITY = 1;
    private RecyclerView list;
    private SwipeRefreshLayout loader;
    private boolean isLoading = false;
    private ExpensesContract.Presenter presenter;
    private FloatingActionButton create;
    private ExpensesAdapter adapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);
        presenter = new ExpensesPresenter(this);
        list = rootView.findViewById(R.id.list);
        loader = rootView.findViewById(R.id.loader);
        loader.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoading = !isLoading;
                loader.setRefreshing(isLoading);
            }
        });
        adapter = new ExpensesAdapter();
        adapter.setPresenter(presenter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        list.setLayoutManager(llm);
        list.setHasFixedSize(true);
        list.setAdapter(adapter);
        //setHasOptionsMenu(true);
        create = rootView.findViewById(R.id.create_new_fab);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddExpensesOrIncomesActivity.class);
                startActivityForResult(i, CALL_EXPENSES_OR_INCOMES_ACTIVITY);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.reloadItems();
    }

    @Override
    public void onItemsReloaded() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, ExpenseWithDetails expenseWithDetails) {
        Intent i = new Intent(getContext(), AddExpensesOrIncomesActivity.class);
        i.putExtra("Expense", expenseWithDetails);
        i.putExtra("exp", new ExpenseDetail(50, 1, 4664));
        startActivityForResult(i, CALL_EXPENSES_OR_INCOMES_ACTIVITY);
    }
}


