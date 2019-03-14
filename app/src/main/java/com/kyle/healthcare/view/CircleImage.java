package com.kyle.healthcare.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kyle.healthcare.R;

/*
 * 呈现圆形照片
 */
public class CircleImage extends View {

    //Function 规定头像的半径<-参数设定 where onDraw
    private float radius;

    //Function 头像图片的初始图片<- 参数设定 where drawableTurnToCircleBitmap
    private Drawable drawable;

    //Function 绘制头像<- drawableTurnToBitmap where onDraw
    private Bitmap bitmap;

    public CircleImage(Context context) {
        super(context);
    }
    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            initCircleImage(context,attrs);
        }catch (NullImageException n) {
            n.showDetail();
            this.bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(this.bitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(0x0);
            canvas.drawCircle(50,50,50,paint);
        }

    }

    /*
     * @Parameter context[Context] attrs[AttributeSet]
     * @Exception  图像未设置时，报错
     * Description attrs属性的值的集合.
     */
    private void initCircleImage(Context context, AttributeSet attrs) throws NullImageException{
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImage);
        this.radius = typedArray.getFloat(R.styleable.CircleImage_radius,50);
        this.drawable = typedArray.getDrawable(R.styleable.CircleImage_image);
        typedArray.recycle();
       if(this.radius == 0){
           this.radius = (getWidth() < getHeight() ? getWidth() : getHeight() )/2;
       }
        if(this.drawable == null){
            throw new NullImageException();
        }else{
            drawableTurnToCircleBitmap();
        }
    }

    private void drawableTurnToCircleBitmap(){
        Bitmap source = ((BitmapDrawable)drawable).getBitmap();
        int length = source.getHeight() < source.getWidth() ? source.getHeight() : source.getWidth();
        Bitmap sourceSquare = Bitmap.createBitmap(source,source.getWidth() - length,source.getHeight() - length,length,length);
        Bitmap canvasBitmap = Bitmap.createBitmap(length,length,Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawCircle(length/2,length/2,length/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sourceSquare,0,0,paint);
        Matrix matrix = new Matrix();
        matrix.postScale(2*this.radius/length,2*this.radius/length);
        this.bitmap = Bitmap.createBitmap(canvasBitmap,0,0,canvasBitmap.getWidth(),canvasBitmap.getHeight(),matrix,true);
    }
    //Drawable转换Bitmap
    private class NullImageException extends Exception{
        //显示错误细节
        private void showDetail(){
            printStackTrace();
            Log.e("NullImageException","CircleImage 中的图片未设置");
        }
    }


    //绘制图像

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(this.bitmap,getWidth() / 2 - radius,getHeight() /2 - this.radius,paint);
    }

    //设置图片（非圆形）
    public void setHeadPortraitByRawBitmap(Bitmap bitmap){
        int length = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        Bitmap bitmapSquare = Bitmap.createBitmap(bitmap,bitmap.getWidth() - length,bitmap.getHeight() - length,length,length);
        Bitmap circleBitmap = Bitmap.createBitmap(length,length,Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(circleBitmap);
        canvas.drawCircle(length/2,length/2,length/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapSquare,0,0,paint);
        Matrix matrix = new Matrix();
        matrix.postScale(2*this.radius/length,2*this.radius/length);
        this.bitmap = Bitmap.createBitmap(circleBitmap,0,0,circleBitmap.getWidth(),circleBitmap.getHeight(),matrix,true);
    }

    //设置图片(圆形)
    public  void setHeadPortrait(Bitmap bitmap){
        this.bitmap = bitmap;
    }
}
