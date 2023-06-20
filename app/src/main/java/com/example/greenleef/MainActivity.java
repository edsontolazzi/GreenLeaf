package com.example.greenleef;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView logo = null;
    EditText cpf = null;
    Button btLogin = null;
    Context context = null;
    String userCpf = null;
    int defaultColor = Color.parseColor("#88a829");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        this.initComponents();
        this.setBasicData();
        this.listenerEvents();
    }

    /**
     * Function to init all components
     */
    protected void initComponents() {
        //Images
        this.logo = findViewById(R.id.image_logo_login);

        //EditTexts
        this.cpf = findViewById(R.id.text_cpf);

        //Buttons
        this.btLogin = findViewById(R.id.bt_login);
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
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCpf = cpf.getText().toString();

                if (CpfValidator.isValidCPF(userCpf)) {
                    openHome();
                } else {
                    CustomToast.showToast("CPF inválido!", context);
                }
            }
        });
    }

    /**
     * Function to open home screen
     */
    private void openHome() {
        Intent intent = new Intent(this, HomeActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("cpf", this.userCpf);

        intent.putExtras(bundle);

        this.startActivity(intent);
    }
}