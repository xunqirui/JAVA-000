package homework.two;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * XlassClassLoader
 * <p>
 * 作业第二题
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
 *
 * @author qrXun on 2020/10/19
 */
public class XlassClassLoader extends ClassLoader {

    /**
     * 寻找指定路径 Class 对象
     *
     * @param filePath  文件路径
     * @param className 类名称
     * @return
     */
    public Class<?> findClass(String filePath, String className) throws ClassNotFoundException {
        ByteBuffer outBuffer = null;
        try (FileChannel channel = new FileInputStream(filePath).getChannel()) {
            ByteBuffer fileByteBuffer = ByteBuffer.allocate((int) channel.size());
            outBuffer = ByteBuffer.allocate((int) channel.size());
            while (channel.read(fileByteBuffer) != -1) {
                fileByteBuffer.flip();
                for (byte eachByte : fileByteBuffer.array()){
                    outBuffer.put((byte) (255 - eachByte));
                }
                fileByteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (outBuffer != null){
            outBuffer.flip();
            byte[] actualByte = outBuffer.array();
            return defineClass(className, actualByte, 0, actualByte.length);
        }else{
            throw new ClassNotFoundException(className + "class is not found");
        }
    }

    public static void main(String[] args) {
        XlassClassLoader loader = new XlassClassLoader();
        try {
            Class helloClass = loader.findClass("./Week_01/src/main/resource/META-INF/Hello.xlass", "Hello");
            Method method = helloClass.getDeclaredMethod("hello");
            Object helloObject = helloClass.newInstance();
            method.invoke(helloObject);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
