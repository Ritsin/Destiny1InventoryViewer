package com.example.destinycompanionapp;

import android.Manifest;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

//This class is for the home screen of the app
public class OverviewItems extends AppCompatActivity {

    ImageView home;
    TextView discipline;
    TextView strength;
    TextView intellect;
    TextView name;
    TextView light;
    TextView race;
    ImageView classImage;

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private WeaponsFragment weaponsFragment;
    private ArmorFragment armorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overviewitems);

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        weaponsFragment = new WeaponsFragment();
        armorFragment = new ArmorFragment();

        setFragment(homeFragment);

        homeFragment.setArguments(getIntent().getBundleExtra("HomeBundle"));
        armorFragment.setArguments(getIntent().getBundleExtra("ArmorBundle"));
        weaponsFragment.setArguments(getIntent().getBundleExtra("WeaponBundle"));

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //To navigate fragments by clicking icons at bottom
                switch (item.getItemId()){

                    case R.id.nav_home:
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_weapons:
                        setFragment(weaponsFragment);
                        return true;
                    case R.id.nav_armor:
                        setFragment(armorFragment);
                        return true;
                    default:
                        return false;

                }
            }
        });

        home = findViewById(R.id.id_imageviewHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(OverviewItems.this, MainActivity.class);
                startActivity(home);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        discipline = findViewById(R.id.id_textDis);
        strength = findViewById(R.id.id_textStrength);
        intellect = findViewById(R.id.id_textIntellect);
        name = findViewById(R.id.id_textName);
        light = findViewById(R.id.id_textLight);
        race = findViewById(R.id.id_Race);
        classImage = findViewById(R.id.id_imageClass);

        //Setting values
        discipline.setText(""+getIntent().getIntExtra("discipline",-1));
        strength.setText(""+getIntent().getIntExtra("strength",-1));
        intellect.setText(""+getIntent().getIntExtra("intellect",-1));
        light.setText(""+getIntent().getIntExtra("light",-1));
        name.setText(getIntent().getStringExtra("name"));
        race.setText(getIntent().getStringExtra("race"));

        if(getIntent().getStringExtra("Class").equals("Titan")){
            classImage.setImageResource(R.drawable.titanlogo);
        }
        if(getIntent().getStringExtra("Class").equals("Hunter")){
            classImage.setImageResource(R.drawable.hunterlogo);
        }
        if(getIntent().getStringExtra("Class").equals("Warlock")){
            classImage.setImageResource(R.drawable.warlocklogo);
        }

        ActivityCompat.requestPermissions(OverviewItems.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

    }

    private void setFragment(androidx.fragment.app.Fragment fragment) {

        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();

    }
}
