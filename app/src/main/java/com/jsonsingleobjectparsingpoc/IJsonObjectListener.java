package com.jsonsingleobjectparsingpoc;

import org.json.JSONObject;

/**
 * Created by aalishan on 7/11/16.
 */

public interface IJsonObjectListener {
    void onJsonObjectLoadingSucess(JSONObject obj);
    void onJsonObjectLoadingFailure();
}
