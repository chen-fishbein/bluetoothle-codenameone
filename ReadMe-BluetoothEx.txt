BluetoothEx

Extends Bluetooth (changing the scope on plugin to protected) by adding the peripheral modes. A couple of hellper methods simplfy creating a service and adding characteristics.
Adds a second demo program that demonstrates the commands showing the asynchronous nature of the architecture of the plugin.

The Central Life Cycle is:

     initialize → startScan → find “peripheral” → stopScan → connect → read/write/subscribe/unsubscribe/services/characteristic/descriptor/disconnect/close → repeat

The Peripheral Life Cycle is:
    initialize → initPeripheral → addService → startAdvertising → respond to calback events → stopAdvertising → removeService/removeAllServices

