package com.example.android.fingerflow;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MorseCodeActivity extends AppCompatActivity {

    private EditText finger2;
    private Button button2;
    private TextView view2;
    private OutputStream outputStream;
    private InputStream inputStream;
    private final String DEVICE_ADDRESS = "98:D3:36:80:F1:03";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    byte buffer[];
    boolean stopThread;
    String temp, s;
    boolean cString = false;
    ArrayList<String> arr1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morse_code);
        finger2 = (EditText) findViewById(R.id.fingerno2);
        view2 = (TextView) findViewById(R.id.display2);
        button2 = (Button) findViewById(R.id.button2);

        temp = "";

        if(BTinit())
        {
            if(BTconnect())
            {
                Toast.makeText(getApplicationContext(),"Connected to device",Toast.LENGTH_LONG).show();
                beginListenForData();

            }
        }

        button2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent morseCodeIntent = new Intent(MorseCodeActivity.this, MorseCodeActivity.class);
                startActivity(morseCodeIntent);
            }
        });


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

//    private void check1(String s)
//    {
//        int number = 0;
//        for(int i = 0; i < s.length();i++)
//        {
//            if(s.charAt(i)=='C')
//            {
//                number = number +1;
//            }
//        }
//        int arraynumber = number - 1;
//
//        int counter = 0;
//        int[] arr = new int[number];
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == 'C') {
//
//                arr[counter] = i;
//                counter = counter + 1;
//
//            }
//        }
//        String word1 = "";
//        for (int j = 0; j < arraynumber; j++) {
//
//            int k = arr[j];
//            int l = arr[j+1];
//
//            String word = s.substring(k+1, l);
//            if (word.equals("ABAA")) {
//                word1 = word1 + "Y";
//            } else if (word.equals("B")) {
//                word1 = word1 + "E";
//            } else if (word.equals("BBB")) {
//                word1 = word1 + "S";
//            } else if (word.equals("AB"))
//            {
//                word1 = word1 + "N";
//            }
//            else if (word.equals("AAA"))
//            {
//                word1 = word1 + "O";
//            }
//            else if (word.equals("AA"))
//            {
//                word1 = word1 + "M";
//            }
//            else if (word.equals("BA"))
//            {
//                word1 = word1 + "A";
//            }
//            else if(word.equals("ABBB"))
//            {
//                word1 = word1 + "B";
//            }
//
//            else if(word.equals("ABAB")) {
//                word1 = word1 + "C";
//            }
//
//            else if(word.equals("ABB")) {
//                word1 = word1 + "D";
//            }
//
//            else if(word.equals("BBAB")) {
//                word1 = word1 + "F";
//            }
//
//            else if(word.equals("AAB")) {
//                word1 = word1 + "G";
//            }
//
//            else if(word.equals("BBBB")) {
//                word1 = word1 + "H";
//            }
//
//            else if(word.equals("BB")) {
//                word1 = word1 + "I";
//            }
//            else if(word.equals("BAAA")) {
//                word1 = word1 + "J";
//            }
//            else if(word.equals("ABA")) {
//                word1 = word1 + "K";
//            }
//            else if(word.equals("BABB")) {
//                word1 = word1 + "L";
//            }
//            else if(word.equals("BAAB")) {
//                word1 = word1 + "P";
//            }
//            else if(word.equals("AABA")) {
//                word1 = word1 + "Q";
//            }
//            else if(word.equals("BAB")) {
//                word1 = word1 + "R";
//            }
//            else if(word.equals("A")) {
//                word1 = word1 + "T";
//            }
//            else if(word.equals("BBA")) {
//                word1 = word1 + "U";
//            }
//            else if(word.equals("BBBA")) {
//                word1 = word1 + "V";
//            }
//
//            else if(word.equals("BAA")) {
//                word1 = word1 + "W";
//            }
//            else if(word.equals("ABBA")) {
//                word1 = word1 + "X";
//            }
//            else if(word.equals("AABB")) {
//                word1 = word1 + "Z";
//            }
//
//
//            else
//            {
//                word1 = word1 + "Error";
//            }
//
//
//        }
//        view2.setText(word1);
//
//    }

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

                                    Log.v("Debug", string);


                                    if (string.equals("One")) {
                                        arr1.add("A");
                                        Log.v("Debug", "Array length " + arr1.size());


                                    }
                                    if(string.equals("Two"))
                                    {
                                        arr1.add("B");
                                        Log.v("Debug", "Array length " + arr1.size());

                                    }
                                    if(string.equals("Three"))
                                    {
                                        arr1.add("C");
                                        Log.v("Debug", "Array length " + arr1.size());
                                        if (cString && arr1.get(arr1.size() - 2).equals("C")){



                                            for (int i = 0; i < arr1.size()-1; i++){
                                                temp += arr1.get(i);

                                            }

                                            s = temp.substring(0, temp.length());
                                            Log.v("Debug", "S " + s);



                                            int number = 0;
                                            for(int i = 0; i < s.length();i++)
                                            {
                                                if(s.charAt(i)=='C')
                                                {
                                                    number = number +1;
                                                }
                                            }
                                            int arraynumber = number - 1;

                                            int counter = 0;
                                            int[] arr = new int[number];
                                            for (int i = 0; i < s.length(); i++) {
                                                if (s.charAt(i) == 'C') {

                                                    arr[counter] = i;
                                                    counter = counter + 1;

                                                }
                                            }

                                            String word1 = "";
                                            for (int j = 0; j < arraynumber; j++) {

                                                int k = arr[j];
                                                int l = arr[j+1];

                                                String word = s.substring(k+1, l);
                                                if (word.equals("ABAA")) {
                                                    word1 = word1 + "Y";
                                                } else if (word.equals("B")) {
                                                    word1 = word1 + "E";
                                                } else if (word.equals("BBB")) {
                                                    word1 = word1 + "S";
                                                } else if (word.equals("AB"))
                                                {
                                                    word1 = word1 + "N";
                                                }
                                                else if (word.equals("AAA"))
                                                {
                                                    word1 = word1 + "O";
                                                }
                                                else if (word.equals("AA"))
                                                {
                                                    word1 = word1 + "M";
                                                }
                                                else if (word.equals("BA"))
                                                {
                                                    word1 = word1 + "A";
                                                }
                                                else if(word.equals("ABBB"))
                                                {
                                                    word1 = word1 + "B";
                                                }

                                                else if(word.equals("ABAB")) {
                                                    word1 = word1 + "C";
                                                }

                                                else if(word.equals("ABB")) {
                                                    word1 = word1 + "D";
                                                }

                                                else if(word.equals("BBAB")) {
                                                    word1 = word1 + "F";
                                                }

                                                else if(word.equals("AAB")) {
                                                    word1 = word1 + "G";
                                                }

                                                else if(word.equals("BBBB")) {
                                                    word1 = word1 + "H";
                                                }

                                                else if(word.equals("BB")) {
                                                    word1 = word1 + "I";
                                                }
                                                else if(word.equals("BAAA")) {
                                                    word1 = word1 + "J";
                                                }
                                                else if(word.equals("ABA")) {
                                                    word1 = word1 + "K";
                                                }
                                                else if(word.equals("BABB")) {
                                                    word1 = word1 + "L";
                                                }
                                                else if(word.equals("BAAB")) {
                                                    word1 = word1 + "P";
                                                }
                                                else if(word.equals("AABA")) {
                                                    word1 = word1 + "Q";
                                                }
                                                else if(word.equals("BAB")) {
                                                    word1 = word1 + "R";
                                                }
                                                else if(word.equals("A")) {
                                                    word1 = word1 + "T";
                                                }
                                                else if(word.equals("BBA")) {
                                                    word1 = word1 + "U";
                                                }
                                                else if(word.equals("BBBA")) {
                                                    word1 = word1 + "V";
                                                }

                                                else if(word.equals("BAA")) {
                                                    word1 = word1 + "W";
                                                }
                                                else if(word.equals("ABBA")) {
                                                    word1 = word1 + "X";
                                                }
                                                else if(word.equals("AABB")) {
                                                    word1 = word1 + "Z";
                                                }


                                                else
                                                {
                                                    word1 = word1 + "Error";
                                                }


                                            }
                                            view2.setText(word1);
                                        } else{
                                            cString = true;
                                        }
                                    }


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