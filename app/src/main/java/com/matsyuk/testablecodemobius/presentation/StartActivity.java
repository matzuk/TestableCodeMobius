package com.matsyuk.testablecodemobius.presentation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.matsyuk.testablecodemobius.R;
import com.matsyuk.testablecodemobius.presentation.main.views.MainFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_start);
        if (savedInstanceState == null) {
            // first creation of activity - add fragment to container
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fmt_container, new MainFragment());
            transaction.commit();
        }
    }

}
