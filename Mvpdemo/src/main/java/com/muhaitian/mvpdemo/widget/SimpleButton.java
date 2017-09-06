package com.muhaitian.mvpdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.muhaitian.mvpdemo.R;
import com.muhaitian.mvpdemo.utils.MeasureUtils;

/**
 * Created by muhaitian on 2017/9/6.
 * 自定义控件button
 */


public class SimpleButton extends View {

    private static final String TAG = "SimpleButton";

    //三种外形模式：圆角矩形，圆弧，直角矩形
    public static final int SHAPE_ROUND_RECT = 101;
    public static final int SHAPE_ARC = 102;
    public static final int SHAPE_RECT = 102;
    //类型模式：正常、可选中、图标选中小时、图标选中切换
    public static final int MODE_NORMAL = 201;
    public static final int MODE_CHECK = 202;
    public static final int MODE_ICON_CHECK_INVISIBLE = 203;
    public static final int MODE_ICON_CHECK_CHANGE = 204;

    //显示外形
    private int mTagShape = SHAPE_ROUND_RECT;
    //显示模式
    private int mTagMode = MODE_NORMAL;
    //画笔
    private Paint mPaint;
    //背景色
    private int mBgColor = Color.WHITE;

    //边框颜色
    private int mBorderColor = Color.parseColor("#ff333333");
    //原始标签颜色
    private int mTextColor = Color.parseColor("#ff666666");
    //选中状态背景色
    private int mBgColorChecked = Color.WHITE;

    //选中状态边框颜色
    private int mBorderColorChecked = Color.parseColor("#ff333333");
    //选中状态字体颜色
    private int mTextColorChecked = Color.parseColor("#ff666666");

    //遮罩颜色
    private int mScrimColor = Color.argb(0x66, 0xc0, 0xc0, 0xc0);
    //字体大小
    private float mTextSize;
    //字体宽度和高度
    private int mFontWidth;
    private int mFontHeight;
    private int mFontWidthChecked;

    //基线偏移距离
    private int mBaseLineDistance;
    //边框大小
    private float mBorderWidth;
    //边框圆角半径
    private float mRaddius;
    //内容
    private String mText;
    //选中时内容
    private String mTextChecked;
    //显示的文字
    private String mTextShow;
    //字体水平空隙
    private int mHorizontalPadding;
    //字体垂直间隙
    private int mVerticalPadding;
    //边框矩形
    private RectF mRect;
    //装饰的Icon
    private Drawable mDecorateIcon;
    //变化时的Icon
    private Drawable mDecorateIconChange;

    //设置图标的位置。
    private int mIconGravity = Gravity.LEFT;
    //icon和文字的间距
    private int mIconPadding = 0;
    //icon大小
    private int mIconSize = 0;
    //是否选中
    private boolean mIsChecked = false;
    //
    private boolean mIsAutoToggleCheck = false;
    //是否被按住
    private boolean mIsPressed = false;

    public SimpleButton(Context context) {
        super(context);
    }

    public SimpleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs) {
        //初始化边框宽度
        mBorderWidth = MeasureUtils.dp2px(context, 0.5f);
        //矩形边角弧度半径
        mRaddius = MeasureUtils.dp2px(context, 5f);
        //字体水平间距
        mHorizontalPadding = (int) MeasureUtils.dp2px(context, 5f);
        //字体垂直间距
        mVerticalPadding = (int) MeasureUtils.dp2px(context, 5f);
        //icon和文字间距
        mIconPadding = (int) MeasureUtils.dp2px(context, 3f);
        //字体大小
        mTextSize = (int) MeasureUtils.dp2px(context, 14f);
        if (attrs != null) {
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SimpleButton);
            try {
                mTagShape = typedArray.getInteger(R.styleable.SimpleButton_SB_shape, SimpleButton.SHAPE_ROUND_RECT);
                mTagMode = typedArray.getInteger(R.styleable.SimpleButton_SB_mode, SimpleButton.MODE_NORMAL);

                if (mTagMode == MODE_CHECK || mTagMode == MODE_ICON_CHECK_CHANGE || mTagMode == MODE_ICON_CHECK_INVISIBLE) {
                    mIsAutoToggleCheck = true;
                    mIsChecked = typedArray.getBoolean(R.styleable.SimpleButton_SB_check, false);
                    mDecorateIconChange = typedArray.getDrawable(R.styleable.SimpleButton_SB_icon_change);
                }
                mIsAutoToggleCheck = typedArray.getBoolean(R.styleable.SimpleButton_SB_auto_check, false);

                //文字选中与未选中
                mText = typedArray.getString(R.styleable.SimpleButton_SB_text);
                mTextChecked = typedArray.getString(R.styleable.SimpleButton_SB_text_check);
                mTextSize = typedArray.getDimension(R.styleable.SimpleButton_SB_text_size, mTextSize);

                mBgColor = typedArray.getColor(R.styleable.SimpleButton_SB_bg_color, Color.WHITE);
                mBorderColor = typedArray.getColor(R.styleable.SimpleButton_SB_border_color, Color.parseColor("#ff333333"));
                mTextColor = typedArray.getColor(R.styleable.SimpleButton_SB_text_color, Color.parseColor("#ff666666"));

                mBgColorChecked = typedArray.getColor(R.styleable.SimpleButton_SB_bg_color_check, mBgColor);
                mBorderColorChecked = typedArray.getColor(R.styleable.SimpleButton_SB_border_color_check, mBorderColor);
                mTextColorChecked = typedArray.getColor(R.styleable.SimpleButton_SB_text_color_check, mTextColor);

                mBorderWidth = typedArray.getDimension(R.styleable.SimpleButton_SB_border_width, mBorderWidth);
                mRaddius = typedArray.getDimension(R.styleable.SimpleButton_SB_border_radius, mRaddius);

                mHorizontalPadding = (int) typedArray.getDimension(R.styleable.SimpleButton_SB_horizontal_padding, mHorizontalPadding);
                mVerticalPadding = (int) typedArray.getDimension(R.styleable.SimpleButton_SB_vertical_padding, mVerticalPadding);
                mIconPadding = (int) typedArray.getDimension(R.styleable.SimpleButton_SB_icon_padding, mIconPadding);

                mDecorateIcon = typedArray.getDrawable(R.styleable.SimpleButton_SB_icon);
                mIconGravity = typedArray.getInteger(R.styleable.SimpleButton_SB_gravity, Gravity.LEFT);

            } finally {
                typedArray.recycle();
            }

            if (mTagMode == MODE_ICON_CHECK_CHANGE && mDecorateIconChange == null) {
                throw new RuntimeException("You must set the drawable by 'tag_icon_change' property in MODE_ICON_CHECK_CHANGE mode");
            }

            if (mDecorateIcon == null && mDecorateIconChange == null) {
                mIconPadding = 0;
            }

            mRect = new RectF();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            setClickable(true);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int allPadding = adjustText(getMeasuredWidth());
        int fontLen = mIsChecked ? mFontWidthChecked : mFontWidth;
    }

    private int adjustText(int maxWidth) {
        if (mPaint.getTextSize() != mTextSize) {
            mPaint.setTextSize(mTextSize);
            final Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            //文字高度
            mFontHeight = (int) (fontMetrics.descent - fontMetrics.ascent);
            //赋值基线的原理还是不太明白
            mBaseLineDistance = (int) Math.ceil((fontMetrics.descent - fontMetrics.ascent) / 1 - fontMetrics.descent);
        }
        if (TextUtils.isEmpty(mText)) {
            mText = "";
        }
        mFontWidth = (int) mPaint.measureText(mText);
        if (TextUtils.isEmpty(mTextChecked)) {
            mFontWidthChecked = mFontWidth;
        } else {
            mFontWidthChecked = (int) mPaint.measureText(mTextChecked);
        }

        //计算图标大小
        if ((mDecorateIcon != null || mDecorateIconChange != null) && mIconSize != mFontHeight) {
            mIconSize = mFontHeight;
        }

        int allPadding;
        //计算除了文字外所需要占用的宽度
        if (mTagMode == MODE_ICON_CHECK_INVISIBLE && mIsChecked) {
            allPadding = mHorizontalPadding * 2;
        } else if (mDecorateIcon == null && !mIsChecked) {
            allPadding = mHorizontalPadding * 2;
        } else {
            allPadding = mIconPadding + mIconSize + mHorizontalPadding * 2;
        }


        return allPadding;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public int getmTagShape() {
        return mTagShape;
    }

    public void setmTagShape(int mTagShape) {
        this.mTagShape = mTagShape;
    }

    public int getmTagMode() {
        return mTagMode;
    }

    public void setmTagMode(int mTagMode) {
        this.mTagMode = mTagMode;
    }

    public Paint getmPaint() {
        return mPaint;
    }

    public void setmPaint(Paint mPaint) {
        this.mPaint = mPaint;
    }

    public int getmBgColor() {
        return mBgColor;
    }

    public void setmBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
    }

    public int getmBorderColor() {
        return mBorderColor;
    }

    public void setmBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getmBgColorChecked() {
        return mBgColorChecked;
    }

    public void setmBgColorChecked(int mBgColorChecked) {
        this.mBgColorChecked = mBgColorChecked;
    }

    public int getmBorderColorChecked() {
        return mBorderColorChecked;
    }

    public void setmBorderColorChecked(int mBorderColorChecked) {
        this.mBorderColorChecked = mBorderColorChecked;
    }

    public int getmTextColorChecked() {
        return mTextColorChecked;
    }

    public void setmTextColorChecked(int mTextColorChecked) {
        this.mTextColorChecked = mTextColorChecked;
    }

    public int getmScrimColor() {
        return mScrimColor;
    }

    public void setmScrimColor(int mScrimColor) {
        this.mScrimColor = mScrimColor;
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getmFontWidth() {
        return mFontWidth;
    }

    public void setmFontWidth(int mFontWidth) {
        this.mFontWidth = mFontWidth;
    }

    public int getmFontHeight() {
        return mFontHeight;
    }

    public void setmFontHeight(int mFontHeight) {
        this.mFontHeight = mFontHeight;
    }

    public int getmFontWidthChecked() {
        return mFontWidthChecked;
    }

    public void setmFontWidthChecked(int mFontWidthChecked) {
        this.mFontWidthChecked = mFontWidthChecked;
    }

    public float getmBaseLineDistance() {
        return mBaseLineDistance;
    }

    public void setmBaseLineDistance(float mBaseLineDistance) {
        this.mBaseLineDistance = mBaseLineDistance;
    }

    public float getmBorderWidth() {
        return mBorderWidth;
    }

    public void setmBorderWidth(float mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public float getmRaddius() {
        return mRaddius;
    }

    public void setmRaddius(float mRaddius) {
        this.mRaddius = mRaddius;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmTextChecked() {
        return mTextChecked;
    }

    public void setmTextChecked(String mTextChecked) {
        this.mTextChecked = mTextChecked;
    }

    public String getmTextShow() {
        return mTextShow;
    }

    public void setmTextShow(String mTextShow) {
        this.mTextShow = mTextShow;
    }

    public int getmHorizontalPadding() {
        return mHorizontalPadding;
    }

    public void setmHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
    }

    public int getmVerticalPadding() {
        return mVerticalPadding;
    }

    public void setmVerticalPadding(int mVerticalPadding) {
        this.mVerticalPadding = mVerticalPadding;
    }

    public RectF getmRect() {
        return mRect;
    }

    public void setmRect(RectF mRect) {
        this.mRect = mRect;
    }

    public Drawable getmDecorateIcon() {
        return mDecorateIcon;
    }

    public void setmDecorateIcon(Drawable mDecorateIcon) {
        this.mDecorateIcon = mDecorateIcon;
    }

    public Drawable getmDecorateIconChange() {
        return mDecorateIconChange;
    }

    public void setmDecorateIconChange(Drawable mDecorateIconChange) {
        this.mDecorateIconChange = mDecorateIconChange;
    }

    public int getmIconGravity() {
        return mIconGravity;
    }

    public void setmIconGravity(int mIconGravity) {
        this.mIconGravity = mIconGravity;
    }

    public int getmIconPadding() {
        return mIconPadding;
    }

    public void setmIconPadding(int mIconPadding) {
        this.mIconPadding = mIconPadding;
    }

    public int getmIconSize() {
        return mIconSize;
    }

    public void setmIconSize(int mIconSize) {
        this.mIconSize = mIconSize;
    }

    public boolean ismIsChecked() {
        return mIsChecked;
    }

    public void setmIsChecked(boolean mIsChecked) {
        this.mIsChecked = mIsChecked;
    }

    public boolean ismIsAutoToggleCheck() {
        return mIsAutoToggleCheck;
    }

    public void setmIsAutoToggleCheck(boolean mIsAutoToggleCheck) {
        this.mIsAutoToggleCheck = mIsAutoToggleCheck;
    }

    public boolean ismIsPressed() {
        return mIsPressed;
    }

    public void setmIsPressed(boolean mIsPressed) {
        this.mIsPressed = mIsPressed;
    }
}
