#import <Foundation/Foundation.h>
#import "com_codename1_cordova_CordovaNativeImpl.h"
#import "BluetoothLeCommandDelegateImpl.h"
#import <objc/message.h>




@implementation com_codename1_cordova_CordovaNativeImpl



-(BluetoothLePlugin*)getBluetoothPlugin {
    if (_bluetoothPlugin == nil) {
        _bluetoothPlugin = [[BluetoothLePlugin alloc] init];
        _bluetoothPlugin.commandDelegate = [[BluetoothLeCommandDelegateImpl alloc] init];
    }
    return _bluetoothPlugin;
}

-(BOOL)execute:(NSString*)param param1:(NSString*)param1{
    
    NSLog(@"Cordova exec: param %@ param1 %@", param, param1);
    NSData* data = [param1 dataUsingEncoding:NSUTF8StringEncoding];
    NSError *e;
    NSArray *array = nil;
    if (param1 == nil || [param1 length] == 0) {
        array = [NSArray array];
    } else {
        array = [NSArray arrayWithObjects:[NSJSONSerialization JSONObjectWithData:data options:nil error:&e], nil];
    }
    NSLog(@"Args %@", array);
    //NSMutableArray *marray = [NSMutableArray arrayWithArray:array];
    //[marray replaceObjectAtIndex:0 withObject:param];
    CDVInvokedUrlCommand* command = [[CDVInvokedUrlCommand alloc] initWithArguments:array callbackId:param className:@"BluetoothLePlugin" methodName:param];
    //[CDVInvokedUrlCommand commandFromJson:marray];
    //command.callbackId = param; // A hack to get the correct callback id.
    NSLog(@"Command created %@ %@ %@ %@", command, command.callbackId, command.className, command.methodName);
    return [self execute:command];
}

- (BOOL)execute:(CDVInvokedUrlCommand*)command
{
    BOOL retVal = YES;
    double started = [[NSDate date] timeIntervalSince1970] * 1000.0;
    CDVPlugin* obj = [self getBluetoothPlugin];
    NSString* methodName = [NSString stringWithFormat:@"%@:", command.methodName];
    SEL normalSelector = NSSelectorFromString(methodName);
    if ([obj respondsToSelector:normalSelector]) {
        // [obj performSelector:normalSelector withObject:command];
        NSLog(@"Firing selector %@", methodName);
        ((void (*)(id, SEL, id))objc_msgSend)(obj, normalSelector, command);
    } else {
        // There's no method to call, so throw an error.
        NSLog(@"ERROR: Method '%@' not defined in Plugin '%@'", methodName, command.className);
        retVal = NO;
    }
    double elapsed = [[NSDate date] timeIntervalSince1970] * 1000.0 - started;
    if (elapsed > 10) {
        NSLog(@"THREAD WARNING: ['%@'] took '%f' ms. Plugin should use a background thread.", command.className, elapsed);
    }
    return retVal;
}
-(BOOL)isSupported{
    return YES;
}

-(void)dealloc {
    if (_bluetoothPlugin != nil) {
        [_bluetoothPlugin dispose];
        _bluetoothPlugin = nil;
    }
    [super dealloc];
}
@end
