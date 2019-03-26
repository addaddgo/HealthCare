package com.kyle.healthcare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kyle.healthcare.R;
import com.kyle.healthcare.bluetooth.Constants;
import com.kyle.healthcare.controller_data.DataManger;

import java.util.ArrayList;


public class FatigueRateView extends SurfaceView implements SurfaceHolder.Callback ,Runnable {

    //笔
    private Paint backgroundPaint;
    private Paint borderPaint;
    private Paint linePaint;
    private Paint dangerousLinePaint;
    private Paint potPaint;
    private Paint textPaint;

    //surfaceView
    private SurfaceHolder surfaceHolder;

    //默认属性
    public FatigueRateView(Context context) {
        super(context);
        initView();
        //设置默认的颜色
        Log.i("construct","默认颜色设置");
        this.backgroundPaint.setColor(Color.BLUE);
        this.backgroundPaint.setColor(Color.BLUE);
        this.linePaint.setColor(Color.BLACK);
        this.potPaint.setColor(Color.GREEN);
        this.dangerousLinePaint.setColor(Color.RED);
        this.linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.dangerousLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.textPaint.setColor(Color.GRAY);
        this.textPaint.setTextSize(20);
        this.linePaint.setStrokeWidth(40);
        this.dangerousLinePaint.setStrokeWidth(40);
    }

    public FatigueRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.FatigueRateView);
        this.backgroundPaint.setColor(typedArray.getColor(R.styleable.FatigueRateView_fatigue_background_color, Color.BLUE));
        this.borderPaint.setColor(typedArray.getColor(R.styleable.FatigueRateView_fatigue_border_color, Color.GREEN));
        this.linePaint.setColor(typedArray.getColor(R.styleable.FatigueRateView_line_color, Color.BLACK));
        this.dangerousLinePaint.setColor(typedArray.getColor(R.styleable.FatigueRateView_dangerous_line_color, Color.RED));
        this.range = typedArray.getInt(R.styleable.FatigueRateView_date_range, Constants.FATIGUE_RATE_RANGE);
        this.potPaint.setColor(typedArray.getColor(R.styleable.FatigueRateView_pot_color, Color.GREEN));
        this.textPaint.setTextSize(typedArray.getInt(R.styleable.FatigueRateView_text_size,20));
        this.textPaint.setColor(typedArray.getInt(R.styleable.FatigueRateView_text_color,Color.GRAY));
        typedArray.recycle();
        this.linePaint.setStrokeWidth(10);
        this.dangerousLinePaint.setStrokeWidth(10);
    }


    //初始化
    private void initView(){
        Log.i("initView","实例化实例变量");
        this.surfaceHolder = this.getHolder();
        this.surfaceHolder.addCallback(this);
        this.backgroundPaint = new Paint();
        this.borderPaint = new Paint();
        this.linePaint = new Paint();
        this.dangerousLinePaint = new Paint();
        this.potPaint = new Paint();
        this.textPaint = new Paint();
        setData();
    }

    private Thread thread;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("surface","created");
        analyzeDrawData();
        this.isRunning = true;
      this.thread = new Thread(this);
      this.thread.start();
    }
    //绘图数据
    private int INTERVAL;
    private int height;
    private int width;
    private int margin;
    private int scaleOfHeight;
    private int distance;
    private int NUMBER_SPOT;
    private int range;
    private int[] data;
    private int potR;
    private int dangerousData;
    private ArrayList<Integer> integers; 
    private int scaleOffset;
    //绘图数据初始化
    private void analyzeDrawData(){
        this.height = getMeasuredHeight();
        this.width = getMeasuredWidth();
        this.NUMBER_SPOT = 10;
        this.margin = 50;
        this.scaleOfHeight = (this.height - 2 * this.margin)/ this.range;
        this.distance = (this.width  - 2 * this.margin)/ (this.NUMBER_SPOT  - 2);
        this.dangerousData = Constants.FATIGUE_RAGE_UNUSUAL;
        this.potR = 7;
        this.INTERVAL = (int)(480 / this.distance);
        if(this.integers.size() == 0){
            this.scaleOffset = 1;
        }else{
            this.scaleOffset = this.integers.size();
        }
    }

    private void setData() {
        DataManger dataManger = DataManger.dataManger;
        if(dataManger.getFatigueViewHolder() == null){
            this.data = new int[]{0, 0, 0, 0, 0, 0, 0,0,0,0};
            this.integers = new ArrayList<>();
            this.scaleOffset = 1;
        }else{
            DataManger.ViewDataHolder dataHolder= dataManger.getFatigueViewHolder();
            this.data = dataHolder.getCurrent();
            this.integers = dataHolder.getArrayList();
            this.scaleOffset = this.integers.size();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("surface", "changed");
        analyzeDrawData();
        this.isRunning = true;
        //采用消息机制来再次开启线程
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("surface","destroyed");
        this.isRunning = false;
    }

    //控制线程
    private boolean isRunning;

    public void stopDrawThread(){
        this.isRunning = false;
        this.thread.interrupt();
    }

    @Override
    public void run() {
        while (this.isRunning){
            getData();
            if(hashAddData){
                try{
                    for(int i = 0; i < this.distance;i+= this.scaleOffset) {
                        synchronized (this.surfaceHolder) {
                            Canvas canvas = surfaceHolder.lockCanvas();
                            drawLine(canvas, -i);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            Thread.sleep(this.INTERVAL);
                        }
                    }

                }catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("DrawThread", "线程被强制停止1");
                    break;
                }
            }else{
                try{
                    for(int i = 0; i < this.distance;i+=this.scaleOffset) {
                        synchronized (this.surfaceHolder) {
                            Canvas canvas = surfaceHolder.lockCanvas();
                            drawLine(canvas,-this.distance );
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            Thread.sleep(this.INTERVAL);
                        }
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("DrawThread", "线程被强制停止2");
                    break;
                }
            }
        }
    }

    //绘图函数
    private void drawLine(Canvas canvas,int offset){
        canvas.drawRect(0,0,this.width,this.height,this.backgroundPaint);
        for(int i = 0; i < this.NUMBER_SPOT - 1;i++){
            if(this.data[i + 1] < this.dangerousData && this.data[i] < this.dangerousData){
                canvas.drawLine(offset + this.margin + this.distance * i,this.height - (int)this.data[i] * this.scaleOfHeight - this.margin,offset + this.margin + this.distance  * (i + 1) ,this.height - (int)this.data[i + 1] * this.scaleOfHeight - this.margin,this.linePaint );
                canvas.drawText(String.valueOf(this.data[i]),offset + this.margin + this.distance  * (i + 1) ,this.height - (int)this.data[i + 1] * this.scaleOfHeight - 6 * this.margin / 2,this.textPaint);
            }else{
                canvas.drawText(String.valueOf(this.data[i]),offset + this.margin + this.distance  * (i + 1) ,this.height - (int)this.data[i + 1] * this.scaleOfHeight - 6 * this.margin / 2,this.textPaint);
                canvas.drawLine(offset + this.margin + this.distance * i,this.height - (int)this.data[i] * this.scaleOfHeight - this.margin ,offset + this.margin + this.distance  * (i + 1) ,this.height - (int)this.data[i + 1] * this.scaleOfHeight - this.margin,this.dangerousLinePaint);
                i++;
                if(i < this.NUMBER_SPOT - 1 && this.data[i] >= this.dangerousData){
                    canvas.drawText(String.valueOf(this.data[i]),offset + this.margin + this.distance  * (i + 1) ,this.height - (int)this.data[i + 1] * this.scaleOfHeight - 6 * this.margin / 2,this.textPaint);
                    canvas.drawLine(offset + this.margin + this.distance * i,this.height - (int)this.data[i] * this.scaleOfHeight - this.margin,offset + this.margin + this.distance  * (i + 1) ,this.height - (int)this.data[i + 1] * this.scaleOfHeight - this.margin,this.dangerousLinePaint);
                }else if(this.data[i] < this.dangerousData){
                    i--;
                }
            }
        }
        onDrawBack(canvas);
        for(int i = 0; i< this.NUMBER_SPOT ; i++){
            canvas.drawCircle(offset + this.margin + this.distance * i,this.height - this.data[i] * this.scaleOfHeight - this.margin,this.potR,this.potPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        analyzeDrawData();
    }

    //背景绘制
    private void onDrawBack(Canvas canvas) {
        canvas.drawRect(0,0,this.margin ,this.width,this.backgroundPaint);
        canvas.drawRect(0,0,this.margin,this.height,this.backgroundPaint);
        canvas.drawRect(0,this.height - this.margin,this.width,this.height,this.backgroundPaint);
        canvas.drawRect(this.width - this.margin,0,this.width,this.height,this.backgroundPaint);
        for(int i = 0; i < (this.width -  this.margin) / 20 ; i++){
            canvas.drawCircle(this.margin + 20 * i,this.margin / 2,this.potR,this.borderPaint);
        }
        for(int i = 0;i < (this.width -   this.margin)/ 20;i++){
            canvas.drawCircle(this.margin + 20 * i,this.height - this.margin / 2,this.potR,this.borderPaint);
        }
        for(int i = 0;i < (this.height - this.margin)/20 ; i++){
            canvas.drawCircle(this.margin /2,this.margin + 20 * i,this.potR,this.borderPaint);
        }
        for (int i = 0; i < (this.height - this.margin)/ 20; i++) {
            canvas.drawCircle(this.width - this.margin / 2,this.margin + 20 * i,this.potR,this.borderPaint);
        }
    }

    //数据更新
    private boolean hashAddData = false;

    //将拿到的新数据切换到data中
    private void getData(){
        if(this.integers.size() != 0){
            for(int i = 0 ;i < this.NUMBER_SPOT - 1;i++){
                this.data[i] = this.data[i+1];
            }
            int newData = this.integers.get(0);
            this.integers.remove(0);
            this.data[this.NUMBER_SPOT - 1] = newData;
            synchronized (this){
                if(this.integers.size() == 0){
                    this.scaleOffset = 1;
                }else{
                    this.scaleOffset = this.integers.size();
                }
                this.hashAddData = true;
            }
        }else{
            this.hashAddData = false;
        }

    }

    //添加新的数据
    public void addNewData(int newData){
        this.integers.add(newData);
        this.hashAddData = true;
    }

    public void close(){
        DataManger.dataManger.setFatigueRateViewDataEnd(this.integers,this.data);
    }



//    private Handler handler;
//    private HandlerThread handlerThread;
//
//    private void openThreadTest(){
//        this.handlerThread = new HandlerThread("fatigueTest");
//        this.handlerThread.start();
//        this.handler = new Handler(this.handlerThread.getLooper()){
//            @Override
//            public void handleMessage(Message msg) {
//                if(msg.what == 1){
//                    addNewData((int)(Math.random() * range));
//                }
//            }
//        };
//    }
}
