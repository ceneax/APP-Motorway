package ceneax.app.motorway.mvvm.repository;

import ceneax.app.motorway.bean.Weather;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class WeatherRepository {

    private static WeatherRepository instance;

    // request对象
    public IWeatherRequest request;

    // 获取单例
    public static WeatherRepository getInstance() {
        if (instance == null) {
            instance = new WeatherRepository();
        }
        return instance;
    }

    // 构造方法
    public WeatherRepository() {
        request = new Retrofit.Builder()
                .baseUrl("https://api.caiyunapp.com/v2.5/Y2FpeXVuIGFuZHJpb2QgYXBp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IWeatherRequest.class);
    }

    // request接口
    public interface IWeatherRequest {
        @GET("{lng},{lat}/weather?lang=zh_CN")
        Call<Weather> getWeather(@Path("lng") double lng, @Path("lat") double lat);
    }

}
