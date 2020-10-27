package homework.two;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * HttpUtil
 *
 * @author qrXun on 2020/10/27
 */
public class HttpUtil {

    private static final OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * 同步请求
     *
     * @param url
     * @return url 为 null 了话直接返回 Null
     * @throws IOException
     */
    public static String syncGet(String url) throws IOException {
        if (url == null) {
            return null;
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return new String(response.body().bytes());
        }
    }

    /**
     * 异步 get
     *
     * @param url              请求地址
     * @param responseConsumer 回调函数处理 response
     */
    public static void asyncGet(String url, Consumer<Response> responseConsumer) {
        if (url != null) {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    responseConsumer.accept(response);
                }
            });
        }
    }

    /**
     * pos 请求
     * @param url
     * @param jsonData
     * @return
     * @throws IOException
     */
    public static String post(String url, String jsonData) throws IOException {
        RequestBody body = RequestBody.create(jsonData, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().toString();
        }
    }

}
