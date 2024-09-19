package com.example.kanji_prototype_1.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;

public class KanjiGridOverlay extends View {

    private Paint linePaint;

    public KanjiGridOverlay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Set up the paint for the grid lines (dotted lines)
        linePaint = new Paint();
        linePaint.setColor(0xFF000000);  // Solid black color for better visibility
        linePaint.setStrokeWidth(5);     // Line thickness
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        // Dotted line effect
        DashPathEffect dashEffect = new DashPathEffect(new float[]{10, 20}, 0);
        linePaint.setPathEffect(dashEffect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Log to verify the onDraw method is called
        Log.d("KanjiGridOverlay", "onDraw: Drawing the grid");

        // Get the view's dimensions
        int width = getWidth();
        int height = getHeight();

        // Draw the 4-square grid with dotted lines
        // Horizontal lines
        canvas.drawLine(0, height / 2, width, height / 2, linePaint);

        // Vertical lines
        canvas.drawLine(width / 2, 0, width / 2, height, linePaint);
    }
}
