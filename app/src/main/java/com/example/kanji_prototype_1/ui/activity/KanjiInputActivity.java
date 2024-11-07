package com.example.kanji_prototype_1.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.kanji_prototype_1.R;
import com.example.kanji_prototype_1.ui.utils.ImagePreprocessor;
import com.example.kanji_prototype_1.ui.view.KanjiCanvasView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KanjiInputActivity extends AppCompatActivity {

    private KanjiCanvasView kanjiCanvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_draw);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize OpenCV
        if (!OpenCVLoader.initDebug()) {
            throw new RuntimeException("OpenCV initialization failed");
        }

        kanjiCanvasView = findViewById(R.id.kanji_canvas_view);

        // Submit button logic
        Button submitButton = findViewById(R.id.submit_drawing_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap originalBitmap = kanjiCanvasView.getBitmap();

                // Preprocess the image using ImagePreprocessor
                Mat preprocessedMat = ImagePreprocessor.preprocessImage(originalBitmap);
                Bitmap preprocessedBitmap = Bitmap.createBitmap(preprocessedMat.cols(), preprocessedMat.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(preprocessedMat, preprocessedBitmap);

                // Save both original and preprocessed images to gallery
                saveImageToGallery(KanjiInputActivity.this, originalBitmap, "original_kanji");
                saveImageToGallery(KanjiInputActivity.this, preprocessedBitmap, "preprocessed_kanji");

                Toast.makeText(KanjiInputActivity.this, "Images saved to gallery", Toast.LENGTH_SHORT).show();
            }
        });

        // Retry button logic to clear the canvas
        Button retryButton = findViewById(R.id.retry_drawing_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kanjiCanvasView.clearCanvas(); // Clear the canvas
                Toast.makeText(KanjiInputActivity.this, "Canvas cleared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Save image to gallery method
    private void saveImageToGallery(Context context, Bitmap bitmap, String imageName) {
        OutputStream fos;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/KanjiDraw");
                values.put(MediaStore.Images.Media.IS_PENDING, true);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName + "_" + getTimestamp() + ".png");

                Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                fos = context.getContentResolver().openOutputStream(uri);

                values.put(MediaStore.Images.Media.IS_PENDING, false);
                context.getContentResolver().update(uri, values, null, null);
            } else {
                String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/KanjiDraw";
                File file = new File(imagesDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
                File image = new File(imagesDir, imageName + "_" + getTimestamp() + ".png");
                fos = new FileOutputStream(image);

                // Make the image visible in the gallery
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(image));
                context.sendBroadcast(intent);
            }

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            if (fos != null) {
                fos.flush();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the current timestamp
    private String getTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
