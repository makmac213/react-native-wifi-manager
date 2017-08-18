package com.reactnativewifimanager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    ConnectivityManager connectivityManager;
    
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
    public void getAllNetworks(Callback callBack, Callback errorCallBack) {
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworks();

            JSONArray callArray = new JSONArray();
            for (NetworkInfo networkInfo : networkInfos) {
                JSONObject callObj = new JSONObject();
                callObj.put("Info", networkInfo.toString());
                callArray.put(callObj);
            }
            callBack.put(callArray.toString);
        } catch(Exception e) {
            errorCallBack.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void getConfiguredNetworks(Callback callBack, Callback errorCallBack) {
        try {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            
            JSONArray callArray = new JSONArray();
            for (WifiConfiguration configuredNetwork : configuredNetworks) {
                JSONObject callObj = new JSONObject();
                // callObj.put("configuration", configuredNetwork.toString());
                callObj.put("BSSID", configuredNetwork.BSSID);
                // callObj.put("FQDN", configuredNetwork.FQDN);
                callObj.put("SSID", configuredNetwork.SSID);
                // callObj.put("allowedAuthAlgorithms", configuredNetwork.allowedAuthAlgorithms.toString());
                // callObj.put("allowedGroupCiphers", configuredNetwork.allowedGroupCiphers.toString());
                // callObj.put("allowedKeyManagement", configuredNetwork.allowedKeyManagement.toString());
                // callObj.put("allowedPairwiseCiphers", configuredNetwork.allowedPairwiseCiphers.toString());
                // callObj.put("allowedProtocols", configuredNetwork.allowedProtocols.toString());
                callObj.put("networkId", configuredNetwork.networkId);
                callObj.put("raw", configuredNetwork.toString());
                callArray.put(callObj);
            }

            callBack.invoke(callArray.toString());
        } catch(Exception e) {
            errorCallBack.invoke(e.getMessage());
        }
    }
}