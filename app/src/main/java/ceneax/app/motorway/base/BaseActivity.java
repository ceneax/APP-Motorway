package ceneax.app.motorway.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import ceneax.app.motorway.Config;
import ceneax.app.motorway.R;
import ceneax.app.motorway.util.ClassUtil;

public abstract class BaseActivity<V extends ViewModel, D extends ViewDataBinding> extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    protected V viewModel;
    protected D dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置状态栏沉浸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);   //设置状态栏颜色透明
            window.setNavigationBarColor(Color.TRANSPARENT); //设置导航栏颜色透明
        }

        // 根据时间动态改变主题
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if((hour >= 18 && hour <= 24) || (hour >= 0 && hour < 6)) {
            Config.CurrentTheme = R.style.AppTheme_Dark;
        }
        setTheme(Config.CurrentTheme);

        // 设置ViewModel和布局
        viewModel = (V) new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ClassUtil.getTClass(getClass(), 0));
        dataBinding = DataBindingUtil.setContentView(this, getLayoutResId());
        dataBinding.setLifecycleOwner(this);

        setupViewModelDataBinding();
        initVariable();
        initViews(savedInstanceState);
        initDatas();
    }

    /**
     * 抽象方法，方法体中必须返回一个布局资源ID
     * @return int型的布局资源id
     */
    public abstract int getLayoutResId();

    /**
     * 将ViewModel与DataBinding关联
     */
    public abstract void setupViewModelDataBinding();

    /**
     * 初始化变量
     */
    public void initVariable() {}

    /**
     * 初始化view
     */
    public void initViews(Bundle savedInstanceState) {}

    /**
     * 初始化一些本地数据和网络请求
     */
    public void initDatas() {}

}
