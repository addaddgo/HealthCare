package com.kyle.healthcare.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/*
 * 滚动选择控件
 */
public class ScrollSelectView extends View{

    //高和宽
    private int height;
    private int width;

    //字符串的最大长度
    private int maxStringLength = 1;

    //选中项的大小
    private int textSize;

    //信息
    private String[] strings;

    //选中项在数据中的位置
    private int pointer;

    //画笔选中项
    private Paint mainPaint;
    //非选中项
    private Paint otherPaint;

    //字符选中项的Y坐标
    private float textStarY;
    private float margin;
    private float lastY = 0;

    //移动距离
    private float displacement = 0;

    //是否传入数据的标识符
    private boolean isDraw = false;

    //是否只呈现一个数据
    private boolean onlyOne = false;
    private String string;

    //是否有数据
    private boolean hasData = false;

    //是否可以触动
    private boolean ableTouched;

    //回调的实现类
    private CallBack callBack;
    private Boolean callBakcIsNull = true;

    public ScrollSelectView(Context context) {
        super(context);
    }
    public ScrollSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //传递高和宽的结果
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setHeightAndWidth();
        setTextSizeAndTextStarY();
        setPaint();
    }
    private void setHeightAndWidth(){
        this.height = getMeasuredHeight();
        this.width = getMeasuredWidth();
    }
    //设置选中项字符尺寸及绘制的初始位置
    private void setTextSizeAndTextStarY(){
        int textSizeByHeight = this.height / 5;
        int textSizeByLength = this.width / this.maxStringLength;
        this.textSize = textSizeByHeight < textSizeByLength ? textSizeByHeight : textSizeByLength;
        this.textStarY = this.height * 3 / 5;
        this.margin = this.height * 3 / 10;
    }
    //设置画笔
    private void setPaint(){
        this.mainPaint = new Paint();
        this.mainPaint.setTextSize(this.textSize);
        this.mainPaint.setColor(0xff222222);
        this.otherPaint = new Paint();
        this.otherPaint.setTextSize(this.textSize);
        this.otherPaint.setColor(0xffaaaaaa);
    }

    //设置数据
    public void setData(String[] strings,int maxStringLength){
        if(strings != null){
            this.strings = strings;
            this.hasData = true;
            this.maxStringLength = maxStringLength;
            setTextSizeAndTextStarY();
            setPaint();
        }else{
            this.hasData = false;
        }
    }

    //获取滑动的距离
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(this.ableTouched){
            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    this.lastY = event.getY();
                    if(!this.callBakcIsNull){
                        this.callBack.touchView();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    this.displacement += event.getY() - this.lastY;
                    this.lastY = event.getY();
                    boolean pointerChange = true;
                    if(Math.abs(this.displacement) > this.margin){
                        this.pointer = this.displacement < 0 ? this.pointer - 1 : this.pointer + 1;
                        this.displacement  = this.displacement < 0 ? this.displacement + this.margin : this.displacement - this.margin;
                        if(!this.callBakcIsNull){
                            this.callBack.pointerChange();
                        }
                        pointerChange = false;
                    }
                    this.pointer = this.pointer < 0 ? this.strings.length -1 : this.pointer;
                    this.pointer = this.pointer % this.strings.length;
                    if(!this.callBakcIsNull && pointerChange){
                        this.callBack.whileScrolling();
                    }
                    break;
            }
            invalidate();
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isDraw){
            scrollItem(canvas);
        }
        if(onlyOne){
            drawString(canvas);
        }
    }

    //根据滑动距离滑动选项
    private void scrollItem(Canvas canvas){
        //绘制选中项
        canvas.drawText(this.strings[this.pointer],(this.width - this.mainPaint.measureText(this.strings[this.pointer])) / 2,this.textStarY + this.displacement,this.mainPaint);
        //向下绘制
        int down1 = (this.pointer - 1) < 0 ? this.strings.length -1 : (this.pointer -1);
        int down2 = (this.pointer - 2) < 0 ? this.strings.length -1 : (this.pointer -2);
        canvas.drawText(this.strings[down1],(this.width - this.mainPaint.measureText(strings[down1])) / 2,this.textStarY + this.displacement + this.margin,this.otherPaint);
        canvas.drawText(this.strings[down2],(this.width - this.mainPaint.measureText(strings[down2])) /2 ,this.textStarY + this.displacement + 2 * this.margin,this.otherPaint);
        //向上绘制
        int up1 = (this.pointer + 1) % this.strings.length;
        int up2 = (this.pointer + 2) % this.strings.length;
        canvas.drawText(this.strings[up1],(this.width - this.mainPaint.measureText(this.strings[up1])) / 2,this.textStarY + this.displacement - this.margin,this.otherPaint);
        canvas.drawText(this.strings[up2],(this.width - this.mainPaint.measureText(this.strings[up2])) / 2,this.textStarY + this.displacement - 2 * this.margin,this.otherPaint);
    }

    //只呈现一种字符
    private void drawString(Canvas canvas){
        canvas.drawText(this.string,(this.width - this.mainPaint.measureText(this.string)) / 2,this.textStarY,this.mainPaint);
    }

    //设置字体颜色
    public void setPaintColor(int centerColor,int otherColor){
        this.otherPaint.setColor(otherColor);
        this.mainPaint.setColor(centerColor);
        invalidate();
    }

    //得到选定项
    public String pick(){
        return this.strings[this.pointer];
    }

    //只呈现一个数据
    public void setShowOnlyOneString(String string){
        this.string = string;
        this.onlyOne = true;
        this.isDraw = false;
        this.maxStringLength = string.length();
        setTextSizeAndTextStarY();
        setPaint();
        this.ableTouched = false;
    }

    //开启滚动选择
    public void starPick(int pointer) throws NullDataException,ArrayIndexOutOfBoundsException{
        if(hasData){
            if(pointer <this.strings.length && pointer > -1){
                this.onlyOne = false;
                this.isDraw = true;
                this.pointer = pointer;
                invalidate();
            }else{
                throw new ArrayIndexOutOfBoundsException();
            }
        }else{
            throw new NullDataException();
        }
    }

    //设置不可调控
    public void setUnableTouched(){
        this.ableTouched = false;
    }
    //设置可以调控
    public void setAbleTouched(){
        this.ableTouched = true;
    }

    //空数据异常
    public class NullDataException extends Exception{
        public void showDetail(){
            Log.d("ScrollSelectView"+getClass(),"在没有设置数据的情况下，使用了starPick函数");
            printStackTrace();
        }
    }

    //设置CallBack的实现类


    public void setCallBack(CallBack callBack) {
        if(callBack!= null){
            this.callBack = callBack;
            this.callBakcIsNull = false;
        }
    }

    //滚动期间的回调
    public interface CallBack{

        //当选中项发生改变
        void pointerChange();

        //当发生滚动（选中项没有改变）
        void whileScrolling();

        //当用户点击
        void touchView();
    }
}
