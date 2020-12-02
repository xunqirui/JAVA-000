package homework.config;

/**
 * DynamicDataSourceContextHolder
 *
 * @author qrXun on 2020/12/2
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> DATASOURCE_CONTEXT_NAME = new ThreadLocal<>();

    /**
     * 放入 dataSourceName 名称
     * @param dateSourceName
     */
    public static void setDatasourceName(String dateSourceName){
        DATASOURCE_CONTEXT_NAME.set(dateSourceName);
    }

    /**
     * 获取 dataSourceName 名称
     */
    public static String getDatasourceName(){
        return DATASOURCE_CONTEXT_NAME.get();
    }

    /**
     * 清空数据源名称
     */
    public static void clearDataSourceName(){
        DATASOURCE_CONTEXT_NAME.remove();
    }



}
