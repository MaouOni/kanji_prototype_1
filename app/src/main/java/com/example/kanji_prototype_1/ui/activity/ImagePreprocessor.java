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

        // Invert the grayscale image so Kanji becomes white, background becomes black
        Core.bitwise_not(mat, mat);

        // Apply adaptive thresholding (optional)
        Mat thresholdMat = new Mat();
        Imgproc.threshold(mat, thresholdMat, 40, 255, Imgproc.THRESH_BINARY);

        // Resize the image to 64x64 pixels AFTER inversion
        Imgproc.resize(thresholdMat, thresholdMat, new Size(128, 128));

        return thresholdMat;
    }
}
