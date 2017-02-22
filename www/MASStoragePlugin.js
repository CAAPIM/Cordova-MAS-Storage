/**
 * Copyright (c) 2016 CA, Inc. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */
 
var MASStoragePlugin;
module.exports = MASStoragePlugin = {
    MASLocalStorageSegment:
        {
            /**
             *  Unknown Mode
             */
            MASLocalStorageSegmentUnknown: -1,
    
            /**
             *  Data in this mode is stored and available in an Application Level
             */
            MASLocalStorageSegmentApplication: 0,

            /**
             *  Data in this mode is stored and available in an Application for a specific User
             */
            MASLocalStorageSegmentApplicationForUser: 1
        },

    MASCloudStorageSegment:
        {
            /**
             *  Unknown Mode
             */
            MASCloudStorageSegmentUnknown: -1,

            /**
             *  Data in this mode is stored and available to a specific User ONLY
             */
            MASCloudStorageSegmentUser: 0,
            
            /**
             *  Data in this mode is stored and available in an Application Level
             */
            MASCloudStorageSegmentApplication: 1,

            /**
             *  Data in this mode is stored and available in an Application for a specific User
             */
            MASCloudStorageSegmentApplicationForUser: 2
        },

        /**
         MASSecureLocalStorage which has the interfaces mapped to the native MASStorage class. These functions would store and fetch data from local device store.
         */
    MASSecureLocalStorage: function () {
		'use strict';

        /**
        *   Saves the provided data to local storage
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} mime mime type of the data
        *   @param {string} key key with which the data is to be stored
        *   @param {string} data data payload
        *   @param {string} mode mode in which the data is to be saved
        */
        this.save = function (successHandler, errorHandler, mime, key, data, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "saveToLocal", [mime, key, data, mode]);
        };

        /**
        *   Find data using key and mode
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} key key of the data to be searched
        *   @param {string} mode mode in which the data was saved
        */
        this.findByUsingKeyAndMode = function (successHandler, errorHandler, key, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "findByUsingKeyAndModeLocal", [key, mode]);
        };

        /**
        *   Delete data using key and mode
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} key key of the data to be deleted
        *   @param {string} mode mode in which the data was saved
        */
        this.deleteByUsingKeyAndMode = function (successHandler, errorHandler, key, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "deleteByUsingKeyAndModeLocal", [key, mode]);
        };

        /**
        *   Delete all data in a mode
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} mode mode for which the data needs to be deleted
        */
        this.deleteAllUsingMode = function (successHandler, errorHandler, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "deleteAllUsingModeLocal", [mode]);
        };

        /**
        *   Returns the sets of keys for a given mode
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} mode mode for which the key set is required
        */
        this.keySetForMode = function (successHandler, errorHandler, mode) {
			return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "keySetForModeLocal", [mode]);
        };
    },

        /**
         MASSecureCloudStorage which has the interfaces mapped to the native MASStorage class. These functions would store and fetch data from cloud storage.
         */
    MASSecureCloudStorage: function () {
		'use strict';

        /**
        *   Saves the provided data to cloud storage
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} mime mime type of the data
        *   @param {string} key key with which the data is to be stored
        *   @param {string} data data payload
        *   @param {string} mode mode in which the data is to be saved
        */
        this.save = function (successHandler, errorHandler, mime, key, data, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "saveToCloud", [mime, key, data, mode]);
        };

        /**
        *   Find data using key and mode
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} key key of the data to be searched
        *   @param {string} mode mode in which the data was saved
        */
        this.findByUsingKeyAndMode = function (successHandler, errorHandler, key, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "findByUsingKeyAndModeCloud", [key, mode]);
        };

        /**
        *   Delete data using key and mode
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} key key of the data to be deleted
        *   @param {string} mode mode in which the data was saved
        */
        this.deleteByUsingKeyAndMode = function (successHandler, errorHandler, key, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "deleteByUsingKeyAndModeCloud", [key, mode]);
        };

        /**
        *   Returns the sets of keys for a given mode
        *   @param {function} successHandler user defined success callback
        *   @param {function} errorHandler user defined error callback
        *   @param {string} mode mode for which the key set is required
        */
        this.keySetForMode = function (successHandler, errorHandler, mode) {
            return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "keySetForModeCloud", [mode]);
        };
	}
};
