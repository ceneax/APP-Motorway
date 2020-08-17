package ceneax.app.motorway.mvvm.repository;

import ceneax.app.motorway.bean.GSVideoInfo;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class VideoRepository {

    // 单例对象
    private static VideoRepository instance;

    // request对象
    public IVideoRequest request;

    // 获取单例
    public static VideoRepository getInstance() {
        if (instance == null) {
            instance = new VideoRepository();
        }
        return instance;
    }

    // 构造方法
    public VideoRepository() {
        request = new Retrofit.Builder()
                .baseUrl("https://pubwechat.jchc.cn/kg_pidwx/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IVideoRequest.class);
    }

    // request接口
    public interface IVideoRequest {
        @GET("hsrc/getVideoUrl?clienttype=1&hightvideo=0")
        Call<GSVideoInfo> getGSVideoInfo(@Query("provName") String name, @Query("videoId") String id);

        @GET("hsrc/getGlVideoUrl")
        Call<String> getGLVideoInfo(@Query("provName") String name, @Query("videoId") String id);
    }

}
