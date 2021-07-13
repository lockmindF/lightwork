package com.example.lightwork11;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static com.example.lightwork11.MainActivity.STATE_MESSAGE_RECEIVED;
import static com.example.lightwork11.secDevice.handler;

public class mainDevice extends AppCompatActivity {
    TextView timer;
    EditText timerET;
    Button chooseimg, yellowBT, savebt, blue, green, red, lblue, white, sendimg;
    ImageView img;
    BluetoothSocket bluetoothSocket;
    public int intTM = 0;


    public static SendReceive sendReceive1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_device);

        Intent intent = getIntent();
        Intent intent1 = getIntent();
        String btsocket = intent.getStringExtra("btsocket");
        sendimg = (Button) findViewById(R.id.sendimg);
        chooseimg = (Button) findViewById(R.id.chooseimg);
        timer = (TextView) findViewById(R.id.timer);
        img = (ImageView) findViewById(R.id.imageView2);
        yellowBT = (Button) findViewById(R.id.yellowBT);
        savebt = (Button) findViewById(R.id.timerEtBT);
        timerET = (EditText) findViewById(R.id.timerET);
        blue = (Button) findViewById(R.id.blue);
        green = (Button) findViewById(R.id.green);
        red = (Button) findViewById(R.id.red);
        lblue = (Button) findViewById(R.id.lblue);
        white = (Button) findViewById(R.id.white);
        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });

        savebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strTimer = timerET.getText().toString();
                int intTimer = Integer.parseInt(strTimer);
                intTM = intTimer;
            }


        });

        sendimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                int subArraySize = 400;
                MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                for (int i = 0; i < imageBytes.length; i += subArraySize) {
                    byte[] tempArray;

                    tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                    MainActivity.sendReceive.write(tempArray);
                }
                new CountDownTimer(intTM * 1000 - 4500, 1000) {
                    public void onTick(long l) {
                        timer.setText("In progress");
                    }

                    public void onFinish() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] imageBytes = stream.toByteArray();
                        int subArraySize = 400;
                        MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                        for (int i = 0; i < imageBytes.length; i += subArraySize) {
                            byte[] tempArray;

                            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                            MainActivity.sendReceive.write(tempArray);
                            timer.setText("Done");
                        }
                    }
                }.start();
            }
        });

        yellowBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                int subArraySize = 400;
                MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                for (int i = 0; i < imageBytes.length; i += subArraySize) {
                    byte[] tempArray;

                    tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                    MainActivity.sendReceive.write(tempArray);
                }
                new CountDownTimer(intTM * 1000 - 4500, 1000) {
                    public void onTick(long l) {
                        timer.setText("In progress");
                    }

                    public void onFinish() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] imageBytes = stream.toByteArray();
                        int subArraySize = 400;
                        MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                        for (int i = 0; i < imageBytes.length; i += subArraySize) {
                            byte[] tempArray;

                            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                            MainActivity.sendReceive.write(tempArray);
                            timer.setText("Done");
                        }
                    }
                }.start();
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.red1);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                int subArraySize = 400;
                MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                for (int i = 0; i < imageBytes.length; i += subArraySize) {
                    byte[] tempArray;

                    tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                    MainActivity.sendReceive.write(tempArray);
                }
                new CountDownTimer(intTM * 1000 - 4000, 1000) {
                    public void onTick(long l) {
                        timer.setText("In progress");
                    }

                    public void onFinish() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] imageBytes = stream.toByteArray();
                        int subArraySize = 400;
                        MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                        for (int i = 0; i < imageBytes.length; i += subArraySize) {
                            byte[] tempArray;

                            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                            MainActivity.sendReceive.write(tempArray);
                            timer.setText("Done");
                        }
                    }
                }.start();
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blue);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                int subArraySize = 400;
                MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                for (int i = 0; i < imageBytes.length; i += subArraySize) {
                    byte[] tempArray;

                    tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                    MainActivity.sendReceive.write(tempArray);
                }
                new CountDownTimer(intTM * 1000 - 4000, 1000) {
                    public void onTick(long l) {
                        timer.setText("In progress");
                    }

                    public void onFinish() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] imageBytes = stream.toByteArray();
                        int subArraySize = 400;
                        MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                        for (int i = 0; i < imageBytes.length; i += subArraySize) {
                            byte[] tempArray;

                            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                            MainActivity.sendReceive.write(tempArray);
                            timer.setText("Done");
                        }
                    }
                }.start();
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.green);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                int subArraySize = 400;
                MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                for (int i = 0; i < imageBytes.length; i += subArraySize) {
                    byte[] tempArray;

                    tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                    MainActivity.sendReceive.write(tempArray);
                }
                new CountDownTimer(intTM * 1000 - 4000, 1000) {
                    public void onTick(long l) {
                        timer.setText("In progress");
                    }

                    public void onFinish() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] imageBytes = stream.toByteArray();
                        int subArraySize = 400;
                        MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                        for (int i = 0; i < imageBytes.length; i += subArraySize) {
                            byte[] tempArray;

                            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                            MainActivity.sendReceive.write(tempArray);
                            timer.setText("Done");
                        }
                    }
                }.start();
            }
        });
        lblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lblue);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                int subArraySize = 400;
                MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                for (int i = 0; i < imageBytes.length; i += subArraySize) {
                    byte[] tempArray;

                    tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                    MainActivity.sendReceive.write(tempArray);
                }
                new CountDownTimer(intTM * 1000 - 4000, 1000) {
                    public void onTick(long l) {
                        timer.setText("In progress");
                    }

                    public void onFinish() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] imageBytes = stream.toByteArray();
                        int subArraySize = 400;
                        MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                        for (int i = 0; i < imageBytes.length; i += subArraySize) {
                            byte[] tempArray;

                            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                            MainActivity.sendReceive.write(tempArray);
                            timer.setText("Done");
                        }
                    }
                }.start();
            }
        });
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.white);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageBytes = stream.toByteArray();
                int subArraySize = 400;
                MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                for (int i = 0; i < imageBytes.length; i += subArraySize) {
                    byte[] tempArray;

                    tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                    MainActivity.sendReceive.write(tempArray);
                }
                new CountDownTimer(intTM * 1000 - 4000, 1000) {
                    public void onTick(long l) {
                        timer.setText("In progress");
                    }

                    public void onFinish() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                        byte[] imageBytes = stream.toByteArray();
                        int subArraySize = 400;
                        MainActivity.sendReceive.write(String.valueOf(imageBytes.length).getBytes());
                        for (int i = 0; i < imageBytes.length; i += subArraySize) {
                            byte[] tempArray;

                            tempArray = Arrays.copyOfRange(imageBytes, i, Math.min(imageBytes.length, i + subArraySize));
                            MainActivity.sendReceive.write(tempArray);
                            timer.setText("Done");
                        }
                    }
                }.start();
            }
        });

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



    public static class SendReceive extends Thread{
        private BluetoothSocket bluetoothSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public SendReceive(BluetoothSocket socket){
            bluetoothSocket = socket;
            Log.d("qwe2", socket.toString());
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
//                            Intent intent1 = new Intent(mainDevice.this, secDevice.class);
//                            intent1.putExtra("numberOfBytes", numberOfBytes);
//                            intent1.putExtra("int",-1);
//                            intent1.putExtra("buffer", buffer);

//                            startActivity(intent1);
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