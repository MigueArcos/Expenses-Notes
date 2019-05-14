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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity.AddAccountEntryActivity;

import java.util.Calendar;
import java.util.Date;

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
    private Spinner spinnerMonths, spinnerYears;
    private final Calendar myCalendar = Calendar.getInstance();
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
        setHasOptionsMenu(true);
        myCalendar.setTime(new Date());
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int year = spinnerYears.getSelectedItemPosition() != 0 ? Integer.parseInt(spinnerYears.getSelectedItem().toString()) : 0;
        presenter.reloadItems(spinnerMonths.getSelectedItemPosition(), year);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_account_entries_menu, menu);
        MenuItem menuSpinnerMonths = menu.findItem(R.id.spinner_month);
        spinnerMonths = (Spinner) menuSpinnerMonths.getActionView();
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, getActivity().getResources().getStringArray(R.array.months_catalogue));
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(monthAdapter);

        MenuItem menuSpinnerYears = menu.findItem(R.id.spinner_year);
        spinnerYears = (Spinner) menuSpinnerYears.getActionView();
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item, getActivity().getResources().getStringArray(R.array.years_catalogue));
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYears.setAdapter(yearsAdapter);
        final int[] spinnerChecks = {0, 0};


        spinnerMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerChecks[0]++ == 0) return;
                int year = spinnerYears.getSelectedItemPosition() != 0 ? Integer.parseInt(spinnerYears.getSelectedItem().toString()) : 0;
                presenter.reloadItems(position, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerChecks[1]++ == 0) return;
                int year = parent.getSelectedItemPosition() != 0 ? Integer.parseInt(parent.getSelectedItem().toString()) : 0;
                presenter.reloadItems(spinnerMonths.getSelectedItemPosition(), year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        int month = myCalendar.get(Calendar.MONTH) + 1;
        int year = myCalendar.get(Calendar.YEAR);
        spinnerMonths.setSelection(month, false);
        //TODO: Change this selection for years
        spinnerYears.setSelection(year - 2000 + 1, false);
        presenter.reloadItems(month, year);
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


