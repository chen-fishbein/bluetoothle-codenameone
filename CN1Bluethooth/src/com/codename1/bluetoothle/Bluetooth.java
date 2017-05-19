/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.bluetoothle;

import ca.weblite.codename1.json.JSONException;
import ca.weblite.codename1.json.JSONObject;
import com.codename1.cordova.CordovaCallback;
import com.codename1.cordova.Cordova;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Chen
 */
public class Bluetooth {

    public static final int SCAN_MODE_BALANCED = 1;

    public static final int SCAN_MODE_LOW_LATENCY = 2;

    public static final int SCAN_MODE_LOW_POWER = 0;

    public static final int SCAN_MODE_OPPORTUNISTIC = -1;

    public static final int MATCH_MODE_AGGRESSIVE = 1;

    public static final int MATCH_MODE_STICKY = 2;

    public static final int MATCH_NUM_ONE_ADVERTISEMENT = 1;
    
    public static final int MATCH_NUM_FEW_ADVERTISEMENT = 2;

    public static final int MATCH_NUM_MAX_ADVERTISEMENT = 3;

    public static final int CALLBACK_TYPE_ALL_MATCHES = 1;

    public static final int CALLBACK_TYPE_FIRST_MATCH = 2;

    public static final int CALLBACK_TYPE_MATCH_LOST = 4;

    public static final int CONNECTION_PRIORITY_LOW = 0;

    public static final int CONNECTION_PRIORITY_BALANCED = 1;

    public static final int CONNECTION_PRIORITY_HIGH = 2;

    protected Cordova plugin;

    public Bluetooth() {
        plugin = new Cordova();
    }

    public boolean initialize(boolean request, boolean statusReceiver, String restoreKey) throws IOException {
        HashMap p = new HashMap();
        p.put("request", request);
        p.put("statusReceiver", statusReceiver);
        p.put("restoreKey", restoreKey);
        JSONObject j = new JSONObject(p);
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("initialize", j.toString(), callack);
        try {
            JSONObject obj = callack.getResponse();
            if (obj != null) {
                String status = (String) obj.get("status");
                return status.equals("enabled");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Not supported by iOS.  With throw an IOException if called on iOS.
     * @throws IOException 
     */
    public void enable() throws IOException {
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("enable", "", callack);
    }

    /**
     * Not supported by iOS.  With throw an IOException if called on iOS.
     * @throws IOException 
     */
    public void disable() throws IOException {
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("disable", "", callack);
    }

    public void startScan(ActionListener callback, ArrayList services, boolean allowDuplicates, int scanMode, int matchMode, int matchNum, int callbackType) throws IOException {
        HashMap p = new HashMap();
        p.put("allowDuplicates", allowDuplicates);
        p.put("scanMode", scanMode);
        p.put("matchMode", matchMode);
        p.put("matchNum", matchNum);
        p.put("callbackType", callbackType);

        JSONObject j = new JSONObject(p);
        if (services != null && services.size() > 0) {
            try {
                j.put("services", services);

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("startScan", j.toString(), callack);
    }

    public void stopScan() throws IOException {
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("stopScan", "", callack);
    }

    public void retrieveConnected(ActionListener callback, ArrayList services) throws IOException {
        JSONObject j = new JSONObject();
        if (services != null && services.size() > 0) {
            try {
                j.put("services", services);

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("retrieveConnected", j.toString(), callack);
    }

    public void connect(ActionListener callback, String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("connect", j.toString(), callack);
    }

    public void reconnect(ActionListener callback, String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("reconnect", j.toString(), callack);
    }

    public void disconnect(String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("disconnect", j.toString(), callack);
    }

    public void close(String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("close", j.toString(), callack);
    }

    /**
     * Not currently supported on iOS.  Currently does nothing if called on iOS.
     * @param callback
     * @param address
     * @throws IOException 
     */
    public void discover(ActionListener callback, String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("discover", j.toString(), callack);
    }

    public void services(ActionListener callback, String address, ArrayList services) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);
        if (services != null && services.size() > 0) {
            try {
                j.put("services", services);

            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("services", j.toString(), callack);
    }

    public void characteristics(ActionListener callback, String address, String service, ArrayList characteristics) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);

        JSONObject j = new JSONObject(p);
        try {
            j.put("characteristics", characteristics);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("characteristics", j.toString(), callack);
    }

    public void descriptors(ActionListener callback, String address, String service, String characteristic) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("descriptors", j.toString(), callack);
    }

    public void read(ActionListener callback, String address, String service, String characteristic) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("read", j.toString(), callack);
    }

    public void subscribe(ActionListener callback, String address, String service, String characteristic) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("subscribe", j.toString(), callack);
    }

    public void unsubscribe(ActionListener callback, String address, String service, String characteristic) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("unsubscribe", j.toString(), callack);
    }

    public void write(ActionListener callback, String address, String service, String characteristic, String value, boolean noResponse) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);
        p.put("value", value);
        if (noResponse) {
            p.put("type", "noResponse");
        }

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("write", j.toString(), callack);
    }

    public void writeQ(ActionListener callback, String address, String service, String characteristic, String value, boolean noResponse) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);
        p.put("value", value);
        if (noResponse) {
            p.put("type", "noResponse");
        }

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("writeQ", j.toString(), callack);
    }

    public void readDescriptor(ActionListener callback, String address, String service, String characteristic, String descriptor) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);
        p.put("descriptor", descriptor);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("readDescriptor", j.toString(), callack);
    }

    public void writeDescriptor(ActionListener callback, String address, String service, String characteristic, String descriptor, String value) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("service", service);
        p.put("characteristic", characteristic);
        p.put("descriptor", descriptor);
        p.put("value", value);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("writeDescriptor", j.toString(), callack);
    }

    public void rssi(ActionListener callback, String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("rssi", j.toString(), callack);
    }

    /**
     * Not supported by iOS.  With throw an IOException if called on iOS.
     * @param callback
     * @param address
     * @param mtu
     * @throws IOException 
     */
    public void mtu(ActionListener callback, String address, int mtu) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        p.put("mtu", mtu);

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("mtu", j.toString(), callack);
    }

    /**
     * Not supported by iOS.  With throw an IOException if called on iOS.
     * 
     * @param callback
     * @param address
     * @param priority
     * @throws IOException 
     */
    public void requestConnectionPriority(ActionListener callback, String address, int priority) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        if (priority == CONNECTION_PRIORITY_LOW) {
            p.put("connectionPriority", "low");
        } else if (priority == CONNECTION_PRIORITY_BALANCED) {
            p.put("connectionPriority", "balanced");
        } else if (priority == CONNECTION_PRIORITY_HIGH) {
            p.put("connectionPriority", "high");
        }

        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback(callback);
        plugin.execute("requestConnectionPriority", j.toString(), callack);
    }

    public boolean isInitialized() throws IOException {
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("isInitialized", "", callack);
        try {
            return callack.getResponse().getBoolean("isInitialized");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isEnabled() throws IOException {
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("isEnabled", "", callack);
        try {
            return callack.getResponse().getBoolean("isEnabled");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isScanning() throws IOException {
        CordovaCallback callack = new CordovaCallback();
        plugin.execute("isScanning", "", callack);
        try {
            return callack.getResponse().getBoolean("isScanning");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean wasConnected(String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("wasConnected", j.toString(), callack);
        try {
            return callack.getResponse().getBoolean("wasConnected");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isConnected(String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("isConnected", j.toString(), callack);
        try {
            return callack.getResponse().getBoolean("isConnected");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isDiscovered(String address) throws IOException {
        HashMap p = new HashMap();
        p.put("address", address);
        JSONObject j = new JSONObject(p);

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("isDiscovered", j.toString(), callack);
        try {
            return callack.getResponse().getBoolean("isDiscovered");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Not supported on iOS.  Will throw IOException if called on iOS.
     * @return
     * @throws IOException 
     */
    public boolean hasPermission() throws IOException {

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("hasPermission", "", callack);
        try {
            return callack.getResponse().getBoolean("hasPermission");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Not supported on iOS.  Will throw IOException if called on iOS.
     * @return
     * @throws IOException 
     */
    public boolean requestPermission() throws IOException {

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("requestPermission", "", callack);
        try {
            return callack.getResponse().getBoolean("requestPermission");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Not supported on iOS. Will throw IOException if called on iOS.
     * @return
     * @throws IOException 
     */
    public boolean isLocationEnabled() throws IOException {

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("isLocationEnabled", "", callack);
        try {
            return callack.getResponse().getBoolean("isLocationEnabled");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Not supported on iOS. Will throw IOException if called on iOS.
     * @return
     * @throws IOException 
     */
    public boolean requestLocation() throws IOException {

        CordovaCallback callack = new CordovaCallback();
        plugin.execute("requestLocation", "", callack);
        try {
            return callack.getResponse().getBoolean("requestLocation");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
}
