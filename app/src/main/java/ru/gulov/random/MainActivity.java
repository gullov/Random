package ru.gulov.random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);

        Fragment f0 = new FragmentMain();

        // Add fragments
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, f0);
        ft.commit();



    }

    public void ShowNum(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.design_bottom_sheet_slide_in1, R.anim.design_bottom_sheet_slide_out1);

        FragmentNum newFragment = new FragmentNum();

        ft.replace(R.id.container, newFragment, "detailFragment");

        ft.commit();
    }
    public void ShowMain(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.design_bottom_sheet_slide_out12, R.anim.design_bottom_sheet_slide_in12);


        FragmentMain newFragment = new FragmentMain();

        ft.replace(R.id.container, newFragment, "detailFragment");

        ft.commit();
    }

    @Override
    public void onBackPressed() {
        ShowMain();
    }

}