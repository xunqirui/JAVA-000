package homework.two;


import java.io.IOException;

/**
 * Test
 *
 * @author qrXun on 2020/10/27
 */
public class Test {

    public static void main(String[] args) {
        try {
            // 同步 get
            String url = "http://localhost:8808/test";
            String backResponse = HttpUtil.syncGet(url);
            System.out.println(backResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
