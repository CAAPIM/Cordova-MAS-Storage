/**
 * Copyright (c) 2016 CA, Inc. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package com.ca.mas.cordova.storage;

import android.util.Base64;
import android.util.Log;

import com.ca.mas.cordova.core.MASCordovaPlugin;
import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASConstants;
import com.ca.mas.storage.DataMarshaller;
import com.ca.mas.storage.MASSecureLocalStorage;
import com.ca.mas.storage.MASStorage;
import com.ca.mas.storage.MASStorageSegment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;


public class MASStoragePlugin extends MASCordovaPlugin {
    private static final String TAG = MASStoragePlugin.class.getCanonicalName();

    private static final int CORDOVA_MAS_LOCAL_STORAGE_SEGMENT_APPLICATION = 0;
    private static final int CORDOVA_MAS_LOCAL_STORAGE_SEGMENT_APPLICATION_FOR_USER = 1;

    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equalsIgnoreCase("saveToLocal")) {
            saveToLocal(args, callbackContext);
        } else if (action.equalsIgnoreCase("findByUsingKeyAndModeLocal")) {
            findByUsingKeyAndModeLocal(args, callbackContext);
        } else if (action.equalsIgnoreCase("deleteByUsingKeyAndModeLocal")) {
            deleteByUsingKeyAndModeLocal(args, callbackContext);
        } else if (action.equalsIgnoreCase("deleteAllUsingModeLocal")) {
            deleteAllUsingModeLocal(args, callbackContext);
        } else if (action.equalsIgnoreCase("keySetForModeLocal")) {
            keySetForModeLocal(args, callbackContext);
        } else {
            callbackContext.error("Invalid action");
            return false;
        }
        return true;
    }

    private void saveToLocal(final JSONArray args, final CallbackContext callbackContext) {
        try {
            MASStorage storage = new MASSecureLocalStorage();
            String key = args.optString(1);
            Object data = args.opt(2);
            int segment_0 = args.getInt(3);
            int segment = fetchSegmentLocal(segment_0);

            storage.save(key, data, segment, new MASCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    success(callbackContext, true, false);
                }

                @Override
                public void onError(Throwable e) {
                    callbackContext.error(getError(e));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            callbackContext.error(getError(e));
        }
    }

    private void findByUsingKeyAndModeLocal(final JSONArray args, final CallbackContext callbackContext) {
        try {
            MASStorage storage = new MASSecureLocalStorage();
            String key = args.optString(0);
            int segment_0 = args.getInt(1);
            int segment = fetchSegmentLocal(segment_0);

            storage.findByKey(key, segment, new MASCallback() {
                @Override
                public void onSuccess(Object result) {
                    JSONObject response = null;
                    try {
                        response = getResultJson(result);
                        success(callbackContext, response, false);
                    } catch (Exception ex) {
                        Log.e(TAG, ex.getMessage());
                        callbackContext.error(getError(ex));
                    }
                }

                @Override
                public void onError(Throwable e) {
                    callbackContext.error(getError(e));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            callbackContext.error(getError(e));
        }
    }

    private void deleteByUsingKeyAndModeLocal(final JSONArray args, final CallbackContext callbackContext) {
        try {
            MASStorage storage = new MASSecureLocalStorage();
            String key = args.optString(0);
            int segment_0 = args.getInt(1);
            int segment = fetchSegmentLocal(segment_0);

            storage.delete(key, segment, new MASCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    success(callbackContext, true, false);
                }

                @Override
                public void onError(Throwable e) {
                    LOG.e(TAG, e.getMessage());
                    callbackContext.error(getError(e));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            callbackContext.error(getError(e));
        }
    }

    private void deleteAllUsingModeLocal(final JSONArray args, final CallbackContext callbackContext) {
        try {
            MASSecureLocalStorage storage = new MASSecureLocalStorage();
            int segment_0 = args.getInt(0);
            int segment = fetchSegmentLocal(segment_0);

            storage.deleteAll(segment, new MASCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    success(callbackContext, true, false);
                }

                @Override
                public void onError(Throwable e) {
                    LOG.e(TAG, e.getMessage());
                    callbackContext.error(getError(e));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            callbackContext.error(getError(e));
        }
    }

    private void keySetForModeLocal(final JSONArray args, final CallbackContext callbackContext) {
        try {
            MASStorage storage = new MASSecureLocalStorage();
            int segment_0 = args.getInt(0);
            int segment = fetchSegmentLocal(segment_0);

            storage.keySet(segment, new MASCallback<Set<String>>() {
                @Override
                public void onSuccess(Set<String> result) {
                    JSONArray array = new JSONArray(result);
                    success(callbackContext, array, false);
                }

                @Override
                public void onError(Throwable e) {
                    LOG.e(TAG, e.getMessage());
                    callbackContext.error(getError(e));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            callbackContext.error(getError(e));
        }
    }


    private static
    @MASStorageSegment
    int fetchSegmentLocal(int segment) {
        switch (segment) {
            case CORDOVA_MAS_LOCAL_STORAGE_SEGMENT_APPLICATION:
                return MASConstants.MAS_APPLICATION;
            case CORDOVA_MAS_LOCAL_STORAGE_SEGMENT_APPLICATION_FOR_USER:
                return MASConstants.MAS_USER | MASConstants.MAS_APPLICATION;
            default:
                throw new UnsupportedOperationException("This segment is not mapped to any of the present segments");
        }
    }

    private JSONObject getResultJson(Object result) throws Exception {
        JSONObject response = new JSONObject();
        if (result == null) {
            response.put("mime", "text/plain");
            response.put("value", "");
            return response;
        }
        DataMarshaller marshaller = StorageDataMarshaller.findMarshaller(result);
        String mime = marshaller.getTypeAsString();

        byte[] bytes = null;
        try {
            response.put("mime", mime);
            bytes = marshaller.marshall(result);
            String b64 = new String(Base64.encode(bytes, 0), "UTF-8");
            StringBuilder base64String = new StringBuilder();
            base64String.append(b64);
            if (base64String.lastIndexOf(System.getProperty("line.separator")) != -1) {
                base64String.deleteCharAt(base64String.lastIndexOf(System.getProperty("line.separator")));
            }
            if (base64String.lastIndexOf("\r") != -1) {
                base64String.deleteCharAt(base64String.lastIndexOf("\r"));
            }
            response.put("value", base64String.toString());
            return response;
        } catch (Exception ex) {
            throw ex;
        }
    }
}