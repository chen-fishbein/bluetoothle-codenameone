/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.cordova;

import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.Callback;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Chen
 */
public class CordovaCallbackManager {
    
    private static Map callbacks = new HashMap();

    public static void setMethodCallback(String action, CordovaCallback cb){
        callbacks.put(action, cb);
    }

    public static void onError(String action, String result){
        try {
            JSONObject json = new JSONObject(result);
            CordovaCallback cb = (CordovaCallback)callbacks.get(action);
            cb.onError(json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static void onSuccess(String action, String result){
        try {
            JSONObject json = new JSONObject(result);
            CordovaCallback cb = (CordovaCallback)callbacks.get(action);
            cb.onSuccess(json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public static void sendResult(String action, String result){
        try {
            JSONObject json = new JSONObject(result);
            CordovaCallback cb = (CordovaCallback)callbacks.get(action);
            cb.sendResult(json);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    
}
