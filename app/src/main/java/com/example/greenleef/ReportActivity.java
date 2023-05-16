package com.example.greenleef;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.location.LocationRequestCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class ReportActivity extends AppCompatActivity implements LocationListener {

    Switch switchLampada = null;
    Switch switchPilha = null;
    Switch switchEletro = null;
    Switch switchPapel = null;
    Switch switchOrgan = null;
    Switch switchOleo = null;
    Button btSend = null;
    EditText textName = null;
    String imageBase64 = "";
    private int lat = 0;
    private int lng = 0;
    Context context = null;
    String userCpf = null;
    int defaultColor = Color.parseColor("#88a829");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

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
        //Buttons
        this.btSend = findViewById(R.id.bt_send);
        this.switchEletro = findViewById(R.id.switch_eletro);
        this.switchPilha = findViewById(R.id.switch_pilhas);
        this.switchLampada = findViewById(R.id.switch_lampada);
        this.switchOleo = findViewById(R.id.switch_oleo);
        this.switchOrgan = findViewById(R.id.switch_organ);
        this.switchPapel = findViewById(R.id.switch_papel);
        this.textName = findViewById(R.id.text_name);

        // Functions
        this.startCaptureLocation();
        this.takePicture();
    }

    /**
     * Function to set basic data
     */
    protected void setBasicData() {
        Window window = getWindow();
        window.setStatusBarColor(this.defaultColor);
        window.setNavigationBarColor(this.defaultColor);
    }

    /**
     * Function to listener events
     */
    protected void listenerEvents() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                String items = getItemsSelected();
                String name = textName.getText().toString();

                if (items.equals("") ||
                    name.equals("") ||
                    lat == 0 ||
                    lng == 0) {
                    isValid = false;
                }

                if (isValid) {
                    // Create
                    ApiManager api = new ApiManager("http://187.16.236.89:7200/", context);
                    api.createReport(userCpf, items, name, lat, lng, imageBase64);

                    backToHome();
                } else {
                    CustomToast.showToast("Verifique os campos e tente novamente.", context);
                }
            }
        });
    }

    private void backToHome() {
        this.onBackPressed();
    }

    private String getItemsSelected() {
        String items = "";

        if (this.switchLampada.isChecked()) {
            items = items + "1,";
        }
        if (this.switchPilha.isChecked()) {
            items = items + "2,";
        }
        if (this.switchPapel.isChecked()) {
            items = items + "3,";
        }
        if (this.switchEletro.isChecked()) {
            items = items + "4,";
        }
        if (this.switchOrgan.isChecked()) {
            items = items + "5,";
        }
        if (this.switchOleo.isChecked()) {
            items = items + "6";
        }

        return items;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                this.imageBase64 = ImageUtil.convert(bitmap);
            }
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    private void startCaptureLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        DataLocation dataLoc = new DataLocation(location.getLatitude(), location.getLongitude());

        this.lat = (int) dataLoc.getLat();
        this.lng = (int) dataLoc.getLng();
    }
}