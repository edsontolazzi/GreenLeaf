package com.example.greenleef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    ImageView logo = null;
    Context context = null;
    TextView textView = null;
    int defaultColor = Color.parseColor("#88a829");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        context = getApplicationContext();
        this.initComponents();
        this.setBasicData();
    }

    /**
     * Function to init all components
     */
    protected void initComponents() {
        //Images
        this.logo = findViewById(R.id.image_logo_about);

        //TextView
        this.textView = findViewById(R.id.tv_about);
    }

    /**
     * Function to set basic data
     */
    protected void setBasicData() {
        logo.setImageResource(R.drawable.logo);

        Window window = getWindow();
        window.setStatusBarColor(this.defaultColor);
        window.setNavigationBarColor(this.defaultColor);
    }
}