package com.ca.mas.cordova.storage;

import android.content.Context;
import android.util.Log;

import com.ca.mas.foundation.MASCallback;
import com.ca.mas.foundation.MASConstants;
import com.ca.mas.storage.MASSecureLocalStorage;
import com.ca.mas.storage.MASStorage;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created by trima09 on 10/13/2016.
 */

public class MASSecureLocalStorageCommand {
    private static final String TAG = MASSecureLocalStorageCommand.class.getCanonicalName();

    public static class SaveCommand extends Command {

        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                String key = args.optString(1);
                Object data = args.opt(2);
                int segment_0 = args.optInt(3);
                int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);

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

    public static class FindByKeyCommand extends Command {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                String key = args.optString(0);
                int segment_0 = args.optInt(2);
                int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
                MASCallback callback = new MASCallback() {

                    @Override
                    public void onSuccess(Object result) {
                        /*if (result == null) {
                            callbackContext.error(getErrorJson(MAGErrorCode.UNKNOWN, "Failed to GET object from local storage. Error #: unknown error", null));
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
            return "findByUsingKeyAndModeLocal";
        }
    }

    public static class DeleteCommand extends Command {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                String key = args.optString(0);
                /*if(key == null || key.isEmpty()){
                    callbackContext.error(getErrorJson(MAGErrorCode.UNKNOWN,"Method Not allowed",""));
                    return;
                }*/
                int segment_0 = args.optInt(2);
                int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
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

    public static class DeleteAllCommand extends Command {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASSecureLocalStorage storage = new MASSecureLocalStorage();
                String key = args.optString(0);
                int segment_0 = args.optInt(2);
                int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
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

    public static class KeySetCommand extends Command {
        @Override
        public void execute(Context context, JSONArray args, final CallbackContext callbackContext) {
            try {
                MASStorage storage = new MASSecureLocalStorage();
                int segment_0 = args.optInt(2);
                int segment = segment_0 == 1 ? MASConstants.MAS_USER : (segment_0 == 2 ? MASConstants.MAS_APPLICATION : MASConstants.MAS_USER | MASConstants.MAS_APPLICATION);
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
}