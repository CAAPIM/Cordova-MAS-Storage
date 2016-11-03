/*
 * Copyright (c) 2016 CA, Inc.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */

package com.ca.mas.cordova.storage;

import android.content.Context;
import android.util.Base64;

import com.ca.mas.core.client.ServerClient;
import com.ca.mas.core.error.MAGErrorCode;
import com.ca.mas.core.error.MAGException;
import com.ca.mas.core.error.MAGRuntimeException;
import com.ca.mas.core.error.MAGServerException;
import com.ca.mas.core.error.TargetApiException;
import com.ca.mas.storage.DataMarshaller;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A {@link Command} encapsulates an action of work to be performed by a function call of Javascript,
 * {@link Command} implementations should be designed thread-safe.
 */
public abstract class Command {
    /**
     * Execute a unit of processing work to be performed.
     *
     * @param context         The Application context
     * @param args            The Cordova.exec() arguments
     * @param callbackContext The callback context used when calling back into JavaScript
     */
    abstract void execute(Context context, JSONArray args, CallbackContext callbackContext);

    /**
     * Return the action to execute
     *
     * @return The action to execute.
     */
    abstract String getAction();

    /**
     * Transform the throwable to a JSON error, used when calling back into JavaScript when for error
     *
     * @param throwable The Throwable object to format.
     * @return A JSON Object to represent the error
     */
    protected JSONObject getError(Throwable throwable) {
        int errorCode = MAGErrorCode.UNKNOWN;
        String errorMessage = throwable.getMessage();
        String errorMessageDetail = "";
        //Try to capture the root cause of the error
        if (throwable instanceof MAGException) {
            MAGException ex = (MAGException) throwable;
            errorCode = ex.getErrorCode();
            errorMessage = ex.getMessage();
        } else if (throwable instanceof MAGRuntimeException) {
            MAGRuntimeException ex = (MAGRuntimeException) throwable;
            errorCode = ex.getErrorCode();
            errorMessage = ex.getMessage();
        } else if (throwable.getCause() != null && throwable.getCause() instanceof MAGException) {
            MAGException ex = (MAGException) throwable.getCause();
            errorCode = ex.getErrorCode();
            errorMessage = ex.getMessage();
        } else if (throwable.getCause() != null && throwable.getCause() instanceof MAGRuntimeException) {
            MAGRuntimeException ex = (MAGRuntimeException) throwable.getCause();
            errorCode = ex.getErrorCode();
            errorMessage = ex.getMessage();
        } else if (throwable.getCause() != null && throwable.getCause() instanceof MAGServerException) {
            MAGServerException serverException = ((MAGServerException) throwable.getCause());
            errorCode = serverException.getErrorCode();
            errorMessage = serverException.getMessage();
        } else if (throwable.getCause() != null && throwable.getCause() instanceof TargetApiException) {
            TargetApiException e = ((TargetApiException) throwable.getCause());
            try {
                errorCode = ServerClient.findErrorCode(e.getResponse());
            } catch (Exception ignore) {
            }
        } else {
            errorMessageDetail = throwable.getMessage();
        }

        JSONObject error = new JSONObject();
        try {
            error.put("errorCode", errorCode);
            error.put("errorMessage", errorMessage);
            StringWriter errors = new StringWriter();
            throwable.printStackTrace(new PrintWriter(errors));
            error.put("errorInfo", errors.toString());
            if (!"".equals(errorMessageDetail)) {
                error.put("errorMessageDetail", errorMessageDetail);
                error.put("errorMessage", "Internal Server Error");
            }
        } catch (JSONException ignore) {
        }
        return error;
    }

    protected void success(CallbackContext callbackContext, boolean value) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, value);
        callbackContext.sendPluginResult(result);
    }

    protected void success(CallbackContext callbackContext, JSONObject resultData) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, resultData);
        callbackContext.sendPluginResult(result);
    }

    protected void success(CallbackContext callbackContext, Object resultData) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, resultData.toString());
        callbackContext.sendPluginResult(result);
    }

    protected JSONObject getErrorJson(int errorCode, String errorMessage, String errorInfo) {
        JSONObject error = new JSONObject();
        try {
            error.put("errorCode", errorCode);
            error.put("errorMessage", errorMessage);
            error.put("errorInfo", errorInfo != null ? errorInfo : "");
        } catch (JSONException ignore) {
        }
        return error;
    }

    protected JSONObject getResultJson(Object result) throws Exception {
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