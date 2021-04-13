package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP服务器
 */
public class UdpServer {
    private static final int port = 9090;
    private static final int bleng = 1024;
    public static void main(String[] args) throws IOException {
        //1.创建一个 udp服务器
        DatagramSocket socket = new DatagramSocket(port);
        while (true){
            DatagramPacket clientPacket = new DatagramPacket(new byte[bleng],bleng);
            //2.等待客户端链接
            socket.receive(clientPacket);
            //执行此代码表示已经有客户端链接了
            //3.拿到客户端的请求消息
            String msg = new String(clientPacket.getData());
            System.out.println("接收到客户端的消息：" +msg);
            //给客户端反馈消息
            String serMsg = msg.replace("吗？",".").replace("你","我");
            //构建客户端发送数据包
            DatagramPacket serPacket = new DatagramPacket(
                    serMsg.getBytes(),
                    serMsg.getBytes().length, //字节数组的参数
                    clientPacket.getAddress(),
                    clientPacket.getPort()
            );
            //给客户端发送消息
            socket.send(serPacket);
        }
    }
}
