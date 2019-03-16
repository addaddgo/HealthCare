package com.kyle.healthcare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kyle.healthcare.R;

public class HeartRateView extends SurfaceView implements SurfaceHolder.Callback,Runnable{

    private SurfaceHolder surfaceHolder;
    private Context context;
    //绘制线程    private DrawThread drawThread;


    //绘制数据
        //画笔
    private int backgroundColor;
    private int borderColor;
    private int dangerousColumnColor;
    private int averageCircleColor;
    private int columnColor;
    private int criticalColor;
    private Paint backgroundPaint;
    private Paint borderPaint;
    private Paint dangerousColumnPaint;
    private Paint averageCircleColorPaint;
    private Paint columnColorPaint;
    private Paint criticalLinePaint;
        //数据
    private int runDirection;
    private int margin ;
    private int INTERVAL;//刷新间隔
    private int NUMBER_COLUMN = 7;
    private int[] heartRateData;
    private int widthOfColumn;
    private int scaleOfHeight;
    private int height;
    private int width;
    private int all;
    private int allDateNumber;
    private int averageCircleR;
    private int textOffsetX;
    private int textOffsetY;
    private int textSize;

    public HeartRateView(Context context) {
        super(context);
        initView(context);
        this.backgroundPaint.setColor(0xff13237d);
        this.borderPaint.setColor(0xfff9fafc);
        this.dangerousColumnPaint.setColor(Color.RED);
        this.averageCircleColorPaint.setColor(Color.BLACK);
        this.columnColorPaint.setColor(0xfff9afc);
        this.criticalLinePaint.setColor(Color.RED);
        this.runDirection = -1;
    }

    public HeartRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.HeartRateView);
        this.backgroundColor = typedArray.getColor(R.styleable.HeartRateView_heart_rate_background_color, 0xff13237d);
        this.backgroundPaint.setColor(this.backgroundColor);
        this.borderColor = typedArray.getColor(R.styleable.HeartRateView_heart_rate_border_color, 0xfff9fafc);
        this.borderPaint.setColor(this.borderColor);
        this.dangerousColumnColor = typedArray.getColor(R.styleable.HeartRateView_dangerous_column_color, Color.RED);
        this.dangerousColumnPaint.setColor(this.dangerousColumnColor);
        this.averageCircleColor = typedArray.getColor(R.styleable.HeartRateView_average_circle_color, Color.BLACK);
        this.averageCircleColorPaint.setColor(this.averageCircleColor);
        this.columnColor = typedArray.getColor(R.styleable.HeartRateView_column_color, 0xfff9afc);
        this.columnColorPaint.setColor(this.columnColor);
        this.criticalColor = typedArray.getColor(R.styleable.HeartRateView_critical_color, Color.RED);
        this.columnColorPaint.setColor(this.criticalColor);
        this.runDirection = typedArray.getInt(R.styleable.HeartRateView_run_direction, -1);
        typedArray.recycle();
    }

    //初始化
    private void initView(Context context){
        //测试数据
        this.heartRateData = new int[this.NUMBER_COLUMN];
        for(int i = 1; i < 8; i++){
            this.heartRateData[i - 1] = i * 25;
        }
        this.allDateNumber = this.NUMBER_COLUMN;
        int all = 0;
        for(int i =0; i < this.NUMBER_COLUMN;i++){
            all += this.heartRateData[i];
        }
        this.all = all;
        this.context = context;
        this.surfaceHolder = this.getHolder();
        this.surfaceHolder.addCallback(this);
        this.backgroundPaint = new Paint();
        this.borderPaint = new Paint();
        this.dangerousColumnPaint = new Paint();
        this.averageCircleColorPaint = new Paint();
        this.columnColorPaint = new Paint();
        this.criticalLinePaint = new Paint();
    }

    //确定绘图所需要的数据
    private void analyseDataOfDrawing(int height,int width){
        this.height = height;
        this.width = width;
        this.INTERVAL = 10;
        this.scaleOfHeight = this.height / 200;//150五十以下为正常的运动心率
        this.margin = this.width / (this.NUMBER_COLUMN);
        this.widthOfColumn = (this.width - 2 * this.margin )/(this.NUMBER_COLUMN * 2 + 1);
        this.averageCircleR = 20;
        this.textSize = 50;
        this.averageCircleColorPaint.setTextSize(this.textSize);
        this.dangerousColumnPaint.setTextSize(this.textSize);
        this.textOffsetY = this.textSize / 3 ;
        this.textOffsetX = this.averageCircleR + this.textSize / 2;
    }

    private Thread thread;
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        analyseDataOfDrawing(getMeasuredHeight(),getMeasuredWidth());
        //开启线程
        Log.i("surface","created");
        this.isRunning = true;
        if(this.thread == null){
            this.thread = new Thread(this);
            this.thread.start();
        }else{
            restartDraw();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.isRunning = false;
        Log.i("surface","destroyed");
    }

    public synchronized void stopDrawThread(){
        this.isRunning = false;
        this.thread.interrupt();
    }

    //线程控制
    private volatile boolean isRunning = false;

        //绘制
        public void run() {
            while (isRunning) {
                synchronized (surfaceHolder) {
                    if(this.hashNewData){
                        changeData();
                        try {
                            for (int i = 0; i <= 2*widthOfColumn; i++) {
                                Canvas canvas = surfaceHolder.lockCanvas();
                                paint(canvas, i * this.runDirection,0);
                                surfaceHolder.unlockCanvasAndPost(canvas);
                                Thread.sleep(INTERVAL);
                            }
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.d("HeartRateThread", "stop:1");
                            break;
                        }
                    }else{
                        try {
                            for (int i = 0; i <= 2 * widthOfColumn; i++) {
                                Canvas canvas = surfaceHolder.lockCanvas();
                                paint(canvas, 2 * widthOfColumn * this.runDirection,-1 * this.runDirection);
                                surfaceHolder.unlockCanvasAndPost(canvas);
                                Thread.sleep(INTERVAL);
                            }
                        }catch (InterruptedException e) {
                            e.printStackTrace();
                            Log.d("HeartRateThread", "stop:3");
                            break;
                        }
                    }
                }
            }
        }

    //绘制函数
    private void paint(Canvas canvas,int offset,int rl){
            //坐标线
        try {
            canvas.drawRect(0, 0, this.width, this.height, this.backgroundPaint);
            for (int i = 0; i < this.NUMBER_COLUMN; i++) {
                if (this.heartRateData[i] > 150) {
                    canvas.drawRect(this.margin + this.widthOfColumn * (2 * i + 1 + rl) + offset, this.height - this.scaleOfHeight * this.heartRateData[i] - this.margin, this.widthOfColumn * (2 * i + 2 + rl) + offset + this.margin, this.height - this.margin, this.dangerousColumnPaint);
                } else {
                    canvas.drawRect(this.margin + this.widthOfColumn * (2 * i + 1 + rl) + offset, this.height - this.scaleOfHeight * this.heartRateData[i] - this.margin, this.widthOfColumn * (2 * i + 2 + rl) + offset + this.margin, this.height - this.margin, this.columnColorPaint);
                }
            }
            canvas.drawRect(this.width - this.margin, 0, this.width, this.height - this.margin, this.backgroundPaint);
            canvas.drawRect(this.width - this.margin, this.margin, this.width - this.margin + 5, this.height - this.margin, this.borderPaint);
            canvas.drawRect(0, this.height - this.margin, this.width - this.margin, this.height, this.backgroundPaint);
            canvas.drawRect(0, this.height - this.margin, this.width - this.margin, this.height - this.margin + 5, this.borderPaint);
            canvas.drawRect(0, this.height - 170 * this.scaleOfHeight - this.margin + 4, this.width - this.margin, this.height - 170 * this.scaleOfHeight - this.margin + 5, this.criticalLinePaint);
            if (this.all / this.allDateNumber > 150) {
                canvas.drawCircle(this.width - this.margin + 2, this.height - this.margin - this.scaleOfHeight * this.all / this.allDateNumber, 20, this.dangerousColumnPaint);
                canvas.drawText(String.valueOf(this.all / this.allDateNumber), this.width - this.margin + this.textOffsetX, this.height - this.margin - this.scaleOfHeight * this.all / this.allDateNumber + this.textOffsetY, this.dangerousColumnPaint);
            } else {
                canvas.drawCircle(this.width - this.margin + 2, this.height - this.margin - this.scaleOfHeight * this.all / this.allDateNumber, 20, this.averageCircleColorPaint);
                canvas.drawText(String.valueOf(this.all / this.allDateNumber), this.width - this.margin + this.textOffsetX, this.height - this.margin - this.scaleOfHeight * this.all / this.allDateNumber + this.textOffsetY, this.averageCircleColorPaint);
            }
            }catch (NullPointerException e){
                e.fillInStackTrace();
                Log.d("DrawThread","stop");
            }
        }

    //从新开始绘图
    private void restartDraw(){
            if(!this.thread.isAlive()) {
                this.thread = new Thread(this);
                this.restartDraw();
            }

    }
    //整体数据更新
    public void setSumData(int all) {
        this.all = all;
    }
    public void setAllDateNumber(int allDateNumber) {
        this.allDateNumber = allDateNumber;
    }


    /*数据更新模式
       data:保存着原来的数据
       newData:保存新加的数据
       新添加数据时:addData 修改newData 还有增加aLL,allDateNumber的值,将hashNewData 改成true
       绘图函数如果发现有新的数据，就将newData添加到data中,hashNewData  = false;
     */
    //数据更新测试
    public  void changeData(){
            if(this.runDirection == 1){
                for (int i = this.heartRateData.length - 1; i > 0; i--) {
                    this.heartRateData[i] = this.heartRateData[i-1];
                }
                this.heartRateData[0] = this.newData;
                this.all+=this.newData;
                this.allDateNumber++;
            }else {
                for (int i = 0; i < this.heartRateData.length - 1; i++) {
                    this.heartRateData[i] = this.heartRateData[i+1];
                }
                this.heartRateData[this.heartRateData.length - 1] = this.newData;
                this.all+= this.newData;
                this.allDateNumber++;
            }
        this.hashNewData = false;
    }

    //数据控制
    private boolean hashNewData = true;
    private int newData = 150;

    public  void addData(int newData){
        this.newData = newData;
        this.hashNewData = true;
    }


    //开启新的线程进行测试
}
