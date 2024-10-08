package com.example.kanji_prototype_1.ui.activity;

import android.graphics.Bitmap;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.android.Utils;
import org.opencv.core.Core;

public class ImagePreprocessor {

    // Method to preprocess the image
    public static Mat preprocessImage(Bitmap bitmap) {
        // Convert the Bitmap to a Mat
        Mat mat = new Mat();
        Utils.bitmapToMat(bitmap, mat);

        // Convert the image to grayscale
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2GRAY);

        // Resize the image to 64x64 pixels
        Imgproc.resize(mat, mat, new Size(64, 64));

        // Apply binary thresholding
        // Thresholding at value 128 with max value 255 (white)
        Imgproc.threshold(mat, mat, 128, 255, Imgproc.THRESH_BINARY);

        // Invert the image so the Kanji is white and the background is black
        Core.bitwise_not(mat, mat);

        return mat;
    }
}
