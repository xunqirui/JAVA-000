package homework.client;

import com.alibaba.fastjson.JSON;
import homework.annotation.Post;
import homework.api.RpcfxRequest;
import homework.api.RpcfxResponse;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * RpcInterfaceDelegation
 * 对应 aop 代理方法类
 *
 * @author qrXun on 2020/12/16
 */
public class RpcInterfaceDelegation {

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

    /**
     * 指定代理方法
     * @param method
     * @param arguments
     * @return
     * @throws IOException
     */
    @RuntimeType
    public static Object rpcCallback(@Origin Method method, @AllArguments Object[] arguments) throws IOException {
        RpcfxRequest request = new RpcfxRequest();
        request.setMethod(method.getName());
        request.setServiceClass(method.getDeclaringClass().getName());
        request.setParams(arguments);
        Post annotation = method.getAnnotation(Post.class);
        if (annotation == null){
            throw new RuntimeException("no " + Post.class.getName() + " find");
        }
        String url = annotation.url();
        if (url == null || "".equals(url)){
            throw new RuntimeException("url can't be null or ''");
        }
        RpcfxResponse response = post(request, url);
        return JSON.parse(response.getResult().toString());
    }

    private static RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }

}
