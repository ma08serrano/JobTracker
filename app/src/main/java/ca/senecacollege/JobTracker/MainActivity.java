package ca.senecacollege.JobTracker;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ca.senecacollege.JobTracker.DatabaseHelper.MyDBHandler;
import ca.senecacollege.JobTracker.DatabaseHelper.User;

// MainActivity will display the fragments and includes the navigation drawer components
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private MyDBHandler db;

    private TextView userInitial;
    private TextView userFullName;

    // Returns a new messaging object to request an action from another app component
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    // Method to handle actions when this activity is first displayed on screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set action bar to layout object
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set side navigation drawer to toggle (open/close) when clicked
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Set selected navigation listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Use shared preferences to retrieve saved username and load data from database
        SharedPreferences sharedpreferences = MainActivity.this.getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String savedUsername = sharedpreferences.getString("usernameKey","");

        User user = new User();

        // Load user from saved db
        db = new MyDBHandler(MainActivity.this, null, null, 1);
        user = db.loadUserHandler(savedUsername);

        // Set the user's initial and full name in the navigation drawer header
        View header = navigationView.getHeaderView(0);
        userInitial = (TextView)header.findViewById(R.id.nav_user_initials);
        userInitial.setText(user.getFirstName().substring(0,1) + user.getLastName().substring(0,1));

        userFullName = (TextView)header.findViewById(R.id.nav_user_fullname);
        userFullName.setText(user.getFirstName() + " " + user.getLastName());

        // Start the job board fragment after logging in - it will be the main fragment
        Fragment mainActivityFragment = new JobBoardFragment();
        if (mainActivityFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_screen_area, mainActivityFragment).commit();
            navigationView.setCheckedItem(R.id.nav_job_board);
        }
    }

    // Method to handle actions when Back button is pressed
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // If side navigation is open, close it when back button is pressed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager mFragmentManager = getSupportFragmentManager();

            // Keeps track of navigating through the screens using the back button
            if (mFragmentManager.getBackStackEntryCount() > 0) {
                mFragmentManager.popBackStack();
            } else {
                // Commented out to prevent the back button from exiting the app (user stays on the app)
                // super.onBackPressed();
            }

        }
    }

    // Method to handle actions when Options Menu is created
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Method to handle the top right button (opens Settings)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        // No inspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to change title of action bar
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    // Method to log the user out
    public void logout() {
        Log.e(TAG, "--> Start logout");

        // Clear the username data saved to shared preferences
        SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("usernameKey", "");
        editor.commit();

        User.setUser(null);

        // Redirect to Login screen after logging user out
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to handle actions when Navigation Item is selected
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        /* Clicking an item from the navigation drawer inserts
         * a specific fragment into main activity, except for log out */
        if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_job_search) {
            fragment = new JobSearchFragment();
        } else if (id == R.id.nav_job_board) {
            fragment = new JobBoardFragment();
        } else if (id == R.id.nav_notifications) {
            fragment = new NotificationsFragment();
        } else if (id == R.id.nav_logout) {
            // Log out will log the user out and return the user to the Login screen
            logout();
        }

        // The specified fragment is placed into the main screen area (content_main.xml page)
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_screen_area, fragment).addToBackStack(null).commit();
        }

        // Side Navigation is closed after selected a screen to go to
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
