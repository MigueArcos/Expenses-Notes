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
import com.migue.zeus.expensesnotes.data.models.ExpenseCategory;
import com.migue.zeus.expensesnotes.data.models.ExpenseDetail;
import com.migue.zeus.expensesnotes.data.models.ExpenseWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddExpensesOrIncomesActivity extends AppCompatActivity implements AddExpensesOrIncomesContract.View{
    private EditText nameEdit, dateEdit;
    private Spinner categorySpinner;
    private LinearLayout container;
    private AddExpensesOrIncomesContract.Presenter presenter;
    private final Calendar myCalendar = Calendar.getInstance();
    //This spinner adapter is global because it can be shared by multiple spinners
    private SpinnerAdapter<Account> accountsAdapter;
    private final List<ExpenseDetailView> expenseDetailViews = new ArrayList<>();
    private ExpenseWithDetails expenseWithDetails;
    private boolean isNewExpense = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_incomes);
        presenter = new AddExpensesOrIncomesPresenter(this);
        nameEdit = findViewById(R.id.name_edit);
        dateEdit = findViewById(R.id.date_edit);
        categorySpinner = findViewById(R.id.category_spinner);
        container = findViewById(R.id.container);

        presenter.getAccounts();
        presenter.getExpenseCategories();

        Bundle data = getIntent().getExtras();

        if (data != null && data.getParcelable("Expense") != null){
            isNewExpense = false;

            expenseWithDetails = data.getParcelable(("Expense"));
            presenter.getExpenseDate(expenseWithDetails.getExpense());
            presenter.getExpenseTitle(expenseWithDetails.getExpense());
            categorySpinner.setSelection(((SpinnerAdapter)categorySpinner.getAdapter()).getItemPosition(expenseWithDetails.getExpense().getExpenseCategoryId()));
            for (ExpenseDetail expenseDetail : expenseWithDetails.getExpenseDetails()){
                addNewExpenseDetail(expenseDetail);
            }
        } else{
            expenseWithDetails = new ExpenseWithDetails();
            presenter.getExpenseDate();
            addNewExpenseDetail();
        }

        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(
                        AddExpensesOrIncomesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, month);
                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateLabel();
                                addNewExpenseDetail();
                            }
                        },
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void showCategories(List<ExpenseCategory> expenseCategories) {
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter<>(this, android.R.layout.simple_spinner_item, expenseCategories);
        categorySpinner.setAdapter(spinnerAdapter);
        //ExpenseCategory category = ((ExpenseCategory) ((SpinnerAdapter)categorySpinner.getAdapter()).getItem(0));
    }

    @Override
    public void showAccounts(List<Account> accounts) {
        accountsAdapter = new SpinnerAdapter<>(this, android.R.layout.simple_spinner_item, accounts);
    }

    private void addNewExpenseDetail(){
        View expenseDetailView = getLayoutInflater().inflate(R.layout.activity_add_expense_detail_view, null);
        container.addView(expenseDetailView);
        expenseDetailViews.add(new ExpenseDetailView(expenseDetailView, accountsAdapter));
    }

    private void addNewExpenseDetail(ExpenseDetail expenseDetail){
        View expenseDetailView = getLayoutInflater().inflate(R.layout.activity_add_expense_detail_view, null);
        container.addView(expenseDetailView);
        expenseDetailViews.add(new ExpenseDetailView(expenseDetailView, accountsAdapter, expenseDetail));
    }

    private void updateLabel() {
        SimpleDateFormat sdf = new SimpleDateFormat(MyUtils.DATE_FORMAT, Locale.US);

        dateEdit.setText(sdf.format(myCalendar.getTime()));
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
    public void notifyExpenseCreated(long id) {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("resultExpenseId", id);
        setResult(Activity.RESULT_OK, returnIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Back arrow
            case android.R.id.home:
                createExpense();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        createOrUpdateExpense();
    }
    private void createOrUpdateExpense(){
       if (isNewExpense) createExpense() ; else updateExpense();
    }
    @SuppressWarnings("ConstantConditions")
    private void createExpense(){
        List<ExpenseDetail> expenseDetails = new ArrayList<>();
        for (ExpenseDetailView expenseDetailView: expenseDetailViews){
            expenseDetails.add(expenseDetailView.getDetail());
        }
        long expenseCategoryId = getSelectedSpinnerItem(categorySpinner).getId();
        presenter.createExpense(nameEdit.getText().toString(), dateEdit.getText().toString(), expenseCategoryId, expenseDetails);
        isNewExpense = false;
    }

    @SuppressWarnings("ConstantConditions")
    private void updateExpense(){
        expenseWithDetails.getExpense().setName(nameEdit.getText().toString());
        expenseWithDetails.getExpense().setDate(MyUtils.toDate(dateEdit.getText().toString()));
        long expenseCategoryId = getSelectedSpinnerItem(categorySpinner).getId();
        expenseWithDetails.getExpense().setExpenseCategoryId(expenseCategoryId);
        //TODO: Remove these lines since the number of expense items may change on expense modification
        for (int i = 0; i < expenseDetailViews.size(); i++){
            expenseDetailViews.get(i).getDetail();
        }
        presenter.updateExpense(expenseWithDetails);
    }

    private BaseEntity getSelectedSpinnerItem(Spinner spinner){
        int selectedIndex = spinner.getSelectedItemPosition();
        return ((SpinnerAdapter)spinner.getAdapter()).getItem(selectedIndex);
    }

    public class ExpenseDetailView{
        private Spinner spinner;
        private EditText valueEdit;
        private ExpenseDetail details;

        ExpenseDetailView(View v, SpinnerAdapter adapter) {
            initializeViews(v, adapter);
            details = new ExpenseDetail();
        }

        ExpenseDetailView(View v, SpinnerAdapter adapter, ExpenseDetail details) {
            initializeViews(v, adapter);
            this.details = details;
            valueEdit.setText(MyUtils.formatCurrency(details.getValue()));
            spinner.setSelection(((SpinnerAdapter) spinner.getAdapter()).getItemPosition(details.getAccountId()));
        }
        private void initializeViews(View v, SpinnerAdapter adapter){
            spinner = v.findViewById(R.id.account_spinner);
            valueEdit = v.findViewById(R.id.value_edit);
            spinner.setAdapter(adapter);
        }

        ExpenseDetail getDetail(){
            details.setValue(valueEdit.getText().toString().equals("") ? 0 : Double.parseDouble(valueEdit.getText().toString().substring(1)));
            details.setAccountId(getSelectedSpinnerItem(spinner).getId());
            return details;
        }
    }
}
