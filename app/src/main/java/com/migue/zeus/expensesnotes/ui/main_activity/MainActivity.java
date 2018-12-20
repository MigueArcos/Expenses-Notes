package com.migue.zeus.expensesnotes.ui.main_activity;

import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.arch.persistence.room.Database;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.migue.zeus.expensesnotes.R;
import com.migue.zeus.expensesnotes.data.AppDatabase;
import com.migue.zeus.expensesnotes.data.models.ExpenseCategory;
import com.migue.zeus.expensesnotes.infrastructure.Cache;
import com.migue.zeus.expensesnotes.infrastructure.utils.MyUtils;
import com.migue.zeus.expensesnotes.ui.main_activity.fragments.expenses_fragment.ExpensesFragment;
import com.migue.zeus.expensesnotes.ui.main_activity.fragments.finances_fragment.FinancesFragment;

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
    private FinancesFragment financesFragment;
    private ExpensesFragment expensesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cache = Cache.getInstance(this);
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        initializeServices();
        initializeFragments();
    }
    private void initializeViews(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void initializeServices(){
        AppDatabase.getInstance(this);
    }
    private void initializeFragments(){
        financesFragment = new FinancesFragment();
        expensesFragment = new ExpensesFragment();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        MyUtils.hideKeyboard(this);
        if (item.getItemId() == currentFragmentId) {
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.expenses:
                fragment = expensesFragment;
                break;
            case R.id.money_manager:
                fragment = financesFragment;
                break;
            case R.id.notes:
                break;
            case R.id.deleted_notes:
                break;
            case R.id.schedule:
                return false;
            case R.id.about:
                return false;
            case R.id.sync:
                return false;
            case R.id.close_session:
                return false;
        }
        if (fragment != null) getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        item.setChecked(true);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
