package udp;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * UDP客户端
 */
public class UdpClient {
    //服务器端端口号
    private static final int port =9090 ;
    //服务器ip
    private  static final String ip = "106.14.185.16";
    //数据包大小
    private  static  final int bleng = 1024;
    public static void main(String[] args) throws IOException {
        //1.创建客户端
        DatagramSocket socket = new DatagramSocket();
        //用户输入发送的消息
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("->");
            //接收到用户输入的信息
            String msg = scanner.nextLine();
            DatagramPacket datagramPacket = new DatagramPacket(
                    msg.getBytes(),
                    msg.getBytes().length,
                    InetAddress.getByName(ip),
                    port
            );
            //3.发送消息
            socket.send(datagramPacket);
            DatagramPacket serPacket = new DatagramPacket(
                    new byte[bleng],
                    bleng
            );
            socket.receive(serPacket);
            System.out.println(new String(serPacket.getData()));
        }
    }
}
