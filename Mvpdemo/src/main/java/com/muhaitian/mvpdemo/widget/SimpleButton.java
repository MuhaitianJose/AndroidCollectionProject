package com.muhaitian.mvpdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
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
        initData(context,attrs);
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
        mRect.set(mBorderWidth, mBorderWidth, w - mBorderWidth, h - mBorderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = mRaddius;
        if (mTagShape == SHAPE_ARC) {
            radius = mRect.height() / 2;
        } else if (mTagShape == SHAPE_RECT) {
            radius = 0;
        }

        //绘制背景
        mPaint.setStyle(Paint.Style.FILL);
        if (mIsChecked) {
            mPaint.setColor(mBgColorChecked);
        } else {
            mPaint.setColor(mBgColor);
        }
        canvas.drawRoundRect(mRect, radius, radius, mPaint);
        //绘制边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        if (mIsChecked) {
            mPaint.setColor(mBorderColorChecked);
        } else {
            mPaint.setColor(mBorderColor);
        }
        canvas.drawRoundRect(mRect, radius, radius, mPaint);
        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        if (mIsChecked) {
            mPaint.setColor(mTextColorChecked);
            int padding = mTagMode == MODE_ICON_CHECK_INVISIBLE ? 0 : mIconSize + mIconPadding;
            canvas.drawText(mTextShow, mIconGravity == Gravity.RIGHT ?
                            (getWidth() - mFontWidthChecked - padding) / 2 : (getWidth() - mFontWidthChecked - padding) / 2 + padding,
                    getHeight() / 2 + mBaseLineDistance, mPaint);
        } else {
            mPaint.setColor(mTextColor);
            int padding = mDecorateIcon == null ? 0 : mIconSize + mIconPadding;
            canvas.drawText(mTextShow,
                    mIconGravity == Gravity.RIGHT ? (getWidth() - mFontWidth - padding) / 2
                            : (getWidth() - mFontWidth - padding) / 2 + padding,
                    getHeight() / 2 + mBaseLineDistance, mPaint);
        }
        //绘制Icon
        if (mTagMode == MODE_ICON_CHECK_CHANGE && mIsChecked && mDecorateIconChange != null) {
            mDecorateIconChange.setColorFilter(mPaint.getColor(), PorterDuff.Mode.SRC_IN);
            mDecorateIconChange.draw(canvas);
        } else if (mTagMode == MODE_ICON_CHECK_INVISIBLE && mIsChecked) {
            //Don't need draw
        } else if (mDecorateIcon != null) {
            mDecorateIcon.setColorFilter(mPaint.getColor(), PorterDuff.Mode.SRC_IN);
            mDecorateIcon.draw(canvas);
        }
        if (mIsPressed) {
            mPaint.setColor(mScrimColor);
            canvas.drawRoundRect(mRect, radius, radius, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int allPadding = adjustText(getMeasuredWidth());
        int fontLen = mIsChecked ? mFontWidthChecked : mFontWidth;
        int width = (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) ?
                MeasureSpec.getSize(widthMeasureSpec) : allPadding + fontLen;
        int height = (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) ?
                MeasureSpec.getSize(heightMeasureSpec) : mVerticalPadding * 2 + mFontHeight;
        setMeasuredDimension(width, height);
        if (mDecorateIcon != null || mDecorateIconChange != null) {
            int top = (height - mIconSize) / 2;
            int left;
            if (mIconGravity == Gravity.RIGHT) {
                int padding = (width - mIconSize - fontLen - mIconPadding) / 2;
                left = width - padding - mIconSize;
            } else {
                left = (width - mIconSize - fontLen - mIconPadding) / 2;
            }
            if (mTagMode == MODE_ICON_CHECK_CHANGE && mIsChecked && mDecorateIconChange != null) {
                mDecorateIconChange.setBounds(left, top, mIconSize + left, mIconSize + top);
            } else if (mDecorateIcon != null) {
                mDecorateIcon.setBounds(left, top, mIconSize + left, mIconSize + top);
            }
        }
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

        //设置显示的文字
        if (mIsChecked && !TextUtils.isEmpty(mTextChecked)) {
            if (mFontWidthChecked + allPadding > maxWidth) {
                float pointWidth = mPaint.measureText(".");
                float maxTextWidth = maxWidth - allPadding - pointWidth * 3;
                mTextShow = clipShowText(mTextChecked, mPaint, maxTextWidth);
                mFontWidthChecked = (int) mPaint.measureText(mTextShow);
            } else {
                mTextShow = mTextChecked;
            }
        } else if (mFontWidth + allPadding > maxWidth) {
            float pointWidth = mPaint.measureText(".");
            // 计算能显示的字体长度
            float maxTextWidth = maxWidth - allPadding - pointWidth * 3;
            mTextShow = clipShowText(mText, mPaint, maxTextWidth);
            mFontWidth = (int) mPaint.measureText(mTextShow);
        } else {
            mTextShow = mText;
        }

        return allPadding;
    }

    private String clipShowText(String oriText, Paint paint, float maxTextWidth) {
        float tmpWidth = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < oriText.length(); i++) {
            char c = oriText.charAt(i);
            float cWidth = paint.measureText(String.valueOf(c));
            if (tmpWidth + cWidth > maxTextWidth) {
                break;
            }
            builder.append(c);
            tmpWidth += cWidth;
        }
        builder.append("...");
        return builder.toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                mIsPressed = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsPressed && !isViewUnder(event.getX(), event.getY())) {
                    mIsPressed = false;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isViewUnder(event.getX(), event.getY())) {
                    toggleTagCheckStatus();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsChecked) {
                    mIsChecked = false;
                    invalidate();
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private void toggleTagCheckStatus() {
        if (mIsAutoToggleCheck) {
            setmIsChecked(!mIsChecked);
        }
    }

    private boolean isViewUnder(float x, float y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public int getTagShape() {
        return mTagShape;
    }

    public void setTagShape(int mTagShape) {
        this.mTagShape = mTagShape;
        update();
    }

    public int getTagMode() {
        return mTagMode;
    }

    public void setTagMode(int mTagMode) {
        this.mTagMode = mTagMode;
        update();
    }

//    public Paint getPaint() {
//        return mPaint;
//    }
//
//    public void setPaint(Paint mPaint) {
//        this.mPaint = mPaint;
//    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
        invalidate();
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int mBorderColor) {
        this.mBorderColor = mBorderColor;
        invalidate();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        invalidate();
    }

    public int getBgColorChecked() {
        return mBgColorChecked;
    }

    public void setBgColorChecked(int mBgColorChecked) {
        this.mBgColorChecked = mBgColorChecked;
        invalidate();
    }

    public int getmBorderColorChecked() {
        return mBorderColorChecked;
    }

    public void setBorderColorChecked(int mBorderColorChecked) {
        this.mBorderColorChecked = mBorderColorChecked;
        invalidate();
    }

    public int getTextColorChecked() {
        return mTextColorChecked;
    }

    public void setTextColorChecked(int mTextColorChecked) {
        this.mTextColorChecked = mTextColorChecked;
        invalidate();
    }

    public int getScrimColor() {
        return mScrimColor;
    }

    public void setScrimColor(int mScrimColor) {
        this.mScrimColor = mScrimColor;
        invalidate();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float mTextSize) {
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

    public void setmBaseLineDistance(int mBaseLineDistance) {
        this.mBaseLineDistance = mBaseLineDistance;
    }

    public float getmBorderWidth() {
        return mBorderWidth;
    }

    public void setmBorderWidth(float mBorderWidth) {
        this.mBorderWidth = mBorderWidth;
    }

    public float getRaddius() {
        return mRaddius;
    }

    public void setRaddius(float mRaddius) {
        this.mRaddius = mRaddius;
        invalidate();
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
        update();
    }

    public String getTextChecked() {
        return mTextChecked;
    }

    public void setTextChecked(String mTextChecked) {
        this.mTextChecked = mTextChecked;
        update();
    }

    public String getTextShow() {
        return mTextShow;
    }

    public void setTextShow(String mTextShow) {
        this.mTextShow = mTextShow;
    }

    public int getHorizontalPadding() {
        return mHorizontalPadding;
    }

    public void setHorizontalPadding(int mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
        update();
    }

    public int getVerticalPadding() {
        return mVerticalPadding;
    }

    public void setVerticalPadding(int mVerticalPadding) {
        this.mVerticalPadding = mVerticalPadding;
        update();
    }

    public RectF getmRect() {
        return mRect;
    }

    public void setmRect(RectF mRect) {
        this.mRect = mRect;
    }

    public Drawable getDecorateIcon() {
        return mDecorateIcon;
    }

    public void setDecorateIcon(Drawable mDecorateIcon) {
        this.mDecorateIcon = mDecorateIcon;
        update();
    }

    public Drawable getDecorateIconChange() {
        return mDecorateIconChange;
    }

    public void setDecorateIconChange(Drawable mDecorateIconChange) {
        this.mDecorateIconChange = mDecorateIconChange;
        update();
    }

    public int getIconGravity() {
        return mIconGravity;
    }

    public void setIconGravity(int mIconGravity) {
        this.mIconGravity = mIconGravity;
    }

    public int getIconPadding() {
        return mIconPadding;
    }

    public void setIconPadding(int mIconPadding) {
        this.mIconPadding = mIconPadding;
        update();
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

    /**
     * 设置选中状态
     *
     * @param Checked
     */
    public void setmIsChecked(boolean Checked) {
        if (mIsChecked == Checked) {
            return;
        }
        mIsChecked = Checked;
        requestLayout();
        invalidate();
        if (mCheckListener != null) {
            mCheckListener.onCheckedChanged(mIsChecked);
        }
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


    private OnCheckedChangeListener mCheckListener;


    public void setCheckListener(OnCheckedChangeListener onCheckedChangeListener) {
        mCheckListener = onCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(boolean isChecked);
    }

    private void update() {
        requestLayout();
        invalidate();
    }

}
