package com.jsonsingleobjectparsingpoc;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aalishan on 7/11/16.
 */

public class JsonObjectDataAsynckTask extends AsyncTask<String, String, JSONObject> {
    private IJsonObjectListener iJsonObjectListener;
    private HttpURLConnection connection = null;
    private BufferedReader bufferedReader = null;

    public JsonObjectDataAsynckTask(IJsonObjectListener ctx) {
        this.iJsonObjectListener = ctx;

    }

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jsonFinalObject = null;
        URL url;
        try {

            url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
            String finalData = buffer.toString();
            JSONObject object = new JSONObject(finalData);
            JSONArray jsonArray = object.getJSONArray("movies");
            jsonFinalObject = jsonArray.getJSONObject(0);
            return jsonFinalObject;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (connection != null) {
            connection.disconnect();

        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonFinalObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if (jsonObject != null) {

            iJsonObjectListener.onJsonObjectLoadingSucess(jsonObject);
        } else {
            iJsonObjectListener.onJsonObjectLoadingFailure();
        }
    }
}
