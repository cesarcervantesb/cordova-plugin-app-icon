package com.ccervantesb.appicon;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Objects;

public class PluginAppIcon extends CordovaPlugin {

    private PackageManager packageManager;
    private String packageName;
    private CallbackContext callbackContext;
    private static final String MSG_SUCCESS_CHANGE_ICON = "App icon changed successfully.";
    private static final String MSG_ERROR_CHANGE_ICON = "An error occurred while changing app icon.";
    private static final String MSG_SUCCESS_RESET_ICON = "App icon reset successfully.";
    private static final String MSG_ERROR_RESET_ICON = "An error occurred while resetting app icon.";
    private static final String MSG_ERROR_PARSE_ARGS = "Missing or invalid arguments.";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        packageManager = cordova.getContext().getPackageManager();
        packageName = cordova.getContext().getPackageName();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals("change")) {
            String enableName = null;
            JSONArray disableNames = new JSONArray();
            if (!args.isNull(0)) {
                enableName = args.getString(0);
            }
            if (!args.isNull(1)) {
                disableNames = args.getJSONArray(1);
            }
            changeIcon(enableName, disableNames);
            return true;
        }
        else if (action.equals("reset")) {
            JSONArray disableNames = new JSONArray();
            if (!args.isNull(0)) {
                disableNames = args.getJSONArray(0);
            }
            resetIcon(disableNames);
            return true;
        }
        else if (action.equals("getAppName")) {
            PluginResult result = new PluginResult(PluginResult.Status.OK, getAppName());
            this.callbackContext.sendPluginResult(result);
            return true;
        }
        return false;
    }

    private String getAppName() {
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        ComponentName componentName = intent.getComponent();
        int status = packageManager.getComponentEnabledSetting(componentName);
        if (status == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || status == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
            String name = componentName.getShortClassName();
            if (Objects.equals(name, ".MainActivity")) {
                return null;
            }
            return name.substring(1);
        }
        else {
            return null;
        }
    }

    private void changeIcon(String enableName, JSONArray arrDisableNames) {
        // Validate
        if (arrDisableNames == null || arrDisableNames.length() == 0) {
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, MSG_ERROR_PARSE_ARGS);
            this.callbackContext.sendPluginResult(result);
        }
        try {
            ArrayList<String> disableNames = new ArrayList<String>();
            for (int i=0; i<arrDisableNames.length(); i++) {
                disableNames.add(arrDisableNames.getString(i));
            }
            // Enable component
            ComponentName enableComponent = new ComponentName(packageName, packageName + "." + enableName);
            packageManager.setComponentEnabledSetting(enableComponent, packageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            // Disable other components
            for (String name : disableNames) {
                ComponentName disableComponent = new ComponentName(packageName, packageName + "." + name);
                packageManager.setComponentEnabledSetting(disableComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }
            // Disable main icon component
            ComponentName mainComponent = new ComponentName(packageName, packageName + ".MainActivity");
            packageManager.setComponentEnabledSetting(mainComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            // Notify
            Toast.makeText(cordova.getContext(), MSG_SUCCESS_CHANGE_ICON, Toast.LENGTH_LONG).show();
            // Send plugin result
            PluginResult result = new PluginResult(PluginResult.Status.OK, MSG_SUCCESS_CHANGE_ICON);
            this.callbackContext.sendPluginResult(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, MSG_ERROR_CHANGE_ICON);
            this.callbackContext.sendPluginResult(result);
        }
    }

    private void resetIcon(JSONArray arrDisableNames) {
        // Validate
        if (arrDisableNames == null || arrDisableNames.length() == 0) {
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, MSG_ERROR_PARSE_ARGS);
            this.callbackContext.sendPluginResult(result);
        }
        try {
            ArrayList<String> disableNames = new ArrayList<String>();
            for (int i=0; i<arrDisableNames.length(); i++) {
                disableNames.add(arrDisableNames.getString(i));
            }
            // Enable main icon component
            ComponentName mainComponent = new ComponentName(packageName, packageName + ".MainActivity");
            packageManager.setComponentEnabledSetting(mainComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            // Disable other components
            for (String name : disableNames) {
                ComponentName disableComponent = new ComponentName(packageName, packageName + "." + name);
                packageManager.setComponentEnabledSetting(disableComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }
            // Notify
            Toast.makeText(cordova.getContext(), MSG_SUCCESS_RESET_ICON, Toast.LENGTH_LONG).show();
            // Send plugin result
            PluginResult result = new PluginResult(PluginResult.Status.OK, MSG_SUCCESS_RESET_ICON);
            this.callbackContext.sendPluginResult(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            PluginResult result = new PluginResult(PluginResult.Status.ERROR, MSG_ERROR_RESET_ICON);
            this.callbackContext.sendPluginResult(result);
        }
    }
}