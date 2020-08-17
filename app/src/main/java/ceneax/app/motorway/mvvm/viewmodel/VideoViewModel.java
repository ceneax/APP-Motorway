package ceneax.app.motorway.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ceneax.app.motorway.bean.GSVideoInfo;
import ceneax.app.motorway.bean.Weather;
import ceneax.app.motorway.mvvm.repository.VideoRepository;
import ceneax.app.motorway.mvvm.repository.WeatherRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoViewModel extends ViewModel {

    private MutableLiveData<GSVideoInfo> gsVideoInfo;
    private MutableLiveData<String> glVideoInfo;
    private MutableLiveData<Weather> weather;

    public MutableLiveData<GSVideoInfo> getGsVideoInfo() {
        if (gsVideoInfo == null) {
            gsVideoInfo = new MutableLiveData<>();
        }
        return gsVideoInfo;
    }

    public void loadGSVideoInfo(String name, String id) {
        Call<GSVideoInfo> call = VideoRepository.getInstance().request.getGSVideoInfo(name, id);
        call.enqueue(new Callback<GSVideoInfo>() {
            @Override
            public void onResponse(Call<GSVideoInfo> call, Response<GSVideoInfo> response) {
                gsVideoInfo.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GSVideoInfo> call, Throwable t) {
            }
        });
    }

    public MutableLiveData<String> getGlVideoInfo() {
        if (glVideoInfo == null) {
            glVideoInfo = new MutableLiveData<>();
            glVideoInfo.setValue("");
        }
        return glVideoInfo;
    }

    public void loadGLVideoInfo(String name, String id) {
        Call<String> call = VideoRepository.getInstance().request.getGLVideoInfo(name, id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                glVideoInfo.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public MutableLiveData<Weather> getWeather() {
        if (weather == null) {
            weather = new MutableLiveData<>();
        }
        return weather;
    }

    public void loadWeather(double lng, double lat) {
        Call<Weather> call = WeatherRepository.getInstance().request.getWeather(lng, lat);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                weather.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
            }
        });
    }

}
