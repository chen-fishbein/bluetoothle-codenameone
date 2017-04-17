/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.bluetoothle;

import ca.weblite.codename1.json.JSONArray;
import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.cordova.Cordova;
import com.codename1.cordova.CordovaCallback;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jeffc
 */
public class BluetoothEx extends Bluetooth {

    private boolean isIOS;
    private String lastError;

    public BluetoothEx() {
        super();
        lastError = null;
        isIOS = Display.getInstance().getPlatformName().equals("ios");
    }

    protected void clearLastError() {
        lastError = null;
    }

    protected void setLastError(String lastMethod, String lastError) {
        this.lastError = lastMethod + ": " + lastError;
    }

    public String getLastError() {
        return lastError != null ? lastError : "success";
    }

    /**
     * Not supported by iOS. With throw an IOException if called on iOS.
     *
     * @throws IOException
     */
    @Override
    public void enable() throws IOException {
        if (!isIOS) {
            CordovaCallback callack = new CordovaCallback();
            plugin.execute("enable", "", callack);
        }
    }

    /**
     * Not supported by iOS. With throw an IOException if called on iOS.
     *
     * @throws IOException
     */
    @Override
    public void disable() throws IOException {
        if (!isIOS) {
            CordovaCallback callack = new CordovaCallback();
            plugin.execute("disable", "", callack);
        }
    }

    public boolean bond(String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("bond", j.toString(), callack);
        try {
            JSONObject obj = callack.getResponse();
            if (obj != null) {
                String status = (String) obj.get("status");
                boolean result = status.equals("bonded");
                if (result) {
                    clearLastError();
                } else {
                    setLastError("bond", status);
                }
            }
        } catch (JSONException ex) {
            setLastError("bond", ex.getMessage());
        }
        return false;
    }

    public boolean unbond(String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("unbond", j.toString(), callack);
        try {
            return callack.getResponse().getBoolean("unbonded");
        } catch (JSONException ex) {
            setLastError("unbond", ex.getMessage());
        }
        return false;
    }

    public void disconnect(ActionListener callback, String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);
        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("disconnect", j.toString(), callack);
    }

    public void close(ActionListener callback, String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("close", j.toString(), callack);
    }

    public boolean initializePeripheral(ActionListener callback, boolean request, String restoreKey) throws IOException {
        boolean result = false;
        HashMap p = new HashMap();
        p.put("address", request);
        p.put("restoreKey", restoreKey);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("initializePeripheral", j.toString(), callack);
        try {
            JSONObject obj = callack.getResponse();
            if (obj != null) {
                String status = (String) obj.get("status");
                result = status.equals("enabled");
                if (result) {
                    clearLastError();
                } else {
                    setLastError("initializePeripheral", status);
                }
            }
        } catch (JSONException ex) {
            setLastError("initializePeripheral", ex.getMessage());
        }
        return result;
    }

    public JSONObject createCharacteristic(String uuid, HashMap permissions, HashMap properties) {
        JSONObject jCharacteristic = new JSONObject();
        try {
            jCharacteristic.put("uuid", uuid);
            JSONObject jPermissions = new JSONObject(permissions);
            jCharacteristic.put("permissions", jPermissions);
            JSONObject jProperties = new JSONObject(properties);
            jCharacteristic.put("properties", jProperties);
        } catch (JSONException ex) {
            setLastError("addCharacteristic", ex.getMessage());
        }
        return jCharacteristic;
    }

    public JSONObject createService(String service, ArrayList characteristics) {
        JSONObject jService = new JSONObject();
        try {
            jService.put("service", service);
            if (characteristics != null && characteristics.size() > 0) {
                jService.put("characteristics", characteristics);
            }
        } catch (JSONException ex) {
            setLastError("createService", ex.getMessage());
        }
        return jService;
    }

    public void addService(ActionListener callback, JSONObject service) throws IOException {
        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("addService", service.toString(), callack);
    }

    public void removeService(ActionListener callback, String service) throws IOException {
        HashMap p = new HashMap();
        p.put("service", service);
        JSONObject xService = new JSONObject(p);
        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("removeService", xService.toString(), callack);
    }

    public boolean removeAllServices() throws IOException {
        boolean result = false;
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("removeAllServices", null, callack);
        try {
            JSONObject obj = callack.getResponse();
            if (obj != null) {
                String status = (String) obj.get("status");
                result = status.equals("allServicesRemoved");
                if (result) {
                    clearLastError();
                } else {
                    setLastError("removeAllServices", status);
                }
            }
        } catch (JSONException ex) {
            setLastError("removeAllServices", ex.getMessage());
        }
        return result;
    }

    public boolean startAdvertising(String service, String name) throws IOException {
        boolean result = false;
        JSONObject params = new JSONObject();
        try {
            if (isIOS) {
                JSONArray array = new JSONArray();
                array.put(service);
                params.put("services", array);
            } else {
                params.put("service", service);
            }
            params.put("name", name);
            CordovaCallback callack = new CordovaCallback();
            plugin.execute("startAdvertising", params.toString(), callack);
            JSONObject obj = callack.getResponse();
            if (obj != null) {
                String status = (String) obj.get("status");
                result = status.equals("advertisingStarted");
                if (result) {
                    clearLastError();
                } else {
                    setLastError("startAdvertising", status);
                }
            }
        } catch (JSONException e) {
            setLastError("startAdvertising", e.getMessage());
        }
        return result;
    }

    public boolean stopAdvertising() throws IOException {
        boolean result = false;
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("stopAdvertising", null, callack);
        try {
            JSONObject obj = callack.getResponse();
            if (obj != null) {
                String status = (String) obj.get("status");
                result = status.equals("advertisingStopped");
                if (result) {
                    clearLastError();
                } else {
                    setLastError("stopAdvertising", status);
                }
            }
        } catch (JSONException ex) {
            setLastError("stopAdvertising", ex.getMessage());
        }
        return result;
    }

    public boolean isAdvertising() throws IOException {
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("isAdvertising", "", callack);
        try {
            return callack.getResponse().getBoolean("isAdvertising");
        } catch (JSONException ex) {
            setLastError("isAdvertising", ex.getMessage());
        }
        return false;
    }

    public void respond(ActionListener callback, int requestId, String value) throws IOException {
        HashMap p = new HashMap();
        p.put("requestId", requestId);
        p.put("value", value);
        JSONObject j = new JSONObject(p);
        CordovaCallback callack = new CordovaCallback(callback);        
        plugin.execute("respond", j.toString(), callack);
    }
    
    public void notify(ActionListener callback, String service, String characteristic, String value) throws IOException {
        HashMap p = new HashMap();
        p.put("service", service);
        p.put("characteristic", characteristic);
        p.put("value", value);
        JSONObject j = new JSONObject(p);
        CordovaCallback callack = new CordovaCallback(callback);        
        plugin.execute("notify", j.toString(), callack);        
    }
    
    public static String getErrorDescription(String error) {
        switch (error) {
            case "errorDisable":
                return "Bluetooth isn't disabled, so unable to enable";
            case "errorEnable":
                return "Immediate failure of the internal enable() function due to Bluetooth already on or airplane mode, so unable to enable";
            case "readRequested":
                return "Respond to a read request with respond(). Characteristic (Android/iOS) or Descriptor (Android)";
            case "writeRequested":
                return "Respond to a write request with respond(). Characteristic (Android/iOS) or Descriptor (Android)";
            case "subscribed":
                return "Subscription started request, use notify() to send new data";
            case "unsubscribed":
                return "Subscription ended request, stop sending data";
            case "notificationReady":
                return "Resume sending subscription updates (iOS)";
            case "notificationSent":
                return "Notification has been sent (Android)";
            case "connected":
                return "A device has connected";
            case "disconnected":
                return "A device has disconnected";
            case "mtuChanged":
                return "MTU has changed for device";
            default:
                return "Unknown";
        }
    }

}
