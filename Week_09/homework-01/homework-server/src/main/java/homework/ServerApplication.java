package homework;

import homework.api.RpcfxRequest;
import homework.api.RpcfxResolver;
import homework.api.RpcfxResponse;
import homework.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ServerApplication
 *
 * @author qrXun on 2020/12/17
 */
@SpringBootApplication
@RestController
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Autowired
    RpcfxInvoker invoker;

    @PostMapping("/")
    public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
        return invoker.invoke(request);
    }

    @Bean
    public RpcfxInvoker rpcfxInvoker(@Autowired RpcfxResolver rpcfxResolver){
        return new RpcfxInvoker(rpcfxResolver);
    }

}
