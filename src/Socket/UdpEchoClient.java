package Socket;


import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UdpEchoClient {
    //客户端的主要流程分为四步
    //1. 从用户这里读取输入的数据
    //2.构造请求发送给服务器
    //3.从服务器读取响应
    //4.吧响应写会客户端

    private DatagramSocket socket = null;
    private String serverIp;
    private int serverPort;

    public UdpEchoClient(String serverIp,int serverPort) throws SocketException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        //客户端创建socket的时候，不需要绑定端口号~~
        socket = new DatagramSocket();
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true){
            //1.读取用户输入的数据
            System.out.print("->");
            String request = scanner.nextLine();
            if(request.equals("exit")){
                break;
            }
            //2.构造请求发送给服务器
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(),
                    request.getBytes().length, InetAddress.getByName(serverIp),serverPort);
            socket.send(requestPacket);
            //3.从服务器读取响应
            DatagramPacket responsePacket = new DatagramPacket(new byte[4096],4096);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(),0,responsePacket.getLength()).trim();
            //4.显示响应数据
            System.out.println(response);
        }
    }

    public static void main(String[] args) throws IOException {
        //ip是一个特殊的ip （环回ip）自己访问自己
        //服务器和客户端都在同一台主机上
        // 客户端写的服务器ip就是环回ip
        // 如果不在同一个主机上  此处的ip就要写成服务器ip
        //post（端口）这个端口要和服务器的端口相匹配
        UdpEchoClient client = new UdpEchoClient("106.14.185.16",9090);
        client.start();
    }
}
/**
 * 理解此处通信的五元组
 * 协议类型：UDP
 * 源ip：客户端的ip（客户端所在的主机ip）
 * 源端口：客户端的端口（操作系统自动分配的端口）
 * 目的ip：服务器的ip  服务器和客户端都在同一台主机上ip就是127.0.0.1
 * 目的端口：9090（服务器启动的时候绑定的端口）
 */
