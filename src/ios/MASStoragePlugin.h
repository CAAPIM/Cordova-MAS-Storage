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

- (void)saveToLocal:(CDVInvokedUrlCommand*)command;


- (void)saveSecurelyToLocal:(CDVInvokedUrlCommand*)command;


#pragma mark - Find methods

- (void)findByUsingKeyAndModeLocal:(CDVInvokedUrlCommand*)command;


- (void)findAllUsingModeLocal:(CDVInvokedUrlCommand*)command;


#pragma mark - Delete methods

- (void)deleteByUsingKeyAndModeLocal:(CDVInvokedUrlCommand*)command;


- (void)deleteAllUsingModeLocal:(CDVInvokedUrlCommand*)command;


#pragma mark - keySet methods

- (void)keySetForModeLocal:(CDVInvokedUrlCommand*)command;


#pragma mark - CloudStorage

#pragma mark - Save/Update methods

- (void)saveToCloud:(CDVInvokedUrlCommand*)command;


#pragma mark - Find methods

- (void)findByUsingKeyAndModeCloud:(CDVInvokedUrlCommand*)command;


- (void)findAllUsingModeCloud:(CDVInvokedUrlCommand*)command;


#pragma mark - Delete methods

- (void)deleteByUsingKeyAndModeCloud:(CDVInvokedUrlCommand*)command;


#pragma mark - keySet methods

- (void)keySetForModeCloud:(CDVInvokedUrlCommand*)command;


@end
