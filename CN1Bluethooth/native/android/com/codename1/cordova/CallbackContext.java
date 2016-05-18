package com.codename1.cordova;

import ca.weblite.codename1.json.*;

/**
 *
 * @author Chen
 */
public class CallbackContext {
    
    private String action;
    
    public CallbackContext(String action) {
        this.action = action;
    }    
    
    public void success(JSONObject res){
        CordovaCallbackManager.onSuccess(action, res.toString());
    }

    public void error(JSONObject res){
        CordovaCallbackManager.onError(action, res.toString());
    }
    
    public void sendPluginResult(PluginResult pluginResult){
        CordovaCallbackManager.sendResult(action, pluginResult.getMessage());        
    }

}
