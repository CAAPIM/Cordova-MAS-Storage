/*
 * Copyright (c) 2016 CA, Inc.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */

package com.ca.mas.cordova.storage;

import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;



public class MASStoragePlugin extends CordovaPlugin {

    private static final String TAG = MASStoragePlugin.class.getCanonicalName();

    private static final Map<String, StorageCommand> storageCommands = new HashMap();

    static {
        // Commands for Local Store and Fetch
        add(new MASSecureLocalStorageCommand.SaveStorageCommand());
        add(new MASSecureLocalStorageCommand.FindByKeyStorageCommand());
        add(new MASSecureLocalStorageCommand.DeleteStorageCommand());
        add(new MASSecureLocalStorageCommand.KeySetStorageCommand());
        add(new MASSecureLocalStorageCommand.DeleteAllStorageCommand());

        // Commands for Cloud Store and Fetch
        add(new MASSecureStorageCommand.SaveStorageCommand());
        add(new MASSecureStorageCommand.FindByKeyStorageCommand());
        add(new MASSecureStorageCommand.DeleteStorageCommand());
        add(new MASSecureStorageCommand.KeySetStorageCommand());
    }

    private static void add(StorageCommand storageCommand) {
        storageCommands.put(storageCommand.getAction(), storageCommand);
    }

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {

        StorageCommand storageCommand = storageCommands.get(action);
        if (storageCommand != null) {
            try {
                storageCommand.execute(webView.getContext(), args, callbackContext);
                return true;
            } catch (Throwable t) {
                Log.e(TAG, t.getMessage(), t);
                return false;
            }
        } else {
            Log.e(TAG, "Action not found: " + action);
            return false;
        }
    }
}