package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ly03.uifinal.R;

/**
 * @author lin
 * @version 2018/7/18 0018
 * activity2
 */
public class MyRelativeLayout extends ViewGroup {

    public MyRelativeLayout(Context context) {
        this(context,null);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec. getMode(widthMeasureSpec);
        int heightMode = MeasureSpec. getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec. getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec. getSize(heightMeasureSpec);
        int layoutWidth = 0;
        int layoutHeight = 0;
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int cWidth = 0;
        int cHeight = 0;
        int count = getChildCount();

        if(widthMode == MeasureSpec. EXACTLY){
            //如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
            layoutWidth = sizeWidth;
        } else{
            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for ( int i = 0; i < count; i++)  {
                View child = getChildAt(i);
                cWidth = child.getMeasuredWidth();
                //获取子控件最大宽度
                layoutWidth = cWidth > layoutWidth ? cWidth : layoutWidth;
            }
        }
        //高度很宽度处理思想一样
        if(heightMode == MeasureSpec. EXACTLY){
            layoutHeight = sizeHeight;
        } else{
            for ( int i = 0; i < count; i++)  {
                View child = getChildAt(i);
                cHeight = child.getMeasuredHeight();
                layoutHeight = cHeight > layoutHeight ? cHeight : layoutHeight;
            }
        }

        // 测量并保存layout的宽高
        setMeasuredDimension(layoutWidth, layoutHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        MyRelativeLayoutParams params = null;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

            params = (MyRelativeLayoutParams) child.getLayoutParams();

            switch (params.position) {
                case MyRelativeLayoutParams.POSITION_MIDDLE:    // 中间
                    left = (getWidth()-childMeasureWidth)/2 - params.rightMargin + params.leftMargin;
                    top = (getHeight()-childMeasureHeight)/2 + params.topMargin - params.bottomMargin;
                    break;
                case MyRelativeLayoutParams.POSITION_LEFT:      // 左上方
                    left = 0 + params.leftMargin;
                    top = 0 + params.topMargin;
                    break;
                case MyRelativeLayoutParams.POSITION_RIGHT:     // 右上方
                    left = getWidth()-childMeasureWidth - params.rightMargin;
                    top = 0 + params.topMargin;
                    break;
                case MyRelativeLayoutParams.POSITION_BOTTOM:    // 左下角
                    left = 0 + params.leftMargin;
                    top = getHeight()-childMeasureHeight-params.bottomMargin;
                    break;
                case MyRelativeLayoutParams.POSITION_RIGHTANDBOTTOM:// 右下角
                    left = getWidth()-childMeasureWidth - params.rightMargin;
                    top = getHeight()-childMeasureHeight-params.bottomMargin;
                    break;
                default:
                    break;
            }

            // 确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, left+childMeasureWidth, top+childMeasureHeight);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MyRelativeLayoutParams(getContext(), attrs);
    }
    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new MyRelativeLayoutParams(p);
    }
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MyRelativeLayoutParams(LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT);
    }
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof MyRelativeLayoutParams;
    }


    public static class MyRelativeLayoutParams extends MarginLayoutParams {
        public static final int POSITION_MIDDLE = 0; // 中间
        public static final int POSITION_LEFT = 1; // 左上方
        public static final int POSITION_RIGHT = 2; // 右上方
        public static final int POSITION_BOTTOM = 3; // 左下角
        public static final int POSITION_RIGHTANDBOTTOM = 4; // 右下角

        public int position = POSITION_LEFT;

        public MyRelativeLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs,R.styleable.MyRelativeLayout);
            //获取设置在子控件上的位置属性
            position = a.getInt(R.styleable.MyRelativeLayout_layout_position,position);

            a.recycle();
        }

        public MyRelativeLayoutParams(int width, int height) {
            super(width, height);
        }

        public MyRelativeLayoutParams(LayoutParams source) {
            super(source);
        }

    }
}
