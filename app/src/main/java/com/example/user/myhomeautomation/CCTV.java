package com.example.user.myhomeautomation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class CCTV extends AppCompatActivity {

    MQTTConnectionToActivity connection;
    String[] topicSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        //MQTT connection
        connection= new MQTTConnectionToActivity(this,topicSub);

        WebView wb = (WebView)findViewById(R.id.cctv_webview);
        wb.loadUrl("http://192.168.100.7:8081");

    }

    public void quit(View view){
        finish();
    }

    public void capture(View v){
        Date name = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", name);

        try {
            // path plus name plus extension type of image
            String ImagePath = this.getFilesDir().toString() + "/" + name + ".jpg";


            // create bitmap screen capture
            View view = getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            // create image file
            File imageFile = new File(ImagePath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();



        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
