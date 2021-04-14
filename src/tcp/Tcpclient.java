package tcp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCP客户端
 */
public class Tcpclient {
    //服务器ip
    private  static  final String ip = "127.0.0.1";

    //服务器端口号
    private static final int port = 9002;
    public static void main(String[] args) throws IOException {
        //1.创建tcp 客户端 连接到服务器端
        Socket socket = new Socket(ip,port);
        try(BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in)
        ){
            while (true){
                System.out.print("->");
                String msg = scanner.nextLine();
                //2.发送消息给服务器端
                bufferedWriter.write(msg+"\n");
                bufferedWriter.flush();
                //3.接收服务器响应的内容
                String serMsg = bufferedReader.readLine();
                System.out.print(serMsg+"\n");
            }
        }

    }
}
