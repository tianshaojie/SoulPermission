package com.qw.sample;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void before(View view) {
        startActivity(new Intent(MainActivity.this, BeforeActivity.class));
    }

    public void after(View view) {
        startActivity(new Intent(MainActivity.this, AfterActivity.class));
    }

    public void apiGuide(View view) {
        startActivity(new Intent(MainActivity.this, ApiGuideActivity.class));
    }

    public void logFilePath(View view) {
        // 内部存储
        String internalFileDir = getFilesDir().getAbsolutePath();
        String internalCacheDir = getCacheDir().getAbsolutePath();
        Log.i("MainActivity", "internalFileDir = " + internalFileDir);
        Log.i("MainActivity", "internalCacheDir = " + internalCacheDir);

        // 私有存储
        String externalCacheDir = getExternalCacheDir().getAbsolutePath();
        String picturePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        String downloadPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

        Log.i("MainActivity", "externalCacheDir = " + externalCacheDir);
        Log.i("MainActivity", "picturePath = " + picturePath);
        Log.i("MainActivity", "downloadPath = " + downloadPath);

        writeToInternalCache();
        writeToExternalCache();
    }

    public void writeToInternalCache() {
        String internalFileDir = getCacheDir().getAbsolutePath();
        File file = new File(internalFileDir, "test.txt");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            for (int i = 0; i < 66; i++) {
                stream.write("Hello world!\n".getBytes());
            }
            stream.flush();
            stream.close();
            Toast.makeText(this, "写入成功！", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeToExternalCache() {
        String externalCacheDir = getExternalCacheDir().getPath();
        File file = new File(externalCacheDir, "test.txt");
        try {
            FileOutputStream stream = new FileOutputStream(file);
            for (int i = 0; i < 66; i++) {
                stream.write("Hello world!\n".getBytes());
            }
            stream.flush();
            stream.close();
            Toast.makeText(this, "写入成功！", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
