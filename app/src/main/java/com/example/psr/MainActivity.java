package com.example.psr;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.psr.psrupload.views.PsrUploadFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        switchToFragment();
    }
    void switchToFragment() {
        Fragment fragment = new PsrUploadFragment();
        String tag = PsrUploadFragment.class.getSimpleName();
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).add(R.id.fragment_container_view, fragment, tag).commit();
    };

    protected void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("", "onOptionsItemSelected");

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home || id == androidx.appcompat.R.id.home) {
            Log.d("", "Ha ha ha");
//            onToolBarBackPress();
//            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {

        Log.d("", "Ha ha ha onSupportNavigateUp");
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }
}