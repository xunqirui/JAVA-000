package homework.netty.gateway.client;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

/**
 * Test
 *
 * @author qrXun on 2020/11/2
 */
public class Test {

    public static void main(String[] args) {
        NettyHttpClient client = new NettyHttpClient();
        client.asyncGet("http://localhost:8808/test", null, fullHttpResponse -> {
            ByteBuf buf = fullHttpResponse.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            System.out.println("response -> "+result);
        });
    }

}
