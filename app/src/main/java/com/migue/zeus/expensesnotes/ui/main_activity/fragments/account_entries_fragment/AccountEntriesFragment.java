package com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment;


import android.app.AlertDialog;
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
import android.widget.TextView;

import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity.AddAccountEntryActivity;

public class AccountEntriesFragment extends Fragment implements AccountEntriesContract.View{
    public static final int CALL_ADD_ACCOUNT_ENTRY_ACTIVITY = 1;
    private RecyclerView list;
    private TextView emptyListLabel;
    private SwipeRefreshLayout loader;
    private boolean isLoading = false;
    private AccountEntriesContract.Presenter presenter;
    private FloatingActionButton create;
    private AccountEntriesAdapter adapter;
    private boolean shouldShowExpenses = false;
    private AlertDialog dialogDeleteAccountEntry;
    public AccountEntriesFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_account_entries, container, false);
        emptyListLabel = rootView.findViewById(R.id.empty_list_label);
        list = rootView.findViewById(R.id.list);
        loader = rootView.findViewById(R.id.loader);
        loader.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isLoading = !isLoading;
                loader.setRefreshing(isLoading);
            }
        });
        shouldShowExpenses = getArguments() != null && getArguments().getBoolean("shouldShowExpenses");
        presenter = new AccountEntriesPresenter(this, shouldShowExpenses);
        adapter = new AccountEntriesAdapter();
        adapter.setPresenter(presenter);
        adapter.setDataObserver(itemNumber -> emptyListLabel.setVisibility(itemNumber == 0 ? View.GONE : View.INVISIBLE));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        list.setLayoutManager(llm);
        list.setHasFixedSize(true);
        list.setAdapter(adapter);
        //setHasOptionsMenu(true);
        create = rootView.findViewById(R.id.create_new_fab);
        create.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), AddAccountEntryActivity.class);
            i.putExtra("isExpense", shouldShowExpenses);
            startActivityForResult(i, CALL_ADD_ACCOUNT_ENTRY_ACTIVITY);
        });
        loader.setRefreshing(false);
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
    public void onItemClick(int position, AccountEntryWithDetails accountEntryWithDetails) {
        Intent i = new Intent(getContext(), AddAccountEntryActivity.class);
        i.putExtra("AccountEntry", accountEntryWithDetails);
        i.putExtra("isExpense", shouldShowExpenses);
        //i.putExtra("exp", new AccountEntryDetail(50, 1, 4664));
        startActivityForResult(i, CALL_ADD_ACCOUNT_ENTRY_ACTIVITY);
    }

    @Override
    public void onItemLongClick(int position, AccountEntryWithDetails accountEntryWithDetails) {
        dialogDeleteAccountEntry = new AlertDialog.Builder(getContext()).
                setTitle(getString(R.string.delete_confirmation)).
                setMessage(getString(R.string.delete_confirmation_warning)).
                setNegativeButton(R.string.dialog_option_no, (dialog, which) -> {}).
                setPositiveButton(R.string.dialog_option_yes, (d, w) -> {
                   presenter.deleteAccountEntry(position, accountEntryWithDetails);
                }).show();
    }

    @Override
    public void notifyItemDeleted(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyItemChanged(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
    }
}


