package com.codename1.cordova;

import com.codename1.impl.android.AndroidNativeUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import ca.weblite.codename1.json.*;
import com.codename1.impl.android.IntentResultListener;
import com.codename1.impl.android.LifecycleListener;

/**
 *
 * @author Chen
 */
public class CordovaPlugin implements LifecycleListener {

    protected CordovaPlugin cordova;

    public CordovaPlugin() {
        cordova = this;
        AndroidNativeUtil.addLifecycleListener(this);
    }

    public boolean execute(String action, String jsonArgs) {
        try {
            JSONArray args = new JSONArray();
            if(jsonArgs != null && jsonArgs.length() > 0){
                JSONObject obj = new JSONObject(jsonArgs);
                args.put(obj);
            }
            CallbackContext ctx = new CallbackContext(action);
            return execute(action, args, ctx);            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException{
        return false;
    }

    public Activity getActivity() {
        return AndroidNativeUtil.getActivity();
    }

    public boolean hasPermission(String permission) {
        return android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(),
                permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(CordovaPlugin ins, int req, String permission) {
        try {
            boolean ret = AndroidNativeUtil.checkForPermission(permission, "required for BLE");
            onRequestPermissionResult(req, null, null);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
    }

    public void startActivityForResult(final CordovaPlugin ins, Intent intent, final int req) {
        AndroidNativeUtil.startActivityForResult(intent, new IntentResultListener() {

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                ins.onActivityResult(req, resultCode, data);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
    }

    @Override
    public void onLowMemory() {
    }

}
