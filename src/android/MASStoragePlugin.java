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

/**
 * Created by trima09 on 10/13/2016.
 */

public class MASStoragePlugin extends CordovaPlugin {

    private static final String TAG = MASStoragePlugin.class.getCanonicalName();

    private static final Map<String, Command> commands = new HashMap();

    static {
        // Commands for Local Store and Fetch
        add(new MASSecureLocalStorageCommand.SaveCommand());
        add(new MASSecureLocalStorageCommand.FindByKeyCommand());
        add(new MASSecureLocalStorageCommand.DeleteCommand());
        add(new MASSecureLocalStorageCommand.KeySetCommand());
        add(new MASSecureLocalStorageCommand.DeleteAllCommand());

        // Commands for Cloud Store and Fetch
        add(new MASSecureStorageCommand.SaveCommand());
        add(new MASSecureStorageCommand.FindByKeyCommand());
        add(new MASSecureStorageCommand.DeleteCommand());
        add(new MASSecureStorageCommand.KeySetCommand());
    }

    private static void add(Command command) {
        commands.put(command.getAction(), command);
    }

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {

        Command command = commands.get(action);
        if (command != null) {
            try {
                command.execute(webView.getContext(), args, callbackContext);
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