package com.example.lightwork11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import static com.example.lightwork11.MainActivity.STATE_MESSAGE_RECEIVED;

public class secDevice extends AppCompatActivity {

    static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sec_device);
        imageView = (ImageView) findViewById(R.id.imageView);

    }

    public static Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff= (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(readBuff,0,msg.arg1);
                    imageView.setImageBitmap(bitmap);


                    break;

            }

            return true;
        }
    });

}
