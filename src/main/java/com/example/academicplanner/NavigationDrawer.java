package com.example.academicplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.academicplanner.databinding.ActivityNavigationDrawerBinding;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.AppCompatActivity;

public class NavigationDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavigationDrawerBinding binding;
    private TextView usernameView;
    private TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNavigationDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        usernameView = headerView.findViewById(R.id.username);
        emailView = headerView.findViewById(R.id.email);

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "User Name");
        String email = sharedPreferences.getString("Email", "User Email");

        usernameView.setText(username);
        emailView.setText(email);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_overview, R.id.nav_agenda, R.id.nav_calendar, R.id.nav_timetable, R.id.nav_subjects, R.id.nav_teachers, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_over_view);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_overview && navController.getCurrentDestination().getId() != R.id.nav_overview) {

                navController.navigate(R.id.nav_overview);
            } else {

                navController.navigate(itemId);
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "User Name");
        String email = sharedPreferences.getString("Email", "User Email");

        usernameView.setText(username);
        emailView.setText(email);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_over_view);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
