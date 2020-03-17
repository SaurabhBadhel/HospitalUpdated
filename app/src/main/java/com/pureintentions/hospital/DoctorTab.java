package com.pureintentions.hospital;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.pureintentions.hospital.Doctor.LoginRepository;
import com.pureintentions.hospital.ui.main.SectionsPagerAdapter;

import static com.pureintentions.hospital.DoctorSearchScreen.Name;
import static com.pureintentions.hospital.DoctorSearchScreen.Uid;

public class DoctorTab extends AppCompatActivity {
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_tab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        String NAME=intent.getStringExtra(Name);
        String ID=intent.getStringExtra(Uid);
        intent.putExtra("UID",ID);
      //  startActivity(intent);

       // setTitle(NAME);
        title=findViewById(R.id.title);
        title.setText(NAME);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);



    }


}