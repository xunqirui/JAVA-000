package homework.api;

public interface RpcfxResolver {

    Object resolve(String serviceClass) throws ClassNotFoundException;

}
