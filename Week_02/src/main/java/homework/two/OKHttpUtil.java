package homework.two;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * HttpUtil
 *
 * @author qrXun on 2020/10/27
 */
public class OKHttpUtil implements HttpClient<Response>{

    private static final OkHttpClient client = new OkHttpClient();

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private static final ExecutorService proxyService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 2,
            1000, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(2048),
            new ThreadFactoryBuilder().setNameFormat("qrxun-pool-%d").build());

    /**
     * 同步请求
     *
     * @param url
     * @return url 为 null 了话直接返回 Null
     * @throws IOException
     */
    @Override
    public String syncGet(String url) throws IOException {
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
    @Override
    public void asyncGet(String url, Map<String, String> headersMap, Consumer<Response> responseConsumer) {
        if (url != null) {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url);
            if (headersMap != null && !headersMap.isEmpty()){
                Headers headers = Headers.of(headersMap);
                requestBuilder.headers(headers);
            }
            Request request = requestBuilder.build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    proxyService.execute(() -> {
                        try {
                            responseConsumer.accept(response);
                        } finally {
                            response.close();
                        }
                    });
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
    @Override
    public String post(String url, String jsonData) throws IOException {
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
