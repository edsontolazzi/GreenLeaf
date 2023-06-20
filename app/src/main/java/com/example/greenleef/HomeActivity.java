package com.example.greenleef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    ImageView logo = null;
    Button btList = null;
    Button btReport = null;
    Button btPoints = null;
    Button btAbout = null;
    TextView pointValue = null;
    Context context = null;
    String userCpf = null;
    int defaultColor = Color.parseColor("#88a829");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = getApplicationContext();

        this.receiveParameters();
        this.initComponents();
        this.setBasicData();
        this.listenerEvents();
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

        //TextView
        this.pointValue = findViewById(R.id.label_points_value);

        //Buttons
        this.btList = findViewById(R.id.bt_list);
        this.btReport = findViewById(R.id.bt_report);
        this.btPoints = findViewById(R.id.bt_points);
        this.btAbout = findViewById(R.id.bt_about);

        // Functions
        this.getPoints();
    }

    /**
     * Function to set basic data
     */
    protected void setBasicData() {
        logo.setImageResource(R.drawable.dog_logo);

        Window window = getWindow();
        window.setStatusBarColor(this.defaultColor);
        window.setNavigationBarColor(this.defaultColor);
    }

    /**
     * Function to listener events
     */
    protected void listenerEvents() {
        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openList();
            }
        });

        btReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReport();
            }
        });

        btPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPoints();
            }
        });

        btAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAbout();
            }
        });
    }

    private void getPoints() {
        ApiManager apiManager = new ApiManager("http://187.16.236.89:7200/", this.context);
        apiManager.getPointsByCpf(HomeActivity.this, userCpf);
    }

    public void setPoints(String points) {
        this.pointValue.setText(points);
    }

    private void openList() {
        Intent intent = new Intent(this, ListActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("cpf", this.userCpf);

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private void openAbout() {
        Intent intent = new Intent(this, AboutActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("cpf", this.userCpf);

        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    private void openReport() {
        Intent intent = new Intent(this, ReportActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("cpf", this.userCpf);

        intent.putExtras(bundle);

        this.startActivity(intent);
    }
}