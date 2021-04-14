package tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP
 */
public class TcpServer {
    //端口号
    private static final int port = 9002;
    public static void main(String[] args) throws IOException {
        //1.创建TCP服务器端
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器启动成功!!!");
        //2.等待客户端的连接
        Socket client =  serverSocket.accept();
        System.out.println(String.format("有客户端连接了，客户端ip:%s,端口号:%d",client.getInetAddress(),client.getPort()));
//        BufferedReader bufferedReader = null;
//        BufferedWriter bufferedWriter = null;
        //拿到读取对象
//        try {
//            bufferedReader = new BufferedReader(
//                    new InputStreamReader(client.getInputStream()));
//            //接收服务器的信息
//            String msg =bufferedReader.readLine();
//            bufferedWriter = new BufferedWriter(
//                    new OutputStreamWriter(client.getOutputStream()));
//            String serMsg = "我收到了@over";
//            bufferedWriter.write(serMsg+"\n"); //必须加\n
//            //刷新缓冲区
//            bufferedWriter.flush();
//        }finally{
//            if(bufferedReader != null){
//                bufferedReader.close();
//            }
//            if(bufferedWriter !=null){
//                bufferedWriter.close();
//            }
//        }
        //try-resouce的写法
        try(BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(client.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(client.getInputStream()))
        ){
            while (true) {
                //3.接收客户端的消息
                String msg = bufferedReader.readLine();
                if(msg !=null && !msg.equals("")){
                    System.out.println("您有新的消息："+msg);
                    //4.返回响应信息给客户端
                    String serMsg = "我收到了@over";
                    bufferedWriter.write(serMsg+"\n");
                    //缓冲区刷新
                    bufferedWriter.flush();
                }
            }
        }
    }
}
