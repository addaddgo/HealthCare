package com.kyle.healthcare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kyle.healthcare.R;

public class FatigueRateViewInHealthFragment extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private int height;
    private int width;
    private Paint paint;
    private Paint backgroundPaint;
    private Bitmap bitmap;
    private SurfaceHolder surfaceHolder;
    public FatigueRateViewInHealthFragment(Context context) {
        super(context);
        this.paint = new Paint();
        this.backgroundPaint = new Paint();
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
    }

    public FatigueRateViewInHealthFragment(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.FatigueRateViewInHealthFragment);
        this.bitmap = ((BitmapDrawable)typedArray.getDrawable(R.styleable.FatigueRateViewInHealthFragment_color_paint)).getBitmap();
        this.paint = new Paint();
        this.backgroundPaint = new Paint();
        this.paint.setColor(typedArray.getColor(R.styleable.FatigueRateViewInHealthFragment_color_paint,Color.GREEN));
        this.backgroundPaint.setColor(typedArray.getColor(R.styleable.FatigueRateViewInHealthFragment_color_background,Color.BLUE));
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setStrokeWidth(8);
        typedArray.recycle();
        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.height = getMeasuredHeight();
        this.width = getMeasuredWidth();
        if(this.bitmapDraw == null){
            Log.d("bitmap","matrix");
        }
        analyzeDrawOfData();
        this.isRunning = true;
        Log.d("thread","start");
        new Thread(this).start();
    }

    //缩减
    private void changeBitmap(){
        int bitmapW = this.height / 2 < this.width / 3 ? this.height / 2: this.width / 2;
        Matrix matrix = new Matrix();
        matrix.postScale(bitmapW / this.width,bitmapW / this.height);
        this.bitmapDraw = Bitmap.createBitmap(this.bitmap,0,0,this.bitmap.getWidth(),this.bitmap.getHeight(),matrix,true);
    }

    private void analyzeDrawOfData(){
        //绘制Z的坐标轴
        this.Y = this.height - this.bitmapDraw.getHeight() * 18 / 25;
        this.X = this.width - this.bitmapDraw.getWidth() * 10 / 23;
        this.paint.setTextSize(this.bitmapDraw.getWidth() / 10);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("thread","pause");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("thread","stop");
    }

    private int Y;
    private int X;
    private boolean isRunning;
    private Bitmap bitmapDraw;
    private int INTERVAL = 10;
    @Override
    public void run() {
        while (isRunning){
            synchronized (surfaceHolder){
                for(double offset = 0.0;offset < Math.PI / 2;offset+=0.0078 ){
                    Canvas canvas = this.surfaceHolder.lockCanvas();
                    drawZ(canvas,offset);
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
                try {
                        Thread.sleep(this.INTERVAL);
                }catch (InterruptedException e){
                    Log.d("thread","interrupted");
                }
            }
        }
    }

    private  void drawZ(Canvas canvas,double offset){
        canvas.drawBitmap(this.bitmapDraw,this.width / 2,this.height / 3,this.paint);
        canvas.drawRect(this.X,0,this.width,this.Y,this.backgroundPaint);
        for (int i = 0; i < 4; i++) {
            canvas.drawText("Z",this.X + this.width / 15 * i,(int)(this.Y - Math.sin(offset + Math.PI / 2)),this.paint);
        }
    }
}
