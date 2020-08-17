package ceneax.app.motorway.mvvm.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.Observer;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.Timer;
import java.util.TimerTask;

import ceneax.app.motorway.R;
import ceneax.app.motorway.base.BaseBottomSheetDialog;
import ceneax.app.motorway.bean.GLVideoTree;
import ceneax.app.motorway.bean.GSVideoInfo;
import ceneax.app.motorway.bean.GSVideoTree;
import ceneax.app.motorway.bean.ProvinceInfo;
import ceneax.app.motorway.bean.Weather;
import ceneax.app.motorway.databinding.DialogVideoBinding;
import ceneax.app.motorway.mvvm.viewmodel.VideoViewModel;
import ceneax.app.motorway.util.HttpUtil;
import ceneax.app.motorway.util.L;
import ceneax.app.motorway.util.OtherUtil;

public class VideoDialog extends BaseBottomSheetDialog<VideoViewModel, DialogVideoBinding>
        implements Player.EventListener {

    private static final String TAG = "VideoDialog";

    private Object videoInfo;
    private ProvinceInfo provinceInfo;

    private ExoPlayer player;

    private boolean hasPlayed = false;

    private Timer timer;

    public VideoDialog(Object videoInfo, ProvinceInfo provinceInfo) {
        this.videoInfo = videoInfo;
        this.provinceInfo = provinceInfo;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.dialog_video;
    }

    @Override
    public void setupViewModelDataBinding() {
        dataBinding.setViewModel(viewModel);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        player = ExoPlayerFactory.newSimpleInstance(activity, new DefaultRenderersFactory(activity),
                new DefaultTrackSelector(), new DefaultLoadControl()
        );
        dataBinding.dialogVideoPlayer.setPlayer(player);
        player.setPlayWhenReady(true);
        player.addListener(this);
        dataBinding.dialogVideoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

        // 设置标题
        dataBinding.dialogVideoTitle.setText(getString(R.string.dialog_video_title) + getString(R.string.dialog_video_title_unknown));

        viewModel.getGsVideoInfo().observe(this, new Observer<GSVideoInfo>() {
            @Override
            public void onChanged(GSVideoInfo gsVideoInfo) {
                if (gsVideoInfo != null) {
                    dataBinding.dialogVideoTitle.setText(getString(R.string.dialog_video_title) + gsVideoInfo.getMessage());
                    player.prepare(new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                            .createMediaSource(Uri.parse(gsVideoInfo.getViderUrl())));
                }
            }
        });
        viewModel.getGlVideoInfo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String glVideoInfo) {
                if (!TextUtils.isEmpty(glVideoInfo)) {
                    HttpUtil.parseGLVideoUrl(activity, glVideoInfo, new HttpUtil.OnCompleteListener() {
                        @Override
                        public void onComplete(String url) {
                            player.prepare(new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                                    .createMediaSource(Uri.parse(url)));
                        }
                    });
                }
            }
        });
        viewModel.getWeather().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather == null)
                    return;

                dataBinding.dialogVideoWeatherWeather.setText(OtherUtil.parseWeather(weather.getResult().getRealtime().getSkycon()));
                dataBinding.dialogVideoWeatherTemp.setText(weather.getResult().getRealtime().getTemperature() + "℃");
                dataBinding.dialogVideoWeatherWind.setText(weather.getResult().getRealtime().getWind().getSpeed() + "千米/时");
                dataBinding.dialogVideoWeatherNjd.setText(weather.getResult().getRealtime().getVisibility() + "千米");
            }
        });
    }

    @Override
    public void initDatas() {
        loadVideo();
    }

    /**
     * 加载视频
     */
    private void loadVideo() {
        double lng = 0;
        double lat = 0;

        if(videoInfo instanceof GSVideoTree) {
            GSVideoTree gsVideoTree = (GSVideoTree) videoInfo;
            lng = Double.parseDouble(gsVideoTree.getLongitude());
            lat = Double.parseDouble(gsVideoTree.getLatitude());
            viewModel.loadGSVideoInfo(provinceInfo.getName(), gsVideoTree.getId());
        } else if(videoInfo instanceof GLVideoTree) {
            GLVideoTree glVideoTree = (GLVideoTree) videoInfo;
            lng = glVideoTree.getLongitude();
            lat = glVideoTree.getLatitude();
            dataBinding.dialogVideoTitle.setText(getString(R.string.dialog_video_title) + glVideoTree.getCameraName());
            viewModel.loadGLVideoInfo(provinceInfo.getName(), glVideoTree.getCameraId());
        }

        if (timer == null) {
            double finalLng = lng;
            double finalLat = lat;

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    viewModel.loadWeather(finalLng, finalLat);
                }
            }, 0, 60 * 1000 * 5);
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_READY:
                hasPlayed = true;
                dataBinding.dialogVideoLoading.setVisibility(View.GONE);
                dataBinding.dialogVideo.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        if (hasPlayed)
            loadVideo();
        else {
            dataBinding.dialogVideoLoadingText.setText(R.string.dialog_video_loading_text_err);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        if(player != null) {
            player.removeListener(this);
            player.release();
            player = null;
        }
    }

}
