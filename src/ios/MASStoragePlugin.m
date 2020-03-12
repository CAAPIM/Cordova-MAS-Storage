/**
 * Copyright (c) 2016 CA, Inc. All rights reserved.
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 *
 */

//
//  MASStoragePlugin.m
//

#import "MASStoragePlugin.h"

#import <MASStorage/MASStorage.h>


static NSString *const MASStoragePluginMimeTypeText = @"text/plain";
static NSString *const MASStoragePluginMimeTypeJSON = @"application/json";
static NSString *const MASStoragePluginMimeByte = @"application/octet-stream";
static NSString *const MASStoragePluginMimeTypePNG = @"image/png";
static NSString *const MASStoragePluginMimeTypeJPG = @"image/jpg";


@implementation MASStoragePlugin


#pragma mark - Lifecycle

- (void)pluginInitialize
{
    
}


#pragma mark - LocalStorage

#pragma mark - Save/Update methods

- (void)saveToLocal:(CDVInvokedUrlCommand *)command {
    
    NSString *mimeType = [command.arguments objectAtIndex:0];
    NSString *key = [command.arguments objectAtIndex:1];
    MASObject *object = [command.arguments objectAtIndex:2];
    MASLocalStorageSegment mode =
    (MASLocalStorageSegment)[[command.arguments objectAtIndex:3] integerValue];
    
    [MAS enableLocalStorage];
    [MASLocalStorage saveObject:object withKey:key type:mimeType mode:mode
                     completion:
     ^(BOOL success, NSError *error){
         
         if (success) {
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];

             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else if (error) {
             
             NSDictionary *errorInfo = @{@"errorCode":[NSNumber numberWithInteger:[error code]],
                                         @"errorMessage":[error localizedDescription]};
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorInfo];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
     }];
}


- (void)saveSecurelyToLocal:(CDVInvokedUrlCommand*)command {
    
    NSString *mimeType = [command.arguments objectAtIndex:0];
    NSString *key = [command.arguments objectAtIndex:1];
    NSObject *object = [command.arguments objectAtIndex:2];
    MASLocalStorageSegment mode =
        (MASLocalStorageSegment)[[command.arguments objectAtIndex:3] integerValue];
    NSString *password = [command.arguments objectAtIndex:4];
    
    [MASLocalStorage enableLocalStorage];
    [MASLocalStorage saveObject:object withKey:key type:mimeType mode:mode password:password
                     completion:
     ^(BOOL success, NSError *error){
         
         if (success) {
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else if (error) {
             
             NSDictionary *errorInfo = @{@"errorCode":[NSNumber numberWithInteger:[error code]],
                                         @"errorMessage":[error localizedDescription]};
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorInfo];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
     }];
}


#pragma mark - Find methods

- (void)findByUsingKeyAndModeLocal:(CDVInvokedUrlCommand *)command {
    
    NSString *key = [command.arguments objectAtIndex:0];
    MASLocalStorageSegment mode =
    (MASLocalStorageSegment)[[command.arguments objectAtIndex:1] integerValue];
    
    [MAS enableLocalStorage];
    [MASLocalStorage findObjectUsingKey:key mode:mode
                             completion:
     ^(MASObject * _Nullable object, NSError * _Nullable error){
         
         if (object) {
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_OK
                              messageAsDictionary:[self resultWithMASObject:object]];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else if (error) {
             
             NSDictionary *errorInfo = @{@"errorCode":[NSNumber numberWithInteger:[error code]],
                                         @"errorMessage":[error localizedDescription]};
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorInfo];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else {
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsBool:NO];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
     }];
}


- (void)findAllUsingModeLocal:(CDVInvokedUrlCommand*)command {
    
    MASLocalStorageSegment mode =
    (MASLocalStorageSegment)[[command.arguments objectAtIndex:0] integerValue];
    
    [MASLocalStorage enableLocalStorage];
    [MASLocalStorage findObjectsUsingMode:mode
                               completion:
     ^(NSArray * _Nullable objects, NSError * _Nullable error) {
         
         if (objects) {
             
             NSMutableArray *results = [NSMutableArray array];
             for (MASObject *object in objects) {
                 
                 [results addObject:[self resultWithMASObject:object]];
             }
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:results];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else if (error) {
             
             NSDictionary *errorInfo = @{@"errorCode":[NSNumber numberWithInteger:[error code]],
                                         @"errorMessage":[error localizedDescription]};
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorInfo];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
     }];
}


#pragma mark - Delete methods

- (void)deleteByUsingKeyAndModeLocal:(CDVInvokedUrlCommand*)command {
    
    NSString *key = [command.arguments objectAtIndex:0];
    MASLocalStorageSegment mode =
    (MASLocalStorageSegment)[[command.arguments objectAtIndex:1] integerValue];
    
    [MASLocalStorage enableLocalStorage];
    [MASLocalStorage deleteObjectUsingKey:key mode:mode
                               completion:
     ^(BOOL success, NSError * _Nullable error){
         
         if (success) {
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else if (error) {
             
             NSDictionary *errorInfo = @{@"errorCode":[NSNumber numberWithInteger:[error code]],
                                         @"errorMessage":[error localizedDescription]};
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorInfo];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
     }];
}


- (void)deleteAllUsingModeLocal:(CDVInvokedUrlCommand*)command {
    
    MASLocalStorageSegment mode =
    (MASLocalStorageSegment)[[command.arguments objectAtIndex:0] integerValue];
    
    [MASLocalStorage enableLocalStorage];
    [MASLocalStorage deleteAllObjectsUsingMode:mode
                                    completion:
     ^(BOOL success, NSError * _Nullable error){
         
         if (success) {
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:YES];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else if (error) {
             
             NSDictionary *errorInfo = @{@"errorCode":[NSNumber numberWithInteger:[error code]],
                                         @"errorMessage":[error localizedDescription]};
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:errorInfo];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
     }];
}


#pragma mark - keySet methods

- (void)keySetForModeLocal:(CDVInvokedUrlCommand*)command {
    
    MASLocalStorageSegment mode =
    (MASLocalStorageSegment)[[command.arguments objectAtIndex:0] integerValue];
    
    [MASLocalStorage enableLocalStorage];
    [MASLocalStorage findObjectsUsingMode:mode
                               completion:
     ^(NSArray * _Nullable objects, NSError * _Nullable error) {
         
         if (objects) {
             
             NSMutableSet *keySet = [NSMutableSet set];
             for (MASObject *object in objects) {
                 
                 if ([object objectForKey:@"key"])
                     [keySet addObject:[object objectForKey:@"key"]];
             }
             
             CDVPluginResult *result =
                [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsArray:[keySet allObjects]];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
         else if (error) {
             
             NSDictionary *errorInfo = @{@"errorCode":[NSNumber numberWithInteger:[error code]],
                                         @"errorMessage":[error localizedDescription]};
             
             CDVPluginResult *result =
             [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR
                           messageAsDictionary:errorInfo];
             
             [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
         }
     }];
}


#pragma mark - Utility methods

- (NSDictionary *)resultWithMASObject:(MASObject *)object {
    
    NSDictionary *result = [NSDictionary dictionary];
    
    NSString *mimeType = [object objectForKey:@"type"];
    if ([mimeType isEqualToString:MASStoragePluginMimeTypeText] ||
        [mimeType isEqualToString:MASStoragePluginMimeTypeJSON]) {
        
        //
        // Confirm there is a payload
        //
        NSString *value = nil;
        
        //
        // Check if the payload has already been converted to a string or needs
        // to be converted
        //
        if ([[object objectForKey:@"value"] isKindOfClass:[NSString class]]) {
            
            value = (NSString *)[object objectForKey:@"value"];
        }
        else {
            
            // Decode the payload
            value = [[NSString alloc] initWithData:[object objectForKey:@"value"] encoding:NSUTF8StringEncoding];
        }
        
        NSData *plainData = [value dataUsingEncoding:NSUTF8StringEncoding];
        NSString *base64Value = [plainData base64EncodedStringWithOptions:kNilOptions];
        
        result = [NSDictionary dictionaryWithObjectsAndKeys:mimeType, @"mime", base64Value, @"value", nil];
    }
    else if ([mimeType isEqualToString:MASStoragePluginMimeTypePNG] ||
             [mimeType isEqualToString:MASStoragePluginMimeTypeJPG] ||
             [mimeType isEqualToString:MASStoragePluginMimeByte]) {
        
        if ([[object objectForKey:@"value"] isKindOfClass:[NSData class]]) {
            
            NSData *imageData = [object objectForKey:@"value"];
            
            //
            // Check if there is metadata inside the payload. If so, change the way of loading the image
            //
            NSString *base64String =
            [[NSString alloc] initWithData:imageData encoding:NSUTF8StringEncoding];
            
//            if (base64String) {
//                
//                NSURL *imageUrl = [NSURL URLWithString:base64String];
//                imageData = [NSData dataWithContentsOfURL:imageUrl];
//            }
//            
//            NSString *base64Value = [imageData base64EncodedStringWithOptions:kNilOptions];
            
            result = [NSDictionary dictionaryWithObjectsAndKeys:mimeType, @"mime", base64String, @"value", nil];
        }
    }
    
    return result;
}



@end
