package com.jsonsingleobjectparsingpoc;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements IJsonObjectListener {
    private Button mBtnLoadJsonObjectData;
    private TextView mTvJsonData;
    private final static String JSON_OBJ_URL = "http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(MainActivity.this);
        mBtnLoadJsonObjectData = (Button) findViewById(R.id.btn_load_data);
        mTvJsonData = (TextView) findViewById(R.id.tv_hello_world);
        mBtnLoadJsonObjectData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                new JsonObjectDataAsynckTask(MainActivity.this).execute(JSON_OBJ_URL);

            }
        });
    }

    @Override
    public void onJsonObjectLoadingSucess(JSONObject obj) {
        pd.dismiss();
        String name = null;
        int year = 0;
        try {
            name = obj.getString("movie");
            year = obj.getInt("year");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTvJsonData.setText("Movie Name " + name + " Releasing Year " + year);

    }

    @Override
    public void onJsonObjectLoadingFailure() {
        Toast.makeText(this, "Some thing went wrong", Toast.LENGTH_LONG).show();
        pd.dismiss();

    }
}
