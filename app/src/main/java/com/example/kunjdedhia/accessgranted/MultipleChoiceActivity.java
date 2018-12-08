package com.example.android.fingerflow;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MultipleChoiceActivity extends AppCompatActivity {

    private EditText finger;
    private Button button;
    private TextView view;
    private OutputStream outputStream;
    private InputStream inputStream;
    private final String DEVICE_ADDRESS = "98:D3:36:80:F1:03";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    byte buffer[];
    boolean stopThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice);

        finger = (EditText)findViewById(R.id.fingerno);
        view = (TextView)findViewById(R.id.display);
        button = (Button)findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v)
            {
                Intent morseCodeIntent = new Intent(MultipleChoiceActivity.this, MainActivity.class);
                startActivity(morseCodeIntent);

            }
        });

        if(BTinit())
        {
            if(BTconnect())
            {
                Toast.makeText(getApplicationContext(),"Connected to device",Toast.LENGTH_LONG).show();
                beginListenForData();
            }
        }
    }


    public boolean BTinit()
    {
        boolean found=false;
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",Toast.LENGTH_LONG).show();
            finish();
        }
        if(!bluetoothAdapter.isEnabled())
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"Tap on Connect again",Toast.LENGTH_SHORT).show();

        }
        else
        {
            for (BluetoothDevice iterator : bondedDevices)
            {
                if(iterator.getAddress().equals(DEVICE_ADDRESS))
                {
                    device=iterator;
                    found=true;
                    break;

                }
            }
        }
        return found;
    }

    public boolean BTconnect()
    {
        boolean connected=true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected=false;
        }
        if(connected)
        {
            try {
                outputStream=socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream=socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connected;
    }



    void beginListenForData()
    {

        String string = "g";
        string.concat("\n");
        try {
            outputStream.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        int byteCount = inputStream.available();
                        if(byteCount > 1)
                        {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string=new String(rawBytes,"UTF-8");
                            handler.post(new Runnable() {
                                public void run()
                                {

                                    //code here
                                    Log.v("Debug", string);
//                                    string.replaceAll(" ", "");

                                    if (string.equals("One")) {
                                        view.setText("A");
                                    }
                                    if(string.equals("Two"))
                                    {
                                        view.setText("B");
                                    }
                                    if(string.equals("Three"))
                                    {
                                        view.setText("C");
                                    }
                                    if(string.equals("Four"))
                                    {
                                        view.setText("D");

                                    }
//                                    else
//                                    {
//                                        view.setText("Invalid Option");
//                                    }
                                }
                            });
                        }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }
}

