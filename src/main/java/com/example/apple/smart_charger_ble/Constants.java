package com.example.apple.smart_charger_ble;

/**
 * Created by apple on 2018/3/6.
 */

public class Constants {
    static int MESSAGE_STATE_CHANGE = 1;   // updateUserInterfaceTitle.
    static int MESSAGE_DEVICE_NAME = 2;    // Send messages about device name state.
    static int MESSAGE_TOAST = 3;          // Send warning message display on Toast state.
    static int MESSAGE_READ = 4;           // Send message read state.
    static int MESSAGE_WRITE = 5;          // Send message write state.

    static String DEVICE_NAME = "device name";
    static String TOAST = "toast";
}
