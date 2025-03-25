/********* PluginAppIcon.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

NSString* MSG_SUCCESS_CHANGE_ICON = @"App icon changed successfully.";
NSString* MSG_ERROR_CHANGE_ICON = @"An error occurred while changing app icon.";
NSString* MSG_SUCCESS_RESET_ICON = @"App icon reset successfully.";
NSString* MSG_ERROR_RESET_ICON = @"An error occurred while resetting app icon.";
NSString* MSG_ERROR_PARSE_ARGS = @"Missing or invalid arguments.";

@interface PluginAppIcon : CDVPlugin {
    // Member variables go here.
}

- (void)changeIcon:(CDVInvokedUrlCommand*)command;
- (void)resetIcon:(CDVInvokedUrlCommand*)command;
- (void)getAppName:(CDVInvokedUrlCommand*)command;

@end

@implementation PluginAppIcon

- (void)changeIcon:(CDVInvokedUrlCommand*)command
{
    NSDictionary* options = [command.arguments objectAtIndex:0];

    if (options != nil && options.count > 0) {
        NSLog(@"ChangeIcon...");
        NSString* enableName = [[options objectForKey:@"enableName"] string];
        [UIApplication.sharedApplication setAlternateIconName:enableName completionHandler:^(NSError * _Nullable error) {
            if (error) {
                NSLog(MSG_ERROR_CHANGE_ICON);
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:MSG_ERROR_CHANGE_ICON] callbackId:command.callbackId];
            }
            else {
                NSLog(MSG_SUCCESS_CHANGE_ICON);
                [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:MSG_SUCCESS_CHANGE_ICON] callbackId:command.callbackId];
            }
        }];
        
    } else {
        NSLog(MSG_ERROR_PARSE_ARGS);
        [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:MSG_ERROR_PARSE_ARGS] callbackId:command.callbackId];
    }
}

- (void)resetIcon:(CDVInvokedUrlCommand *)command
{
    NSLog(@"ResetIcon...");
    [UIApplication.sharedApplication setAlternateIconName:nil completionHandler:^(NSError * _Nullable error) {
        if (error) {
            NSLog(MSG_SUCCESS_RESET_ICON);
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:MSG_ERROR_RESET_ICON] callbackId:command.callbackId];
        }
        else {
            NSLog(MSG_ERROR_RESET_ICON);
            [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:MSG_SUCCESS_RESET_ICON] callbackId:command.callbackId];
        }
    }];
}

- (void)getAppName:(CDVInvokedUrlCommand *)command
{
    NSString* appName = UIApplication.sharedApplication.alternateIconName;
    NSLog(@"AlternateIconName: %@", appName);
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:appName] callbackId:command.callbackId];
}

@end
