package com.migue.zeus.expensesnotes.ui.add_expense_or_income_activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.models.Account;
import com.migue.zeus.expensesnotes.data.models.BaseEntity;
import com.migue.zeus.expensesnotes.data.models.AccountEntryCategory;
import com.migue.zeus.expensesnotes.data.models.AccountEntryDetail;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MathAnalyzer;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAccountEntryActivity extends AppCompatActivity implements AddAccountEntryContract.View{
    private EditText nameEdit, dateEdit;
    private Spinner categorySpinner;
    private LinearLayout container;
    private AddAccountEntryContract.Presenter presenter;
    private final Calendar myCalendar = Calendar.getInstance();
    //This spinner adapter is global because it can be shared by multiple spinners
    private SpinnerAdapter<Account> accountsAdapter;
    private final List<AccountEntryDetailView> accountEntryDetailViews = new ArrayList<>();
    private AccountEntryWithDetails accountEntryWithDetails;
    private boolean isNewAccountEntry = true;
    private boolean isExpense = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_entry);
        presenter = new AddAccountEntryPresenter(this);
        nameEdit = findViewById(R.id.name_edit);
        dateEdit = findViewById(R.id.date_edit);
        categorySpinner = findViewById(R.id.category_spinner);
        container = findViewById(R.id.container);

        presenter.getAccounts();


        Bundle data = getIntent().getExtras();
        isExpense = data != null && data.getBoolean("isExpense");
        presenter.getAccountEntryCategories(isExpense);
        if (data != null && data.getParcelable("AccountEntry") != null){
            isNewAccountEntry = false;
            accountEntryWithDetails = data.getParcelable("AccountEntry");
            presenter.getAccountEntryDate(accountEntryWithDetails.getAccountEntry());
            presenter.getAccountEntryTitle(accountEntryWithDetails.getAccountEntry());
            categorySpinner.setSelection(((SpinnerAdapter)categorySpinner.getAdapter()).getItemPosition(accountEntryWithDetails.getAccountEntry().getAccountEntryCategoryId()));
            for (AccountEntryDetail accountEntryDetail : accountEntryWithDetails.getAccountEntryDetails()){
                addNewExpenseDetail(accountEntryDetail);
            }
        } else{
            accountEntryWithDetails = new AccountEntryWithDetails();
            presenter.getAccountEntryDate();
            addNewExpenseDetail();
        }

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(
                        AddAccountEntryActivity.this,
                        (view, year, month, dayOfMonth) -> {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, month);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateLabel();
                            //addNewExpenseDetail();
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void showCategories(List<AccountEntryCategory> accountEntryCategories) {
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter<>(this, android.R.layout.simple_spinner_item, accountEntryCategories);
        categorySpinner.setAdapter(spinnerAdapter);
        //AccountEntryCategory category = ((AccountEntryCategory) ((SpinnerAdapter)categorySpinner.getAdapter()).getItem(0));
    }

    @Override
    public void showAccounts(List<Account> accounts) {
        accountsAdapter = new SpinnerAdapter<>(this, android.R.layout.simple_spinner_item, accounts);
    }

    private void addNewExpenseDetail(){
        View expenseDetailView = getLayoutInflater().inflate(R.layout.activity_add_account_entry_detail_view, null);
        container.addView(expenseDetailView);
        accountEntryDetailViews.add(new AccountEntryDetailView(expenseDetailView, accountsAdapter));
    }

    private void addNewExpenseDetail(AccountEntryDetail accountEntryDetail){
        View expenseDetailView = getLayoutInflater().inflate(R.layout.activity_add_account_entry_detail_view, null);
        container.addView(expenseDetailView);
        accountEntryDetailViews.add(new AccountEntryDetailView(expenseDetailView, accountsAdapter, accountEntryDetail));
    }

    private void updateLabel() {
        dateEdit.setText(MyUtils.formatDate(myCalendar.getTime()));
    }

    @Override
    public void showTitle(String title) {
        nameEdit.setText(title);
    }

    @Override
    public void showDate(String date) {
        dateEdit.setText(date);
        MyUtils.fromStringDate(dateEdit.getText().toString(), myCalendar);
    }

    @Override
    public void notifyAccountEntryCreated(long id) {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("resultExpenseId", id);
        setResult(Activity.RESULT_OK, returnIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Back arrow
            case android.R.id.home:
                createOrUpdateAccountEntry();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        createOrUpdateAccountEntry();
    }
    private void createOrUpdateAccountEntry(){
       if (isNewAccountEntry) createAccountEntry() ; else updateExpense();
    }
    @SuppressWarnings("ConstantConditions")
    private void createAccountEntry(){
        List<AccountEntryDetail> accountEntryDetails = new ArrayList<>();
        for (AccountEntryDetailView accountEntryDetailView : accountEntryDetailViews){
            accountEntryDetails.add(accountEntryDetailView.getDetail());
        }
        BaseEntity expenseCategory = getSelectedSpinnerItem(categorySpinner);
        String accountEntryName =  nameEdit.getText().toString().equals(MyUtils.EmptyString) ? expenseCategory.getReadableName() : nameEdit.getText().toString();
        if (getSumOfEntries(accountEntryDetails) != 0){
            presenter.createAccountEntry(accountEntryName, dateEdit.getText().toString(), expenseCategory.getId(), accountEntryDetails, isExpense);
            isNewAccountEntry = false;
        }
    }
    private double getSumOfEntries(List<AccountEntryDetail> details){
        double sum = 0;
        for (AccountEntryDetail detail : details){
            sum += detail.getValue();
        }
        return sum;
    }

    @SuppressWarnings("ConstantConditions")
    private void updateExpense(){
        BaseEntity expenseCategory = getSelectedSpinnerItem(categorySpinner);
        String accountEntryName =  nameEdit.getText().toString().equals(MyUtils.EmptyString) ? expenseCategory.getReadableName() : nameEdit.getText().toString();
        accountEntryWithDetails.getAccountEntry().setName(accountEntryName);
        accountEntryWithDetails.getAccountEntry().setDate(MyUtils.toDate(dateEdit.getText().toString()));
        accountEntryWithDetails.getAccountEntry().setAccountEntryCategoryId(expenseCategory.getId());
        //TODO: Remove these lines since the number of accountEntry items may change on accountEntry modification
        for (int i = 0; i < accountEntryDetailViews.size(); i++){
            accountEntryDetailViews.get(i).getDetail();
        }
        presenter.updateAccountEntry(accountEntryWithDetails);
    }

    private BaseEntity getSelectedSpinnerItem(Spinner spinner){
        int selectedIndex = spinner.getSelectedItemPosition();
        return ((SpinnerAdapter)spinner.getAdapter()).getItem(selectedIndex);
    }

    public class AccountEntryDetailView {
        private Spinner spinner;
        private EditText valueEdit;
        private AccountEntryDetail details;

        AccountEntryDetailView(View v, SpinnerAdapter adapter) {
            initializeViews(v, adapter);
            details = new AccountEntryDetail();
        }

        AccountEntryDetailView(View v, SpinnerAdapter adapter, AccountEntryDetail details) {
            initializeViews(v, adapter);
            this.details = details;
            valueEdit.setText(MyUtils.formatCurrency(details.getValue()).replace("$",""));
            spinner.setSelection(((SpinnerAdapter) spinner.getAdapter()).getItemPosition(details.getAccountId()));
        }
        private void initializeViews(View v, SpinnerAdapter adapter){
            spinner = v.findViewById(R.id.account_spinner);
            valueEdit = v.findViewById(R.id.value_edit);
            spinner.setAdapter(adapter);
        }

        AccountEntryDetail getDetail(){
            double value = valueEdit.getText().toString().equals("") ? 0 : MathAnalyzer.evaluate(valueEdit.getText().toString());

            details.setValue(value);
            details.setAccountId(getSelectedSpinnerItem(spinner).getId());
            return details;
        }
    }
}
