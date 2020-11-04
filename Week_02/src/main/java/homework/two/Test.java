package homework.two;


import okhttp3.Response;

import java.io.IOException;
import java.util.Arrays;

/**
 * Test
 *
 * @author qrXun on 2020/10/27
 */
public class Test {

    public static void main(String[] args) {
        try {
            String url = "http://localhost:8808/test";
            // 同步 get
            HttpClient<Response> client = new OKHttpUtil();
            String backResponse = client.syncGet(url);
            System.out.println(backResponse);
            // 异步 get
            client.asyncGet(url, null, response -> {
                try {
                    System.out.println(Arrays.toString(response.body().bytes()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
