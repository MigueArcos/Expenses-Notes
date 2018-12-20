package com.migue.zeus.expensesnotes.ui.main_activity.fragments.finances_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.migue.zeus.expensesnotes.R;

public class FinancesFragment extends Fragment implements FinancesContract.View{
    private RecyclerView list;
    private SwipeRefreshLayout loader;
    private boolean isLoading = false;
    private FinancesContract.Presenter presenter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_finances, container, false);
        presenter = new FinancesPresenter(this);
        list = rootView.findViewById(R.id.list);
        loader = rootView.findViewById(R.id.loader);
        loader.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoading = !isLoading;
                loader.setRefreshing(isLoading);
            }
        });
        FinancesAdapter adapter = new FinancesAdapter();
        adapter.setPresenter(presenter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        list.setLayoutManager(llm);
        list.setHasFixedSize(true);
        list.setAdapter(adapter);
        //setHasOptionsMenu(true);

        return rootView;
    }
    private int fibonacci(int n){
        return (n == 0 || n == 1) ? 1 : fibonacci(n - 1) + fibonacci(n - 2);
    }
}
