/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.cordova;

import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;

/**
 *
 * @author Chen
 */
public class CordovaCallback {
    
    private JSONObject response;
    
    private ActionListener listener;

    public CordovaCallback() {
    }

    public CordovaCallback(ActionListener listener) {
        this.listener = listener;
    }
    
    public void onError(JSONObject json){
        this.response = json;
    }

    public void onSuccess(JSONObject json){
        this.response = json;
        if(listener != null){
            Display.getInstance().callSerially(new Runnable(){
                public void run(){
                    listener.actionPerformed(new ActionEvent(json));
                }
            });
        }
    }

    public void sendResult(JSONObject json){
        this.response = json;
        if(listener != null){
            Display.getInstance().callSerially(new Runnable(){
                public void run(){
                    listener.actionPerformed(new ActionEvent(json));
                }
            });
        }
    }

    public JSONObject getResponse() {
        return response;
    }
    
    public boolean isError(){
        try {
            return response != null && response.getString("error") != null;
        } catch (JSONException ex) {
        }
        return false;
    }
    
}
