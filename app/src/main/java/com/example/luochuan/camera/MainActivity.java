package com.example.luochuan.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String imaPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Button button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent intent = new Intent();
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,100);
                //方式2
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
                    File  file = new File(Environment.getExternalStorageDirectory(),"1012");
                    if(!file.exists()){
                        //mkdir:创建单级目录
                        file.mkdirs();
                    }
                    File imgFile = new File(file,System.currentTimeMillis()+".jpg");
                    imaPath = imgFile.getAbsolutePath();
                    //指定存储路径
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));

                }
                startActivityForResult(intent,200);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==RESULT_OK){}
        }
        if(requestCode==200){
            if(resultCode==RESULT_OK){
                Bitmap bmp = BitmapFactory.decodeFile(imaPath);
                handleBitmap(imaPath);
            }

        }
    }

    /**
     * 图片二次采样的方法
     * @param path
     */
    public Bitmap handleBitmap(String path){
        int sampleSize = 1;
        BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
        bmpOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,bmpOptions);
        int w = bmpOptions.outWidth;
        int h = bmpOptions.outHeight;

        if(w>200){
            sampleSize = 2;

        }
        bmpOptions.inSampleSize = sampleSize;
        bmpOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path,bmpOptions);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
