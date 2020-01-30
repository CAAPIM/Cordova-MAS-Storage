/**
 * Copyright (c) 2016 CA, Inc. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */

//
//  MASStoragePlugin.h
//


#import <Cordova/CDV.h>



@interface MASStoragePlugin : CDVPlugin



#pragma mark - LocalStorage

#pragma mark - Save/Update methods
/**
 *  Save to local database
 */
- (void)saveToLocal:(CDVInvokedUrlCommand*)command;

/**
 *  Save securely to local database
 */
- (void)saveSecurelyToLocal:(CDVInvokedUrlCommand*)command;


#pragma mark - Find methods

/**
 *  Search local database for key in given mode
 */
- (void)findByUsingKeyAndModeLocal:(CDVInvokedUrlCommand*)command;

/**
 *  List all entries in given mode
 */
- (void)findAllUsingModeLocal:(CDVInvokedUrlCommand*)command;


#pragma mark - Delete methods

/**
 *  Delete a key in given mode
 */
- (void)deleteByUsingKeyAndModeLocal:(CDVInvokedUrlCommand*)command;

/**
 *  Delete all entries in given mode
 */
- (void)deleteAllUsingModeLocal:(CDVInvokedUrlCommand*)command;


#pragma mark - keySet methods
/**
 *  List all keys in given mode
 */
- (void)keySetForModeLocal:(CDVInvokedUrlCommand*)command;



@end
