package ceneax.app.motorway.mvvm.repository;

import java.util.List;

import ceneax.app.motorway.bean.GLVideoTree;
import ceneax.app.motorway.bean.GSVideoTree;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainRepository {

    // 单例对象
    private static MainRepository instance;

    // request对象
    public IMainRequest request;

    // 获取单例
    public static MainRepository getInstance() {
        if (instance == null) {
            instance =  new MainRepository();
        }
        return instance;
    }

    // 构造方法
    public MainRepository() {
        request = new Retrofit.Builder()
                .baseUrl("https://pubwechat.jchc.cn/kg_pidwx/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IMainRequest.class);
    }

    // request接口
    public interface IMainRequest {
        @GET("hsrc/getVideoTree")
        Call<List<GSVideoTree>> getGSVideoTree(@Query("provName") String name, @Query("province") int code);

        @GET("hsrc/queryGlVideoList.do")
        Call<List<GLVideoTree>> getGLVideoTree(@Query("provName") String name, @Query("province") int code);
    }

}
