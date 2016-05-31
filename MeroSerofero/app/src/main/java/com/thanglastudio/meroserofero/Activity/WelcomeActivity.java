package com.thanglastudio.meroserofero.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thanglastudio.meroserofero.Adapter.CustomViewPagerAdapter;
import com.thanglastudio.meroserofero.R;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {
    CustomViewPagerAdapter mPageAdapter;
    int index;
    Button button;
    SharedPreferences prefs = null;
    ViewPager viewpager;
    CircleIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        prefs = getSharedPreferences("com.thanglastudio.meroserofero", MODE_PRIVATE);
        viewpager = (ViewPager) findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        button = (Button) findViewById(R.id.button_continue);


    }

    private void showWelcomeScreen() {

        //This runs only for first time
        mPageAdapter = new CustomViewPagerAdapter(WelcomeActivity.this);
        viewpager.setAdapter(mPageAdapter);
        indicator.setViewPager(viewpager);
        //At last index continue button is shown and directed to MainActivity
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

                //Toast.makeText(WelcomeScreenActivity.this,position+"",Toast.LENGTH_SHORT).show();
                if ((position + 1) < mPageAdapter.getCount()) {
                    button.setVisibility(View.INVISIBLE);
                } else {
                    button.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewActivity();

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        showWelcomeScreen();

        if (prefs.getBoolean("firstrun", true)) {
            showWelcomeScreen();
            prefs.edit().putBoolean("firstrun", false).commit();
        } else {
            openNewActivity();
        }

    }

    private void openNewActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}