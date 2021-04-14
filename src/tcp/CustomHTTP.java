package tcp;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 简易版 HTTP
 */
public class CustomHTTP {
    // 端口号
    private static final int port = 9004;

    public static void main(String[] args) throws IOException {
        // 1.创建一个 tcp 服务器
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器已启动");
        // 2.等待客户端的连接
        Socket socket = serverSocket.accept();
        // 3.得到两个读写对象
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()))
        ) {
            // 读取首行信息【方法类型 URL HTTP版本号】
            String firstLine = reader.readLine();
            if (firstLine != null && !firstLine.equals("")) {
                String[] fLine = firstLine.split(" ");
                // 读取到请求方法的类型
                String method = fLine[0];
                // 读取到 URL
                String url = fLine[1];
                // 读取 HTTP 版本号
                String httpVer = fLine[2];
                System.out.println(String.format("读取到客户端请求，方法类型：%s,URL：%s,版本：%s",
                        method, url, httpVer));
                // 4.业务逻辑处理
                String content = "<h1>未知</h1>";
                if (url.contains("404")) {
                    content = "<h1>没有找到此页面</h1>";
                } else if (url.contains("200")) {
                    content = "<h1>你好，世界！</h1>";
                }
                // 5.将结果打印到浏览器上
                // 写入首行信息
                writer.write(httpVer + " 200 ok\n");
                // 写入 Head（Content-Type/Content-Length）
                writer.write("Content-Type: text/html;charset=utf-8\n");
                writer.write("Content-Length: " + content.getBytes().length + "\n");
                // 写入空行
                writer.write("\n");
                // 写入 body
                writer.write(content);
                writer.flush();
            }
        }
    }
}