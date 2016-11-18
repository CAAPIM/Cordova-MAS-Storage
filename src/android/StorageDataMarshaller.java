/**
 * Copyright (c) 2016 CA, Inc. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */
package com.ca.mas.cordova.storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.ca.mas.storage.DataMarshaller;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


class StorageDataMarshaller {

    private static List<DataMarshaller> marshallers = new ArrayList<>();

    static {
        marshallers.add(new BitmapDataMarshaller());
        marshallers.add(new JsonDataMarshaller());
        marshallers.add(new StringDataMarshaller());
        marshallers.add(new ByteArrayDataMarshaller());

    }

    public static DataMarshaller findMarshaller(Object type){
        for (DataMarshaller current : marshallers) {
            if (current.getType().isAssignableFrom(type.getClass())) {
                return current;
            }
        }
        throw new TypeNotPresentException(type.getClass().getName(), null);
    }

    private static class BitmapDataMarshaller implements DataMarshaller<Bitmap> {

        @Override
        public Bitmap unmarshall(byte[] content) throws Exception {
            return BitmapFactory.decodeByteArray(content, 0, content.length);
        }

        @Override
        public byte[] marshall(Bitmap data) throws Exception {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            data.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        @Override
        public Class<Bitmap> getType() {
            return Bitmap.class;
        }

        @Override
        public String getTypeAsString() {
            return "image/png";
        }
    }


    private static class ByteArrayDataMarshaller implements DataMarshaller<byte[]> {

        @Override
        public byte[] unmarshall(byte[] content) throws Exception {
            return content;
        }

        @Override
        public byte[] marshall(byte[] data) throws Exception {
            return data;
        }

        @Override
        public Class<byte[]> getType() {
            return byte[].class;
        }

        @Override
        public String getTypeAsString() {
            return "application/octet-stream";
        }
    }

    /**
     * Converts a JSONObject to byte[] and back.
     */
    private static class JsonDataMarshaller implements DataMarshaller<JSONObject> {

        @Override
        public JSONObject unmarshall(@NonNull byte[] content) throws UnsupportedEncodingException, JSONException {
            return new JSONObject(new String(content, "UTF-8"));
        }

        @Override
        public byte[] marshall(@NonNull JSONObject data) throws UnsupportedEncodingException {
            return data.toString().getBytes("UTF-8");
        }

        @Override
        public Class<JSONObject> getType() {
            return JSONObject.class;
        }

        @Override
        public String getTypeAsString() {
            return "application/json";
        }
    }

    private static class StringDataMarshaller implements DataMarshaller<String> {

        @Override
        public String unmarshall(byte[] content) throws Exception {
            return new String(content, "UTF-8");
        }

        @Override
        public byte[] marshall(@NonNull String data) throws Exception {
            return data.getBytes("UTF-8");
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }

        @Override
        public String getTypeAsString() {
            return "text/plain";
        }
    }
}
