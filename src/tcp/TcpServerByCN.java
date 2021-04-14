package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 英译汉服务器
 */
public class TcpServerByCN {
    // 端口号
    private static final int port = 9003;

    // 定义翻译字典
    static HashMap<String, String> dirMap = new HashMap<>();

    static {
        dirMap.put("hello", "你好");
        dirMap.put("cat", "猫");
        dirMap.put("dog", "狗");
    }

    public static void main(String[] args) throws IOException {
        // 1.创建tcp服务器
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("英译汉服务器已启动~");

        // 2.等待客户端连接
        Socket client = serverSocket.accept();

        // 执行到此行说明已经有客户端连接
        System.out.println(String.format("有客户端连接了，客户端IP：%s，端口：%d",
                client.getInetAddress().getHostAddress(),
                client.getPort()));

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream()));
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(client.getInputStream()))) {
            while (true) {
                // 3.得到客户端的英文单词
                String en = reader.readLine();
                if (en != null && !en.equals("")) {
                    // 4.英译汉的解决处理方法
                    String cn = processData2(en);
                    // 5.将结果返回给客户端
                    writer.write(cn + "\n");
                    writer.flush();
                }
            }
        }
    }

    /**
     * 英译汉方式2
     * @param en
     * @return
     */
    private static String processData2(String en) {
        // todo:可以调用数据库查询英文的结果
        String cn = "未知";
        cn = dirMap.get(en);
        return cn;
    }

    /**
     * 英译汉方式 1
     * @param en
     * @return
     */
    private static String processData(String en) {
        String cn = "未知";
        switch (en) {
            case "hello":
                cn = "你好";
                break;
            case "cat":
                cn = "猫";
                break;
            case "dog":
                cn = "狗";
                break;
            // ...
            default:
                break;
        }
        return cn;
    }
}
