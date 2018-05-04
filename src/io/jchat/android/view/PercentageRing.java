package io.jchat.android.view;

import com.lnpdit.agriculturalmachinery.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class PercentageRing extends View {


	/**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private float max;

    /**
     * 当前进度
     */
    private Float progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;



    /**
     * 收益率

     */
    private String mRate="50";

    /**
     * 连利率文字
     */
    private String mText1;

    /**
     * 状态的改变
     */
    private String mText2;



    public PercentageRing(Context context) {
        this(context, null);
    }

    public PercentageRing(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentageRing(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();


        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.PercentageRing);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.PercentageRing_ringColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.PercentageRing_circleBackground, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.PercentageRing_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.PercentageRing_textSize, 30);
        roundWidth = mTypedArray.getDimension(R.styleable.PercentageRing_roundWidth, 20);
        max = mTypedArray.getInteger(R.styleable.PercentageRing_radius, 50);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.PercentageRing_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.PercentageRing_style, 0);

        mTypedArray.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        int centreX = getWidth()/2; //获取圆心的x坐标
        int centreY = getHeight()/2; //获得圆心y坐标



        //圆环的半径

        int radius = (int)(centreX - roundWidth);




        paint.setColor(0xff40E0D0);
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth-15); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿


        canvas.drawCircle(centreX, centreY, centreX- roundWidth, paint); //画出圆环




        /**
         * secd circel
         */

        paint.setColor(0xff40E0a0);
        //     paint.setColor(roundColor);
//        paint.setStrokeWidth(roundWidth -  10);
        canvas.drawCircle(centreX,centreY,centreX - roundWidth -3, paint );

        Log.e("log", centreX + "");



        /**
         * 画收益百分比
         */


        if(textIsDisplayable && mRate != null && style == STROKE){
            paint.setStrokeWidth(0);
            paint.setColor(Color.WHITE);
            paint.setTextSize(textSize);
            paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
            float textWidth = paint.measureText(mRate );   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(mRate+"%" , centreX - textWidth / 2-5, centreX - textSize/2 + 5, paint); //画出进度百分比

//            float percentWidth = paint.measureText("%");
//            paint.setTextSize(textSize /2 + 10);
//            canvas.drawText("%",centreX - percentWidth / 2 + textWidth / 2 + 35,centreX -percentWidth / 2 + 10 ,paint);


        }


        /**
         * 描述第二段文字
         */

        if (textIsDisplayable && mText1 != null && style == STROKE){
            paint.setStrokeWidth(0);
            paint.setColor(Color.WHITE);
            paint.setTextSize(textSize/2 + 5);
            paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
            float textWidth = paint.measureText(mText1);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(mText1 , centreX - 35 , centreX + textSize/2+10, paint); //画出进度百分比
        }


        /**
         * 描述第3段文字
         */

        if (textIsDisplayable && mText2 != null && style == STROKE){
            paint.setStrokeWidth(0);
            paint.setColor(Color.BLACK);
            paint.setTextSize(textSize/2+ 3);
            paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
            float textWidth = paint.measureText(mText2);   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
            canvas.drawText(mText2 , centreX - textWidth /2   , centreX + textSize/2 +70 -20 , paint); //画出进度百分比
        }

        /**
         * 画圆弧 ，画圆环的进度
         */



        paint.setStrokeWidth(roundWidth-15);
        paint.setColor(roundProgressColor);  //设置进度的颜色


        RectF oval = new RectF(roundWidth-1 ,roundWidth-18,centreX+radius,centreY + radius-2 );


        switch (style) {
            case STROKE:{

                paint.setStyle(Paint.Style.STROKE);
              //  canvas.rotate(-90,centre,centre);
//                canvas.drawArc(oval, 270, 360 * 50 / 100, false, paint);  //根据进度画圆弧
                canvas.drawArc(oval, 270, 360 * progress / max, false, paint);
                break;
            }
            case FILL:{
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
              //  canvas.rotate(-90,centre,centre);
                if(progress !=0)
                    canvas.drawArc(oval, 270, 360 * progress / max, true, paint);  //根据进度画圆弧

                break;
            }
        }

        Log.d("debug","endding");

    }


    public synchronized float getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     * @param max
     */
    public synchronized void setMax(int max) {
        if(max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     * @return
     */
    public synchronized float getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     * @param b1
     */
    public synchronized void setProgress(Float b1) {
        if(b1 < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(b1 > max){
            b1 = max;
        }
        if(b1 <= max){
            this.progress = b1;
            postInvalidate();
        }

    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }


    public synchronized  String getmRate() {
        return mRate;
    }

    public synchronized void setmRate(String mRate) {
        this.mRate = mRate;
    }

    public  synchronized  String getmText1() {
        return mText1;
    }

    public synchronized  void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public synchronized String getmText2() {
        return mText2;
    }

    public synchronized void setmText2(String mText2) {
        this.mText2 = mText2;
    }
}