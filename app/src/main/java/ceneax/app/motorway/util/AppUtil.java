package ceneax.app.motorway.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AppUtil {

    /**
     * 将dp转换为px单位
     * @param context 上下文
     * @param dp dp值
     * @return 返回int的px
     */
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 将px转换为dp单位
     * @param context 上下文
     * @param px px值
     * @return 返回int的dp
     */
    public static int px2dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获取状态栏高度
     * @param context 上下文
     * @return 返回int值的px
     */
    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");

        if(resourceId > 0)
            return context.getApplicationContext().getResources().getDimensionPixelSize(resourceId);

        return 0;
    }

    /**
     * 获取可用屏幕宽高
     * @param windowManager 参数
     * @return 返回DisplayMetrics
     */
    public static DisplayMetrics getScreenSize(WindowManager windowManager) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获取真实屏幕宽高
     * @param windowManager 参数
     * @return 返回DisplayMetrics
     */
    public static DisplayMetrics getRealScreenSize(WindowManager windowManager) {
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
