package Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * UDP协议无连接，类似于发微信
 * TCP协议有链接，类似于打电话
 */
public class TcpEchoServer {
    //1.初始化服务器
    //2.进入主循环
    //  1)先去从内核中获取到一个TCP的链接
    //  2)处理这个TCP的链接
    //    a)读取请求并解析
    //    b)根据请求计算响应
    //    c)把响应写回给客户端
    private ServerSocket serverSocket = null;

    public  TcpEchoServer(int port) throws IOException {
        //这个操作和前面的UDP类似，也要绑定端口号
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动");
        while (true){
            /*
             * TCP的连接管理是由操作系统内核管理的。
             *    先描述：通信中五元组：协议类型、源ip源端口、目的ip目的端口
             *    在组织：使用一个阻塞队列来组织若干个连接对象
             * 客户端和服务器建立连接的过程，完全由内核进行负责
             * 应用程序的代码感知不到。当连接建立成功：
             * 内核已经把这个连接对象放到咱们的阻塞队列中了
             * 代码中调用的accept就是从阻塞队列中取出一个   ->>>这就是一个生产者消费者模型（线程）
             * 连接对象(在应用程序中的化身就是Socket对象)
             *后续的数据读写都是针对clientSocket这个对象来进行展开的！！
             * 如果服务器启动之后，没有客户端建立连接，此时代码中调用accept就会阻塞，
             * 阻塞到真的有客户端建立连接之后为止
             *
             */
            /*
             * 理解clientSocket 和serverSocket
             * 举个例子，买房子的时候，
             * （外）西装革履的房产中介（马路牙子上，招揽客人）就是serverSocket ->> 处理客户端的链接
             * （内）小姐姐，“置业顾问”（房产销售）有关任何房子的细节都可以问他就是clientSocket ->>和客户端进行具体交互
             *
             */
            //1)先从内核中获取一个TCP连接
            Socket clientSocket = serverSocket.accept();
             //2)处理这个连接
            processConnection(clientSocket);
        }
    }

    /**
     * @问题：一个连接中，客户端和服务器只交互一次吗？
     *
     * 服务器的处理方式
     *1.短连接：一个连接中，客户端和服务器之间只交互一次，交互完毕，就断开连接
     *2.长连接：一个连接中，客户端和服务器之间交互N次，直到满足一定条件再断开连接
     *           效率更高，避免了反复建立连接和断开连接的过程
     */

    private void processConnection(Socket clientSocket) {
        System.out.printf("[%s:%d]客户端上线\n",clientSocket.getInetAddress().toString(),
                clientSocket.getPort());
        //通过 clientSocket来和客户端交互，先做好准备工作，获取到clientSocket 中的流对象
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))){
            //此处先实现一个“长连接”版本的服务器
            //一次连接的处理过程中，需要处理多个请求和响应.
            //这个循环何时结束？当客户端断开连接时，就结束了
            //当客户端断开连接的时候，服务器再去调用readLine 或者 write 方法都会触发异常(IOException)
            while(true) {

                //1.读取请求并解析(此处的readLine 对应的客户端发送数据的格式，必须是按行发送)
                String request = bufferedReader.readLine(); //此处暗含一个信息->客户端发的数据必须是一个按行发送的数据
                //2.根据请求计算响应
                String response = process(request);
                //3.把响应写回到客户端(客户端要按行来读)
                bufferedWriter.write(response+"\n");
                //刷新缓冲区，一定要带着这行代码！！！
                bufferedWriter.flush();
                System.out.printf("[%S:%d] req: %s; resp: %s\n",clientSocket.getInetAddress().toString(),
                        clientSocket.getPort(),request,response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("[%s:%d]客户端下线\n",clientSocket.getInetAddress().toString(),
                    clientSocket.getPort());
        }
    }

    private String process(String request) {
        return request;
    }

    public static void main(String[] args) throws IOException {
        TcpEchoServer server = new TcpEchoServer(9090);
        server.start();
    }
}
