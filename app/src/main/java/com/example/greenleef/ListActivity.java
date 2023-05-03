package com.example.greenleef;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ImageView logo = null;
    ListView lvGreenLeaf = null;
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

        // ListView
        this.lvGreenLeaf = findViewById(R.id.lv_greenleaf);

        // API reports
        ApiManager apiManager = new ApiManager("http://187.16.236.89:7200/", this.context);
        apiManager.getReportsByCpf(ListActivity.this, userCpf);
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

    /**
     * SetListView method
     *
     * @param json
     *
     * @throws JSONException
     */
    public void setListView(JSONArray json) throws JSONException {
        final ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < json.length(); i++) {
            JSONObject obj = ApiManager.jsonStringToObject(json.get(i).toString());
            list.add(obj.getString("name") + " - " + obj.getString("status") + " - " + obj.getString("created_at"));
        }

        final ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        lvGreenLeaf.setAdapter(adapter);
    }
}