/**
 * Copyright (c) 2016 CA, Inc. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */

package com.ca.mas.cordova.storage;

import android.content.Context;
import android.util.Log;

import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASConstants;
import com.ca.mas.storage.MASSecureLocalStorage;
import com.ca.mas.storage.MASStorage;
import com.ca.mas.storage.MASStorageSegment;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;


public class MASSecureLocalStorageCommand {
    private static final String TAG = MASSecureLocalStorageCommand.class.getCanonicalName();

    public static class SaveStorageCommand extends StorageCommand {

        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                String key = args.optString(1);
                Object data = args.opt(2);
                int segment_0 = args.getInt(3);
                int segment=fetchSegment(segment_0);
                MASCallback<Void> callback = new MASCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                        success(callbackContext, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackContext.error(getError(e));
                    }
                };
                storage.save(key, data, segment, callback);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                callbackContext.error(getError(e));
            }
        }

        @Override
        public String getAction() {
            return "saveToLocal";
        }
    }

    public static class FindByKeyStorageCommand extends StorageCommand {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                String key = args.optString(0);
                int segment_0 = args.getInt(1);
                int segment=fetchSegment(segment_0);
                MASCallback callback = new MASCallback() {

                    @Override
                    public void onSuccess(Object result) {

                        JSONObject response = null;
                        try {
                            response = getResultJson(result);
                            success(callbackContext, response);
                        } catch (Exception ex) {
                            onError(ex);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackContext.error(getError(e));
                    }
                };
                storage.findByKey(key, segment, callback);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                callbackContext.error(getError(e));
            }
        }

        @Override
        public String getAction() {
            return "findByUsingKeyAndModeLocal";
        }
    }

    public static class DeleteStorageCommand extends StorageCommand {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                String key = args.optString(0);
                int segment_0 = args.getInt(1);
                int segment=fetchSegment(segment_0);
                MASCallback<Void> callback = new MASCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                        success(callbackContext, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackContext.error(getError(e));
                    }
                };
                storage.delete(key, segment, callback);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                callbackContext.error(getError(e));
            }
        }

        @Override
        public String getAction() {
            return "deleteByUsingKeyAndModeLocal";
        }
    }

    public static class DeleteAllStorageCommand extends StorageCommand {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASSecureLocalStorage storage = new MASSecureLocalStorage();
                int segment_0 = args.getInt(0);
                int segment=fetchSegment(segment_0);
                MASCallback<Void> callback = new MASCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                        success(callbackContext, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackContext.error(getError(e));
                    }
                };
                storage.deleteAll(segment, callback);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                callbackContext.error(getError(e));
            }
        }

        @Override
        public String getAction() {
            return "deleteAllUsingModeLocal";
        }
    }

    public static class KeySetStorageCommand extends StorageCommand {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                int segment_0 = args.getInt(0);
                int segment=fetchSegment(segment_0);
                MASCallback<Set<String>> callback = new MASCallback<Set<String>>() {

                    @Override
                    public void onSuccess(Set<String> result) {
                        success(callbackContext, result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callbackContext.error(getError(e));
                    }
                };
                storage.keySet(segment, callback);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                callbackContext.error(getError(e));
            }
        }

        @Override
        public String getAction() {
            return "keySetForModeLocal";
        }
    }
    private static @MASStorageSegment int fetchSegment(int segment){
        int resultSegment;
        switch (segment) {
            case MASCordovaStorageConstants.CORDOVA_MAS_LOCAL_STORAGE_SEGMENT_APPLICATION:
                resultSegment = MASConstants.MAS_APPLICATION;
                break;
            case MASCordovaStorageConstants.CORDOVA_MAS_LOCAL_STORAGE_SEGMENT_APPLICATION_FOR_USER:
                resultSegment = MASConstants.MAS_USER | MASConstants.MAS_APPLICATION;
                break;
            default:
                throw new UnsupportedOperationException("This segment is not mapped to any of the present segments");
        }
        return resultSegment;
    }
}