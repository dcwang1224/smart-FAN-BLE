package com.example.apple.smart_charger_ble;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Switch sw_ble;
    private BluetoothAdapter mBluetoothAdapter;
    private AlertDialog.Builder dialog_list;
    private final int REQUEST_ENABLE_BT=1;
    Intent intent_on;
    ArrayList<String> discoveredDevicesAdapter = new ArrayList<String>();
    String[] RequestBLEPermissions = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        sw_ble = (Switch)findViewById(R.id.sw_ble);

        ActivityCompat.requestPermissions(this, RequestBLEPermissions, 642);

        if(mBluetoothAdapter.isEnabled()) {
              sw_ble.setChecked(true);
              Log.d("Tag", "bluetooth enabled");
              discover();
        }else {
            sw_ble.setChecked(false);
            Log.d("Tag", "bluetooth disabled");
        }

        sw_ble.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(mBluetoothAdapter == null) {
                        Toast.makeText(sw_ble.getContext(), "Device dosen't support Bluetooth", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!mBluetoothAdapter.isEnabled()) {
                        Intent mIntentOpenBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(mIntentOpenBT, REQUEST_ENABLE_BT);
                    }
                }else {
                    mBluetoothAdapter.disable();
                }

            }
        });

    }


    private void discover() {
            if(mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            } else {
                mBluetoothAdapter.startDiscovery();
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(discoveryFinishReceiver, filter);
                Log.d("Tag", "under discovery");
            }
    }

    public final BroadcastReceiver discoveryFinishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.d("Tag", "bluetooth Action Found");
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                discoveredDevicesAdapter.add(device.getName());

            }
        }
    };

    public void onScanList (View view) {
        AlertDialog.Builder dialog_scan_list;
        String[] device_array = discoveredDevicesAdapter.toArray(new String[0]);
        mBluetoothAdapter.cancelDiscovery();
        dialog_scan_list = new AlertDialog.Builder(MainActivity.this);
        dialog_scan_list.setTitle("Available Devices");
        dialog_scan_list.setItems(device_array, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              //String aa = discoveredDevicesAdapter.get(which);
             //Toast.makeText(MainActivity.this, "Connect to Name:" + aa, Toast.LENGTH_SHORT).show();

             //String[] name = aa.split("\n");
             Log.d("Tag", "#### begin to connection ######");
             Intent intent = new Intent();
             intent.setClass(MainActivity.this, control_platform.class);
             startActivity(intent);
        //for (BluetoothDevice device : setPairedDevices) {
        //    if(device.getName().matches(name[0])) {
        //        Log.d("Tag", "#### Start connection ######");
        //bChatService.connect(device, true);           //Start connect to given device defined by user.
        //    }
          }

        });
        dialog_scan_list.show();
    }

    private Handler mHandler = new Handler(){
        //int i = 0;
        @Override
        public void handleMessage(Message msg) {
            String[] memberInfo;

            /*super.handleMessage(msg);
            switch(msg.what){
                case 1:                          // To modify UI display
                    String infoString = (String)msg.obj;
                    //tvResult.setText(infoString);
                    memberInfo = infoString.split("--");
                    tvResult.setText(memberInfo[2]);
                    break;
                case 2:
                    tvResult.setText("Connection Fail!!!");
                    break;
            }*/
            Log.d("Tag", "handleMessage");

        }
    };

}


