package com.migue.zeus.expensesnotes.ui.main_activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.arch.persistence.room.Database;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.AccountEntryDetail;
import com.migue.zeus.expensesnotes.data.models.AccountEntryWithDetails;
import com.migue.zeus.expensesnotes.infrastructure.Cache;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountEntriesDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountEntriesDetailsDao;
import com.migue.zeus.expensesnotes.infrastructure.dao.AccountsDao;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;
import com.migue.zeus.expensesnotes.ui.main_activity.fragments.account_entries_fragment.AccountEntriesFragment;
import com.migue.zeus.expensesnotes.ui.main_activity.fragments.accounts_fragment.AccountsFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int WRITE_PERMISSION = 1;
    private int currentFragmentId;

    private NavigationView navigationView;
    private DrawerLayout drawer;

    private ProgressDialog progressDialog;
    private AlertDialog.Builder message;

    private Cache cache;

    private AlarmManager alarmManager;
    private PackageManager packageManager;
    private ComponentName receiver;
    private AccountsFragment accountsFragment;
    private AccountEntriesFragment expensesFragment;
    private AccountEntriesFragment incomesFragment;
    private AlertDialog dialogRequestWritePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cache = Cache.getInstance(this);
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }
        initializeViews();


        /*List<AccountEntryWithDetails> accountEntries =  new Gson().fromJson(getString(R.string.accounts_money_manager), new TypeToken<List<AccountEntryWithDetails>>(){}.getType());
        AccountEntriesDao accountEntriesDao = AppDatabase.getInstance().accountEntriesDao();
        AccountEntriesDetailsDao accountEntriesDetailsDao = AppDatabase.getInstance().accountEntriesDetailsDao();
        for (AccountEntryWithDetails accountEntryWithDetails : accountEntries){
            long id = accountEntriesDao.insert(accountEntryWithDetails.getAccountEntry());
            for (AccountEntryDetail detail : accountEntryWithDetails.getAccountEntryDetails()) {
                detail.setAccountEntryId(id);
                accountEntriesDetailsDao.insert(detail);
            }
        }*/
        //Services will be initialized when the write permission is granted (in that callback)
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            dialogRequestWritePermission = new AlertDialog.Builder(this).
                    setTitle(R.string.write_permission).
                    setMessage(R.string.main_activity_request_write_permission).
                    setNegativeButton(R.string.close_app_message, (dialog, which) -> this.finish()).
                    setPositiveButton(R.string.activate_permission, (d, w) -> {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                    }).
                    setCancelable(false).create();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
        }
        else{
            initializeServices();
            initializeFragments();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_PERMISSION:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dialogRequestWritePermission.dismiss();
                    initializeServices();
                    initializeFragments();
                }
                //User clicked never ask me again
                else if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    dialogRequestWritePermission.show();
                    dialogRequestWritePermission.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // permission denied, boo!
                    dialogRequestWritePermission.show();
                }

        }
    }

    private void initializeViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Fragment fragment = null;
                switch (currentFragmentId) {
                    case R.id.expenses:
                        fragment = expensesFragment;
                        //toolbar.findViewById(R.id.spinner_years).setVisibility(View.VISIBLE);
                        //toolbar.findViewById(R.id.spinner_months).setVisibility(View.VISIBLE);
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        break;
                    case R.id.revenues:
                        fragment = incomesFragment;
                        //toolbar.findViewById(R.id.spinner_years).setVisibility(View.VISIBLE);
                        //toolbar.findViewById(R.id.spinner_months).setVisibility(View.VISIBLE);
                        getSupportActionBar().setDisplayShowTitleEnabled(false);
                        break;
                    case R.id.money_manager:
                        fragment = accountsFragment;
                        //toolbar.findViewById(R.id.spinner_years).setVisibility(View.GONE);
                        //toolbar.findViewById(R.id.spinner_months).setVisibility(View.GONE);
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                        break;
                    case R.id.notes:
                        break;
                    case R.id.deleted_notes:
                        break;
                    case R.id.schedule:
                        return;
                    case R.id.about:
                        return;
                    case R.id.sync:
                        return;
                    case R.id.close_session:
                        return;
                }
                if (fragment != null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void setToolbar(int currentFragmentId){

    }
    private void initializeServices() {
        AppDatabase.getInstance(this);
    }

    private void initializeFragments() {
        accountsFragment = new AccountsFragment();
        expensesFragment = new AccountEntriesFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("shouldShowExpenses", true);
        expensesFragment.setArguments(bundle);
        incomesFragment = new AccountEntriesFragment();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        MyUtils.hideKeyboard(this);
        if (item.getItemId() == currentFragmentId) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        currentFragmentId = item.getItemId();
        item.setChecked(true);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
