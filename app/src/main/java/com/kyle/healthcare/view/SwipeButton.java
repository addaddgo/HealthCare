package com.kyle.healthcare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kyle.healthcare.R;

public class SwipeButton extends View {

    public SwipeButton(Context context) {
        super(context);
        this.paintLine = new Paint();
        this.paintLine.setColor(Color.GRAY);
        this.paintCircle = new Paint();
        this.paintCircle.setColor(Color.YELLOW);
        this.paintLine.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintLine.setStrokeWidth(5);
    }

    public SwipeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.paintLine = new Paint();
        this.paintLine.setColor(Color.GRAY);
        this.paintCircle = new Paint();
        this.paintLine.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintLine.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintLine.setStrokeWidth(5);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.SwipeButton);
        this.vertical = typedArray.getBoolean(R.styleable.SwipeButton_orientation,false);
        this.paintCircle.setColor(typedArray.getColor(R.styleable.SwipeButton_circle_color,Color.YELLOW));
        typedArray.recycle();
    }

    private int radius;
    private Paint paintLine;
    private Paint paintCircle;
    private int width;
    private int height;
    private boolean vertical;
    private int x;
    private int y;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
        if(vertical){
            this.radius = this.width / 2 - 1;
            this.x = this.width / 2;
            this.y = this.radius;
        }else{
            this.radius = this.height / 2 - 1;
            this.x = this.radius;
            this.y = this.height / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(vertical){
            canvas.drawLine(this.width /  2 ,0,this.width / 2,this.height,this.paintLine);
            canvas.drawCircle(this.x,this.y,this.radius,this.paintCircle);
        }else {
            canvas.drawLine(0 ,this.height / 2, this.width,this.height / 2,this.paintLine);
            canvas.drawCircle(this.x,this.y,this.radius,this.paintCircle);
        }
    }

    private int lastLocation;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
            if(this.vertical){
                int move = (int)event.getY() - lastLocation;
                if(this.y >= this.radius && this.y <= this.height - this.radius){
                    if(move <= this.radius - this.y){
                        move = this.radius - this.y;
                    }else if(move >= this.height - this.y - this.radius){
                        move = this.height - this.y - this.radius;
                    }
                    this.y+=move;
                }
                this.lastLocation = (int)event.getY();
            }else{
                int move = (int)event.getX() - lastLocation;
                if(this.x >= this.radius && this.x <= this.width - this.radius){
                    if(move <= this.radius - this.x){
                        move = this.radius - this.x;
                    }else if(move >= this.width - this.x - this.radius){
                        move = this.width - this.x - this.radius;
                    }
                    this.x+=move;
                }
                this.lastLocation = (int)event.getX();
            }
        }
        invalidate();
        return true;
    }
}
