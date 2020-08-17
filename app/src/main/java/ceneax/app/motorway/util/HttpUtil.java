package ceneax.app.motorway.util;

import android.content.Context;
import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static void parseGLVideoUrl(Context context, String url, OnCompleteListener listener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Handler handler = new Handler(context.getMainLooper());
        Request request = new Request.Builder().url(url).get().build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete("");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String[] tmp = response.body().string().split("http://");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (tmp.length > 0) {
                            listener.onComplete("http://" + tmp[1]);
                        } else {
                            listener.onComplete("");
                        }
                    }
                });
            }
        });
    }

    public interface OnCompleteListener {
        void onComplete(String url);
    }

}
