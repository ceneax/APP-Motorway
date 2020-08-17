package ceneax.app.motorway;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.IOException;

import ceneax.app.motorway.bean.Province;
import ceneax.app.motorway.util.AppUtil;
import ceneax.app.motorway.util.IOUtil;
import ceneax.app.motorway.util.JsonUtil;

public class App extends Application {

    private static final String TAG = "App";

    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        // 初始化bugly
//        String processName = AppUtil.getProcessName(android.os.Process.myPid());
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
//        strategy.setUploadProcess(processName == null || processName.equals(getPackageName()));
//        CrashReport.initCrashReport(this, "18bb299c-613a-40f2-81d9-a249202d2329", false, strategy);
        CrashReport.initCrashReport(this, "18bb299c-613a-40f2-81d9-a249202d2329", false);

        // 读取资源文件的province.json
        try {
            Config.province = JsonUtil.jsonToBean(IOUtil.toString(getAssets().open("province.json")), Province.class);
            Config.currentProvince = Config.province.getJiangsu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }

}
