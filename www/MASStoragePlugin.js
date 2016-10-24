cordova.define("cordova-plugin-mas-storage.MASStoragePlugin", function(require, exports, module) {
    //
    //  MASStoragePlugin.js
    //
    //  Copyright (c) 2016 CA, Inc.
    //
    //  This software may be modified and distributed under the terms
    //  of the MIT license. See the LICENSE file for details.
    //
    var MASStoragePlugin =
    {        
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
        MASSecureLocalStorage: function()
        {
             this.save = function(successHandler, errorHandler, mime, key, data, mode)
            {
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "saveToLocal", [mime,key,data,mode]);
            };

            this.saveSecurely = function(successHandler, errorHandler, mime, key, data, mode, password)
            {
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "saveSecurelyToLocal", [mime,key,data,mode,password]);
            };

            this.findByUsingKeyAndMode = function(successHandler, errorHandler, key, mode)
            {               
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "findByUsingKeyAndModeLocal", [key,mode]);
            };

            this.findAllUsingMode = function(successHandler, errorHandler, mode)
            {               
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "findAllUsingModeLocal", [mode]);
            };

            this.deleteByUsingKeyAndMode = function(successHandler, errorHandler, key, mode)
            {               
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "deleteByUsingKeyAndModeLocal", [key,mode]);
            };

            this.deleteAllUsingMode = function(successHandler, errorHandler, mode)
            {               
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "deleteAllUsingModeLocal", [mode]);
            };

            this.keySetForMode = function(successHandler, errorHandler, mode)
            {               
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "keySetForModeLocal", [mode]);
            };
        },

        /**
         MASSecureCloudStorage which has the interfaces mapped to the native MASStorage class. These functions would store and fetch data from cloud storage.
         */
        MASSecureCloudStorage: function()
        {
            this.save = function(successHandler, errorHandler, mime, key, data, mode)
            {
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "saveToCloud", [mime,key,data,mode]);
            };

            this.findByUsingKeyAndMode = function(successHandler, errorHandler, key, mode)
            {
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "findByUsingKeyAndModeCloud", [key,mode]);
            };

            this.findAllUsingMode = function(successHandler, errorHandler, mode)
            {               
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "findAllUsingModeCloud", [mode]);
            };

            this.deleteByUsingKeyAndMode = function(successHandler, errorHandler, key, mode)
            {               
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "deleteByUsingKeyAndModeCloud", [key,mode]);
            };

            this.keySetForMode = function(successHandler, errorHandler, mode)
            {
                return Cordova.exec(successHandler, errorHandler, "MASStoragePlugin", "keySetForModeCloud", [mode]);
            };
        }
    };
module.exports = MASStoragePlugin;

});
