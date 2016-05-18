package com.codename1.bluetoothle;

import ca.weblite.codename1.json.JSONArray;
import com.codename1.cordova.*;

public class Operation {
  public String type;
  public JSONArray args;
  public CallbackContext callbackContext;

  public Operation(String type, JSONArray args, CallbackContext callbackContext) {
    this.type = type;
    this.args = args;
    this.callbackContext = callbackContext;
  }
}
