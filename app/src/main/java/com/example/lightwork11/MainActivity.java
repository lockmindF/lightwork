package com.example.lightwork11;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends Activity {



    public int intTM;
    public int value;
    private Button MainDev;
    private  Button SupDev;
    Button listen, send, listDevice, back, bt, timerETBT, green,blue, red,lblue, white;
    ListView listView;
    TextView status, msg_box, timer;
    EditText writeMsg, timerET;
    ImageView imageView, blackIm,www;

    public static mainDevice.SendReceive sendReceive;


    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;




    static final int STATE_LISTENING = 1;
    static final int STATE_CONNECTING = 2;
    static final int STATE_CONNECTED = 3;
    static final int STATE_CONNECTED__FAILED = 4;
    static final int STATE_MESSAGE_RECEIVED = 5;



    int REQUEST_ENABLE_BLUETOOTH = 1;

    private  static final String APP_NAME = "BTChat";
    private static final UUID MY_UUID=UUID.fromString("9391b7c0-dca3-11ea-87d0-0242ac130003");
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);



        MainDev = (Button) findViewById(R.id.MainDev);
        MainDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();



                intent.setClass(MainActivity.this, mainDevice.class);
                startActivity(intent);

            }
        });

        SupDev = (Button) findViewById(R.id.SupDev);
        SupDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();



                intent.setClass(MainActivity.this, secDevice.class);
                startActivity(intent);
            }
        });



        findViewByIdes();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!bluetoothAdapter.isEnabled()){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent,REQUEST_ENABLE_BLUETOOTH);
        }

        implementListeners();



    }



    private void implementListeners() {
        listDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<BluetoothDevice> bt =bluetoothAdapter.getBondedDevices();
                String[] strings = new String[bt.size()];
                btArray=new BluetoothDevice[bt.size()];
                int index=0;

                if(bt.size()>0){
                    for(BluetoothDevice device : bt) {
                        btArray[index]=device;
                        strings[index]=device.getName();
                        index++;
                    }
                    ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,strings);
                    listView.setAdapter(arrayAdapter);
                }
            }
        });

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ServerClass serverClass = new ServerClass();
                serverClass.start();

            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClientClass clientClass=new ClientClass(btArray[i]);
                clientClass.start();

                status.setText("Connecting");
            }
        });


    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case STATE_LISTENING:
                    status.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    status.setText("Connecting");
                    break;
                case  STATE_CONNECTED:
                    status.setText("Connected");
                    MainDev.setVisibility(View.VISIBLE);
                    SupDev.setVisibility(View.VISIBLE);
                    break;
                case  STATE_CONNECTED__FAILED:
                    status.setText("Connection failed");
                    break;



            }

            return true;
        }
    });

    private void findViewByIdes(){
        listen=(Button) findViewById(R.id.listen);
        listView=(ListView) findViewById(R.id.listview);
        status=(TextView) findViewById(R.id.status);
        listDevice = (Button) findViewById(R.id.listDevices);


    }


    private class ServerClass extends Thread{
        private BluetoothServerSocket serverSocket;

        public  ServerClass(){
            try {
                serverSocket=bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME,MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            BluetoothSocket socket = null;

            while (socket==null){
                try {
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTING;
                    handler.sendMessage(message);
                    socket=serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTED__FAILED;
                    handler.sendMessage(message);
                }

                if(socket!=null){
                    Message message=Message.obtain();
                    message.what=STATE_CONNECTED;
                    handler.sendMessage(message);
                    new Singleton(socket);
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sendReceive = new mainDevice.SendReceive(socket);
                    sendReceive.start();


                    break;
                }
            }
        }
    }

    public class ClientClass extends Thread{
        private BluetoothDevice device;
        public BluetoothSocket socket;


        public ClientClass(BluetoothDevice device){
            device=device;

            try {
                socket=device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
                new Singleton(socket);
                Log.d("qwe1", socket.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public  void  run(){
            try {
                socket.connect();
                Message message=Message.obtain();
                message.what=STATE_CONNECTED;
                handler.sendMessage(message);
                sendReceive = new mainDevice.SendReceive(socket);
                sendReceive.start();


            } catch (IOException e) {
                e.printStackTrace();
                Message message=Message.obtain();
                message.what=STATE_CONNECTED__FAILED;
                handler.sendMessage(message);
            }
        }
    }

    public static final class Singleton {
        private static Singleton instance;
        public BluetoothSocket value;

        private Singleton(BluetoothSocket value) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            this.value = value;
        }


    }



}