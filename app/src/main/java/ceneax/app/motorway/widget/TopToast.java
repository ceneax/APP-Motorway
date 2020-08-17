package ceneax.app.motorway.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import ceneax.app.motorway.R;
import ceneax.app.motorway.util.AppUtil;

public class TopToast extends PopupWindow {

    public TopToast(Activity activity, String content) {
        TextView textView = new TextView(activity);
        textView.setText(content);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setPadding(AppUtil.dp2px(activity, 10), AppUtil.dp2px(activity, 10),
                AppUtil.dp2px(activity, 10), AppUtil.dp2px(activity, 10));
        textView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), AppUtil.dp2px(activity, 10));
            }
        });
        textView.setClipToOutline(true);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundColor(R.attr.colorPrimary);

        setContentView(textView);
        setWidth(AppUtil.getScreenSize(activity.getWindowManager()).widthPixels - AppUtil.dp2px(activity, 10) * 2);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.TopToastAnim);
        showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP, 0, 0);

        handler.sendEmptyMessageDelayed(0, 3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            dismiss();
        }
    };

}
