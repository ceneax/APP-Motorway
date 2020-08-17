package ceneax.app.motorway.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.appcompat.widget.AppCompatTextView;

import ceneax.app.motorway.R;
import ceneax.app.motorway.util.AppUtil;
import ceneax.app.motorway.util.L;

public class RoundIconButton extends AppCompatTextView implements View.OnClickListener {

    private Drawable normalIcon, clickIcon;
    private int backgroundColor;

    private boolean flag = false;
    private OnClickListener onClickListener;

    public RoundIconButton(Context context) {
        this(context, null);
    }

    public RoundIconButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundIconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs, defStyleAttr);

        if (backgroundColor == 0)
            setBackgroundColor(R.attr.colorPrimary);
        else
            setBackgroundColor(backgroundColor);

        setPadding(AppUtil.dp2px(getContext(), 5), AppUtil.dp2px(getContext(), 3),
                AppUtil.dp2px(getContext(), 5), AppUtil.dp2px(getContext(), 3));
        setGravity(Gravity.CENTER_VERTICAL);

        setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(new Rect(0, 0, view.getWidth(), view.getHeight()), AppUtil.dp2px(getContext(), 20));
            }
        });
        setClipToOutline(true);

        if (!flag)
            setCompoundDrawables(normalIcon, null, null, null);
        else
            setCompoundDrawables(clickIcon, null, null, null);

        setClickable(true);
        setOnClickListener(this);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundIconButton, defStyleAttr, 0);
        normalIcon = typedArray.getDrawable(R.styleable.RoundIconButton_normalIcon);
        clickIcon = typedArray.getDrawable(R.styleable.RoundIconButton_clickIcon);
        backgroundColor = typedArray.getColor(R.styleable.RoundIconButton_backColor, 0);
        flag = typedArray.getBoolean(R.styleable.RoundIconButton_selected, false);

        if(normalIcon != null && clickIcon != null) {
            int iconSize = Math.round(getTextSize() * 1.2f);
            normalIcon.setBounds(0, 0, iconSize, iconSize);
            clickIcon.setBounds(0, 0, iconSize, iconSize);
        }

        typedArray.recycle();
    }

    public boolean getFlag() {
        return flag;
    }

    @Override
    public void onClick(View v) {
        if (flag)
            setCompoundDrawables(normalIcon, null, null, null);
        else
            setCompoundDrawables(clickIcon, null, null, null);

        invalidate();

        if (onClickListener != null) {
            onClickListener.onClick(v, flag);
        }
        flag = !flag;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(View v, boolean flag);
    }

}
