package Socket;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class UdpDictServer extends UdpEchoServer{
    private Map<String,String> dict = new HashMap<>();
    public UdpDictServer(int port) throws SocketException {
        super(port);
        //所谓"有道词典" 实现，本质上就是个hash表
        dict.put("cat","小猫");
        dict.put("dog","小狗");
    }

    @Override
    public String process(String request) {
        return dict.getOrDefault(request,"这超出了我的知识范围");
    }

    public static void main(String[] args) throws IOException {
        UdpDictServer server = new UdpDictServer(9090);
        server.start();
    }
}
