/*
 * Copyright (c) 2016 CA, Inc.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */

package com.ca.mas.cordova.storage;

import android.content.Context;
import android.util.Log;

import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASConstants;
import com.ca.mas.storage.MASSecureStorage;
import com.ca.mas.storage.MASStorage;
import com.ca.mas.storage.MASStorageSegment;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by trima09 on 10/13/2016.
 */

public class MASSecureStorageCommand {
    private static final String TAG = MASSecureStorageCommand.class.getCanonicalName();

    public static class SaveCommand extends Command {

        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureStorage();
                String key = args.optString(1);
                Object data = args.opt(2);
                int segment_0 = args.getInt(3);
              //  int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
                int segment=fetchSegmentEnum(segment_0);
                MASCallback<Void> callback = new MASCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                        success(callbackContext, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
            return "saveToCloud";
        }
    }

    public static class FindByKeyCommand extends Command {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureStorage();
                String key = args.optString(0);
                int segment_0 = args.getInt(1);
                //int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
                int segment=fetchSegmentEnum(segment_0);
                MASCallback callback = new MASCallback() {

                    @Override
                    public void onSuccess(Object result) {
                        /*if (result == null) {
                            callbackContext.error(getErrorJson(MAGErrorCode.UNKNOWN, "Failed to GET object from cloud storage. Error #: unknown error", null));
                            return;
                        }*/
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
            return "findByUsingKeyAndModeCloud";
        }
    }

    public static class DeleteCommand extends Command {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureStorage();
                String key = args.optString(0);
                int segment_0 = args.getInt(1);
               // int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
                int segment=fetchSegmentEnum(segment_0);
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
            return "deleteByUsingKeyAndModeCloud";
        }
    }

    public static class KeySetCommand extends Command {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureStorage();
                int segment_0 = args.getInt(0);
               // int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
                int segment=fetchSegmentEnum(segment_0);
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
            return "keySetForModeCloud";
        }
    }
    private static @MASStorageSegment int fetchSegmentEnum(int segment){
        int resultSegment;
        switch(segment){
            case MASCordovaConstants.CORDOVA_MAS_CLOUD_STORAGE_SEGMENT_USER:
                resultSegment=MASConstants.MAS_USER;
                break;
            case MASCordovaConstants.CORDOVA_MAS_CLOUD_STORAGE_SEGMENT_APPLICATION:
                resultSegment=MASConstants.MAS_APPLICATION;
                break;
            case MASCordovaConstants.CORDOVA_MAS_CLOUD_STORAGE_SEGMENT_APPLICATION_FOR_USER:
                resultSegment=MASConstants.MAS_USER | MASConstants.MAS_APPLICATION;
                break;
            default:
                throw new UnsupportedOperationException("This segment is not mapped to any of the present segments");
        }
        return resultSegment;

    }
}
