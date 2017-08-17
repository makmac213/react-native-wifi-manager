package com.reactnativewifimanager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.CallLog;
import android.provider.CallLog.Calls;

import java.lang.StringBuffer;
import java.util.Date;
import java.util.List;

import org.json.*;

public class RNWifiManagerModule extends ReactContextBaseJavaModule {

    WifiManager wifiManager;
    
    private static final String TAG = RNWifiManagerModule.class.getSimpleName();

    private Context context;

    // set the activity - pulled in from Main
    public RNWifiManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "WifiManager";
    }

    @ReactMethod
    public void getConfiguredNetworks(Callback callBack, Callback errorCallBack) {
        try {
            wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            
            JSONArray callArray = new JSONArray();
            for (WifiConfiguration configuredNetwork : configuredNetworks) {
                JSONObject callObj = new JSONObject();
                callObj.put("configuration", configuredNetwork.toString());
                callArray.put(callObj);
            }

            callBack.invoke(callArray.toString());
        } catch(Exception e) {
            errorCallBack.invoke(e.getMessage());
        }
    }
}