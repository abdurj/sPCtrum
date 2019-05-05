package com.example.spctrum;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Uri imageUri;
    Bitmap rbaBitmap, imageBitmap;
    final int REQUEST_IMAGE_CAPURE = 101;


    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }

    public void openGallery(View v) {
        Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(myIntent, 100);
    }

    public void takePicture(View v){
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
        else if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void removeGreen(View v) {

        if (imageView != null) {


            Mat rbMat = new Mat();

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inDither = false;
            o.inSampleSize = 4;

            int width = imageBitmap.getWidth();
            int height = imageBitmap.getHeight();
            rbaBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

            Utils.bitmapToMat(imageBitmap, rbMat);

            Scalar s = new Scalar(0, 255, 0);

            Core.subtract(rbMat, s, rbMat);
            /*
                List<Mat> rgbMat = new ArrayList<>(3);
                Core.split(rbMat, rgbMat);

                Mat r = rgbMat.get(0);
                Mat g = rgbMat.get(1);
                Mat b = rgbMat.get(2);
                g = Mat.zeros(g.rows(),g.cols(), CV_8UC1);
                rgbMat.set(1, g);

                Core.merge(rgbMat,rbMat);
            */
            Utils.matToBitmap(rbMat, rbaBitmap);

            imageView.setImageBitmap(rbaBitmap);

        }


    }

}
