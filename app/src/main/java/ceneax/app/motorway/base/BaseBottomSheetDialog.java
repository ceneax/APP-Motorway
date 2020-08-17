package ceneax.app.motorway.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ceneax.app.motorway.R;
import ceneax.app.motorway.util.AppUtil;
import ceneax.app.motorway.util.ClassUtil;

public abstract class BaseBottomSheetDialog<V extends ViewModel, D extends ViewDataBinding> extends BottomSheetDialogFragment {

    private static final String TAG = "BaseBottomSheetDialog";

    protected V viewModel;
    protected D dataBinding;

    public Activity activity;

    // 是否使用dialog默认不带圆角的背景
    private boolean useDefaultDialogBackground;
    private boolean useFullScreen;

    public BaseBottomSheetDialog() {
        this(false, false);
    }

    public BaseBottomSheetDialog(boolean useDefaultDialogBackground, boolean useFullScreen) {
        this.useDefaultDialogBackground = useDefaultDialogBackground;
        this.useFullScreen = useFullScreen;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 设置ViewModel和布局
        viewModel = (V) new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(ClassUtil.getTClass(getClass(), 0));
        dataBinding.setLifecycleOwner(this);

        setupViewModelDataBinding();
        initVariable();
        initViews(savedInstanceState);
        initDatas();
    }

    @Override
    public void onStart() {
        super.onStart();

        // 获取dialog实例
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();

        // 将dialog背景设置圆角
        if (!useDefaultDialogBackground)
            dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackground(activity.getDrawable(R.drawable.shape_layout_corner));
        else
            dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundColor(Color.BLACK);

        // 获取dialog根布局
        FrameLayout rootLayout = dialog.getDelegate().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if(rootLayout != null) {
            // 设置dialog最大高度
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) rootLayout.getLayoutParams();
            layoutParams.height = getDialogHeight();
            rootLayout.setLayoutParams(layoutParams);

            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(rootLayout);
            behavior.setPeekHeight(getDialogHeight());
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private int getDialogHeight() {
        if(useFullScreen)
            return AppUtil.getScreenSize(activity.getWindowManager()).heightPixels;
        else
            return AppUtil.getRealScreenSize(activity.getWindowManager()).heightPixels - (AppUtil.getRealScreenSize(activity.getWindowManager()).heightPixels / 3);
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
     * 获取View之后，用来初始化的方法体，此方法传递了savedInstanceState参数，用于恢复数据
     * @param savedInstanceState 存放数据的对象
     */
    public void initViews(Bundle savedInstanceState) {}

    /**
     * 初始化一些本地数据和网络请求
     */
    public void initDatas() {}

}