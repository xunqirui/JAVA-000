package homework.netty.gateway.server.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * SingLetonConfig
 *
 * @author qrXun on 2020/11/3
 */
public class PropertiesSingletonConfig {

    private String port;

    private Map<String, String[]> router;

    private static PropertiesSingletonConfig singletonConfig = null;

    private static final String DEFAULT_PORT = "8888";

    private static final String DEFAULT_ROUTER = "http://localhost:8808";

    /**
     * properties 设置端口号的 key 名称
     */
    public static final String SERVER_PORT = "server.port";

    /**
     * properties 设置 router 的前缀
     */
    private static final String PREFIX_ROUTER = "router";

    private PropertiesSingletonConfig(String port, Map<String, String[]> router) {
        this.port = port;
        this.router = router;
    }

    public String getPort() {
        return port;
    }

    public Map<String, String[]> getRouter() {
        return router;
    }


    public static PropertiesSingletonConfig getInstance(String propertiesPath){
        if (singletonConfig == null){
            Map<String, String[]> routerMap = new HashMap<>();
            if (propertiesPath == null){
                return defaultBean(routerMap);
            }
            try (InputStream inputStream = PropertiesSingletonConfig.class.getResourceAsStream(propertiesPath)){
                if (inputStream == null){
                    return defaultBean(routerMap);
                }
                ResourceBundle resourceBundle = new PropertyResourceBundle(inputStream);
                String port = DEFAULT_PORT;
                Enumeration<String> stringEnumeration = resourceBundle.getKeys();
                while (stringEnumeration.hasMoreElements()){
                    String key = stringEnumeration.nextElement();
                    if (SERVER_PORT.equals(key)){
                        port = Optional.of(resourceBundle.getString(SERVER_PORT)).orElse(DEFAULT_PORT);
                    } else if (key.startsWith(PREFIX_ROUTER)){
                        String router = Optional.of(resourceBundle.getString(key)).orElse(DEFAULT_ROUTER);
                        String[] proxyArray = router.split(",");
                        String[] keyArray = key.split("\\.");
                        routerMap.put(keyArray[keyArray.length - 1], proxyArray);
                    }
                }
                singletonConfig = new PropertiesSingletonConfig(port, routerMap);
                return singletonConfig;
            } catch (IOException e) {
                e.printStackTrace();
                return defaultBean(routerMap);
            }
        }
        return singletonConfig;
    }

    /**
     * 创建默认 config
     * @param routerMap 允许为 Null
     * @return
     */
    private static PropertiesSingletonConfig defaultBean(Map<String, String[]> routerMap){
        Map<String, String[]> defaultMap = Optional.of(routerMap).orElse(new HashMap<>());
        return new PropertiesSingletonConfig(DEFAULT_PORT, defaultMap);
    }
}
