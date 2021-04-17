package tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 演示沾包问题
 */
public class TCPClient2 {

    private static final int port = 9005;

    private static final  String ip = "127.0.0.1";
    public static void main(String[] args) throws IOException {
        //创建客户端并连接服务器
        Socket socket = new Socket(ip,port);

        String msg = "hi\n";

        //得到写入对象
        try(OutputStream outputStream = socket.getOutputStream()){
            //发送数据请求
           for(int i = 0;i<10;i++) {
                outputStream.write(msg.getBytes(),0,msg.getBytes().length);
            }
        }
    }
}
