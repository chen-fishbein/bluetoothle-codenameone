package com.codename1.cordova;

import com.codename1.bluetoothle.*;

public class CordovaNativeImpl {
    
   private BluetoothLePlugin bl;
    
    public CordovaNativeImpl() {
        bl = new BluetoothLePlugin();
    }
    
    public boolean execute(String action, String jsonArgs) {
        return bl.execute(action, jsonArgs);
    }

    public boolean isSupported() {
        return true;
    }

}
