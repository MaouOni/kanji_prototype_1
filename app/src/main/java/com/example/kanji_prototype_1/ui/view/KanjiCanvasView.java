package com.example.kanji_prototype_1.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class KanjiCanvasView extends View {

    private Path drawPath;   // Path that user draws on
    private Paint drawPaint; // Paint for drawing

    public KanjiCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // Initialize the paint and path
    private void init() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);       // Set drawing color to black
        drawPaint.setAntiAlias(true);          // Smooth edges of the drawing
        drawPaint.setStrokeWidth(30);          // Set stroke width
        drawPaint.setStyle(Paint.Style.STROKE); // Only draw outlines
        drawPaint.setStrokeJoin(Paint.Join.ROUND); // Join lines smoothly
        drawPaint.setStrokeCap(Paint.Cap.ROUND);   // Smooth end of lines
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(drawPath, drawPaint); // Draw the path the user has created
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);  // Start a new line
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);  // Draw as finger moves
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();  // Redraw the view
        return true;
    }

    // Method to convert canvas to bitmap
    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);  // Draw the current view onto the bitmap
        return bitmap;
    }

    // Clear the drawing canvas
    public void clearCanvas() {
        drawPath.reset();
        invalidate();  // Redraw the view
    }
}
