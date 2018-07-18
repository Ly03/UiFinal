package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.ly03.uifinal.R;

/**
 * @author lin
 * @version 2018/7/18 0018
 * 第一篇，自定义TextView
 */
public class MyTextView extends View {
    private static final String TAG = "MyTextView";

    private String tvText;
    private int tvColor;
    private Drawable tvBg;
    private int tvSize;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    Rect mBound;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //自定义属性 arr
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyTextView,defStyleAttr,0);

        tvText = array.getString(R.styleable.MyTextView_mText);
        tvColor = array.getColor(R.styleable.MyTextView_mColor, ContextCompat.getColor(context,R.color.colorPrimary));
        tvBg = array.getDrawable(R.styleable.MyTextView_mBackground);
        tvSize = array.getInt(R.styleable.MyTextView_mTextSize,40);
        array.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(tvColor);
        mPaint.setTextSize(tvSize);
        mBound = new Rect();
        //getTextBounds:绘制文字的Rect
        mPaint.getTextBounds(tvText, 0, tvText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY){//如果是精确模式，match_parent 或 100dp
            mWidth = width;
        }else if (widthMode == MeasureSpec.AT_MOST){//wrap_content模式
            //文本的宽度+padding
            mWidth = mBound.width() + getPaddingLeft() + getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY){//如果是精确模式，match_parent 或 100dp
            mHeight = height;
        }else if (heightMode == MeasureSpec.AT_MOST){//wrap_content模式
            mHeight = tvText.length() + getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
        canvas.drawText(tvText,getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }


}
