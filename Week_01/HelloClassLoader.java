package weeko1;


import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
 * 画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。
 */
public class HelloClassLoader extends ClassLoader {

    public static void main(String... args) throws Exception {

        try {
            Class clazz = new HelloClassLoader().findClass("Hello");
            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("hello");
            if (method != null) {
                method.invoke(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        File file = new File(" /Users/wei/Desktop/课件/讲师秦金卫-资料分享/Hello/Hello.xlass");
        byte[] byteArr = new byte[(int) file.length()];

        try {
            new FileInputStream(file).read(byteArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] bytes = new byte[(int) file.length()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (~byteArr[i]);
        }

        return defineClass(name, bytes, 0, bytes.length);
    }


    private byte[] decode(String str) {
        return java.util.Base64.getDecoder().decode(str);
    }

}
