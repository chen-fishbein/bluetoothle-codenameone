/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.cordova;

import com.codename1.system.NativeInterface;
import com.codename1.util.Callback;

/**
 *
 * @author Chen
 */
public interface CordovaNative extends NativeInterface{

    public boolean execute(String action, String jsonArgs);
}
