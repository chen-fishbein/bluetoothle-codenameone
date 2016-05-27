#import <Foundation/Foundation.h>
#import "BluetoothLePlugin.h"

@interface com_codename1_cordova_CordovaNativeImpl : NSObject {
    
    BluetoothLePlugin* _bluetoothPlugin;
}

-(BOOL)execute:(NSString*)param param1:(NSString*)param1;
-(BOOL)isSupported;
@end
