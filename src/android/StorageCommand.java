/*
 * Copyright (c) 2016 CA, Inc.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */

package com.ca.mas.cordova.storage;

import android.util.Base64;

import com.ca.mas.cordova.core.Command;
import com.ca.mas.storage.DataMarshaller;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A {@link StorageCommand} encapsulates an action of work to be performed by a function call of Javascript,
 * {@link StorageCommand} implementations should be designed thread-safe.
 */
public abstract class StorageCommand extends Command{


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