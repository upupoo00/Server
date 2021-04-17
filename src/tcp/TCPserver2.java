package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 沾包/半包
 */
public class TCPserver2 {
    private static final int port = 9005;
    //数据传输的最大值
    private static final int leng = 1024;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        Socket clientSocket = serverSocket.accept();
        //读取信息
//        try(InputStream inputStream =  clientSocket.getInputStream()){
//            while (true){
//                byte[] bytes = new byte[leng];
//                int result = inputStream.read(bytes,0,leng);
//                if(result>0){
//                    //表示读取成功
//                    System.out.println("读取客户端消息:"+new String(bytes));
//                }
//            }
//
//        }
        //得到读取对象
        try(BufferedReader reader = new BufferedReader
                (new InputStreamReader(clientSocket.getInputStream()))){
            //按行定义TCP的边界
            while (true){
                String msg = reader.readLine();
                if(msg == null){
                    break;
                }
                System.out.println("接收到客户端的消息："+msg);
            }
        }
    }
}
