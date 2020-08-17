package ceneax.app.motorway.mvvm.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ceneax.app.motorway.Config;
import ceneax.app.motorway.R;
import ceneax.app.motorway.base.BaseActivity;
import ceneax.app.motorway.bean.GLVideoTree;
import ceneax.app.motorway.bean.GSVideoTree;
import ceneax.app.motorway.bean.ProvinceInfo;
import ceneax.app.motorway.databinding.ActivityMainBinding;
import ceneax.app.motorway.mvvm.viewmodel.MainViewModel;
import ceneax.app.motorway.util.AppUtil;
import ceneax.app.motorway.util.DialogUtil;
import ceneax.app.motorway.util.MapUtil;
import ceneax.app.motorway.widget.RoundIconButton;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements RoundIconButton.OnClickListener {

    private static final String TAG = "MainActivity";

    private AMap aMap;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void setupViewModelDataBinding() {
        dataBinding.setViewModel(viewModel);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        // 创建地图
        dataBinding.activityMainMap.onCreate(savedInstanceState);

        // 初始化地图控制器
        aMap = dataBinding.activityMainMap.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);

        // 地图背景颜色会造成状态栏文字看不清，根据地图样式动态换状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Config.CurrentTheme == R.style.AppTheme) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        } else {
            aMap.setMapType(AMap.MAP_TYPE_NIGHT);
        }

        // 设置marker
        viewModel.getGsVideoTrees().observe(this, new Observer<List<GSVideoTree>>() {
            @Override
            public void onChanged(List<GSVideoTree> gsVideoTrees) {
                for (GSVideoTree gsVideoTree : gsVideoTrees) {
                    ImageView marker = new ImageView(MainActivity.this);
                    marker.setLayoutParams(new ViewGroup.LayoutParams(AppUtil.dp2px(MainActivity.this, 20), AppUtil.dp2px(MainActivity.this, 20)));
                    marker.setImageResource(R.drawable.ic_gs_normal);

                    aMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(gsVideoTree.getLatitude()), Double.parseDouble(gsVideoTree.getLongitude())))
//                            .title(gsVideoTree.getId())
                            .icon(BitmapDescriptorFactory.fromView(marker))
                    ).setObject(gsVideoTree);
                }
            }
        });
        viewModel.getGlVideoTrees().observe(this, new Observer<List<GLVideoTree>>() {
            @Override
            public void onChanged(List<GLVideoTree> glVideoTrees) {
                for (GLVideoTree glVideoTree : glVideoTrees) {
                    ImageView marker = new ImageView(MainActivity.this);
                    marker.setLayoutParams(new ViewGroup.LayoutParams(AppUtil.dp2px(MainActivity.this, 20), AppUtil.dp2px(MainActivity.this, 20)));
                    marker.setImageResource(R.drawable.ic_gl_normal);

                    aMap.addMarker(new MarkerOptions()
                            .position(new LatLng(glVideoTree.getLatitude(), glVideoTree.getLongitude()))
//                            .title(glVideoTree.getCameraId())
                            .icon(BitmapDescriptorFactory.fromView(marker))
                    ).setObject(glVideoTree);
                }
            }
        });

        aMap.addOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                new VideoDialog(marker.getObject(), Config.currentProvince).show(getSupportFragmentManager(), TAG);
                return true;
            }
        });

        // 给操作按钮绑定事件
        dataBinding.activityMainBtGs.setOnClickListener(this);
        dataBinding.activityMainBtGl.setOnClickListener(this);
        dataBinding.activityMainBtShip.setOnClickListener(this);
        dataBinding.activityMainBtFwq.setOnClickListener(this);
        dataBinding.activityMainBtSfz.setOnClickListener(this);
        dataBinding.activityMainBtEvent.setOnClickListener(this);
        dataBinding.activityMainBtChangePro.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        loadData();
    }

    private void loadData() {
        // 设置默认中心坐标
        MapUtil.changeProvince(aMap, Config.currentProvince);
        // 清除所有marker
        MapUtil.clearAllMarkers(aMap);

        if (dataBinding.activityMainBtGs.getFlag())
            viewModel.loadGSVideoTrees(Config.currentProvince.getName(), Config.currentProvince.getCode());
        if (dataBinding.activityMainBtGl.getFlag())
            viewModel.loadGLVideoTrees(Config.currentProvince.getName(), Config.currentProvince.getCode());
    }

    @Override
    public void onClick(View v, boolean flag) {
        switch (v.getId()) {
            case R.id.activity_main_bt_gs:
//                MapUtil.showOrHideTypeMarker(aMap, GSVideoTree.class, !flag);
                if (flag) {
                    MapUtil.showOrHideTypeMarker(aMap, GSVideoTree.class, false);
                } else {
                    if (viewModel.getGsVideoTrees().getValue().size() <= 0) {
                        viewModel.loadGSVideoTrees(Config.currentProvince.getName(), Config.currentProvince.getCode());
                    }
                    MapUtil.showOrHideTypeMarker(aMap, GSVideoTree.class, true);
                }
                break;
            case R.id.activity_main_bt_gl:
                if (flag) {
                    MapUtil.showOrHideTypeMarker(aMap, GLVideoTree.class, false);
                } else {
                    if (viewModel.getGlVideoTrees().getValue().size() <= 0) {
                        viewModel.loadGLVideoTrees(Config.currentProvince.getName(), Config.currentProvince.getCode());
                    }
                    MapUtil.showOrHideTypeMarker(aMap, GLVideoTree.class, true);
                }
                break;
            case R.id.activity_main_bt_ship:
                DialogUtil.showTopToast(this, "下个版本才能支持这个功能，等一等吧！");
                break;
            case R.id.activity_main_bt_fwq:
                DialogUtil.showTopToast(this, "下个版本才能支持这个功能，等一等吧！");
                break;
            case R.id.activity_main_bt_sfz:
                DialogUtil.showTopToast(this, "下个版本才能支持这个功能，等一等吧！");
                break;
            case R.id.activity_main_bt_event:
                DialogUtil.showTopToast(this, "下个版本才能支持这个功能，等一等吧！");
                break;
            case R.id.activity_main_bt_change_pro:
                Class clazz = Config.province.getClass();
                Field[] fields = clazz.getDeclaredFields();
                // 给dialog用
                List<String> datas = new ArrayList<>();
                List<ProvinceInfo> provinceInfos = new ArrayList<>();

                for (Field field : fields) {
                    field.setAccessible(true);
                    ProvinceInfo provinceInfo;
                    try {
                        provinceInfo = (ProvinceInfo) field.get(Config.province);
                        datas.add(provinceInfo.getName());
                        provinceInfos.add(provinceInfo);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                DialogUtil.showBottomList(this, getString(R.string.activity_main_choose_pro), datas, new DialogUtil.OnSelectedListener() {
                    @Override
                    public void onSelected(String s, int position) {
                        // 切换省份
                        Config.currentProvince = provinceInfos.get(position);
                        loadData();
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBinding.activityMainMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataBinding.activityMainMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBinding.activityMainMap.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        dataBinding.activityMainMap.onSaveInstanceState(outState);
    }

}