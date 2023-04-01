package com.example.greenleef;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class ListActivity extends AppCompatActivity {

    ImageView logo = null;
    Context context = null;
    String userCpf = null;
    int defaultColor = Color.parseColor("#04AD5C");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        context = getApplicationContext();

        this.receiveParameters();
        this.initComponents();
        this.setBasicData();
    }

    /**
     * Function to init all components
     */
    protected void receiveParameters() {
        Bundle bundle = getIntent().getExtras();

        this.userCpf = bundle.getString("cpf");
    }

    /**
     * Function to init all components
     */
    protected void initComponents() {
        //Images
        this.logo = findViewById(R.id.image_logo_list);

        // Functions
        this.prepareList();
    }

    /**
     * Function to set basic data
     */
    protected void setBasicData() {
        logo.setImageResource(R.drawable.logogreenleef);

        Window window = getWindow();
        window.setStatusBarColor(this.defaultColor);
        window.setNavigationBarColor(this.defaultColor);
    }

    private void prepareList() {

    }
}