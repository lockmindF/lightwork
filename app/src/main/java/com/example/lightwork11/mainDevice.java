package com.example.lightwork11;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class mainDevice extends AppCompatActivity {
    TextView tv;
    Button chooseimg;
    ImageView img;
    BluetoothSocket bluetoothSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_device);
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        String btsocket = intent.getStringExtra("btsocket");
        chooseimg = (Button) findViewById(R.id.chooseimg);

        img = (ImageView) findViewById(R.id.imageView2);
        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });
        bluetoothSocket = MainActivity.btsocket;
    }




    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class SendReceive extends Thread{
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket){
            bluetoothSocket =socket;
            InputStream tempIn=null;
            OutputStream tempOut=null;

            try {
                tempIn=bluetoothSocket.getInputStream();
                tempOut=bluetoothSocket .getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputStream=tempIn;
            outputStream=tempOut;

        }

        public void run(){
            byte[] buffer = null;
            int numberOfBytes = 0;
            int index = 0;
            boolean flag = true;


            while(true){
                if(flag){
                    try {
                        byte[] temp = new byte[inputStream.available()];
                        if(inputStream.read(temp)>0){
                            numberOfBytes=Integer.parseInt(new String(temp, "UTF-8"));
                            buffer=new byte[numberOfBytes];
                            flag=false;

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        byte[] data = new byte[inputStream.available()];
                        int numbers=inputStream.read(data);

                        System.arraycopy(data,0,buffer,index,numbers);
                        index=index+numbers;


                        if(index==numberOfBytes){
                            handler.obtainMessage(STATE_MESSAGE_RECEIVED,numberOfBytes,-1,buffer).sendToTarget();
                            flag=true;
                            index=0;



                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
                outputStream.flush();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}