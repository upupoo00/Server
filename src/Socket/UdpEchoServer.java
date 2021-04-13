package Socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpEchoServer {
    //对于一个服务器程序来说，核心流程也要分为两步
    //1.初始化操作（实例化Socket对象）
    //2.进入主循环，接受并处理请求（死循环）
    //  a）读取数据并解析
    //  b）根据请求计算响应
    //  c）把响应结果写回客户端

    private DatagramSocket socket = null;

    public UdpEchoServer(int port) throws SocketException {
        //new 这个Socket对象的时候，就会让当前的socket对象和一个ip
        //地址以及一个端口号关联起来（绑定端口）
        //在构造socket 的时候如果没写ip，默认的是0.0.0.0(特殊ip)->
        // 会关联到这个主机的所有ip
        socket = new DatagramSocket(port);
    }

    /**
     * socket 对象本质上是一个文件，这个文件是网卡的抽象
     * ip 是用来决定互联网上某个主机的位置
     * port(端口号) 是决定数据交给这个主机上的哪个程序
     *
     * 这里的关系不是一一对应 ，是配合使用，换句话说
     * 同一个ip上面的N个服务器程序就有N个端口号
     */
    public  void start() throws IOException {
        System.out.println("服务器启动");
        while(true){
            // a）读取数据并解析
            //DatagramPacket UDP socket 发送接收数据的基本单位
            DatagramPacket requestPacket = new DatagramPacket(new byte[4096],4096);

            //程序启动之后，马上就能执行到receive 操作
            //大概率情况下，调用receive的时候，客户端还未启动，没有发出任何数据
            //此时receive操作就会阻塞，一直阻塞到真有数据过来为止(此处的阻塞时间完全不可预期)
            //当真的有客户端数据过来之后，此时的receive就回吧收到的数据放到DatagramPacket对象的缓冲区中
            socket.receive(requestPacket);

            /**
             * 为什么要用trim方法？
             * 用户实际发送的数据可能远远小于4096
             * 而此处getLength得到的长度就是4096
             * 通过trim就可以去掉不必要的空白字符
             */
            //此处是要把请求数据，转成一个String(本来请求是一个byte[])
            String request = new String(requestPacket.getData(),
                    0,requestPacket.getLength()).trim();

            //  b）根据请求计算响应
            String response = process(request);
            //  c）吧响应写回客户端，响应数据就是response ，需要包装成一个Packet对象
            DatagramPacket responsePacket = new DatagramPacket(response.getBytes(),
                    response.getBytes().length,requestPacket.getSocketAddress());
            socket.send(requestPacket);

            //这是一条锦上添花的操作，打印一条请求日志
            System.out.printf("[%s:%d] req: %s; resp: %s\n", requestPacket.getAddress().toString(),
                    requestPacket.getPort(),request,response);
        }
    }

    public String process(String request){
        //由于此处是一个 echo server，请求的内容是啥，响应内容就是啥
        //如果是一个更复杂的服务器，此处就需要包含更多的业务逻辑来进行具体计算
        return request;
    }

    public static void main(String[] args) throws IOException {
        UdpEchoServer server = new UdpEchoServer(9090);
        server.start();
    }
}
