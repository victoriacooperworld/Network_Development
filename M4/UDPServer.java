import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPServer {
    public static void main(String[] args) throws IOException {
        /*
         * 接收客户端发送的数据
         */
        //1.创建服务器端DatagramSocket，指定端口
        DatagramSocket socket=new DatagramSocket(8800);
        //2.创建数据报，用于接收客户端发送的数据
        while(true){
            byte[] data =new byte[1024];//创建字节数组，指定接收的数据包的大小
            DatagramPacket packet=new DatagramPacket(data, data.length);
            //3.接收客户端发送的数据
            System.out.println("Server has started!");
            socket.receive(packet);//此方法在接收到数据报之前会一直阻塞
            //4.读取数据
            String info=new String(data, 0, packet.getLength());
            System.out.println("Client:"+info);
            if (info.equals("index")){
                InetAddress address=packet.getAddress();
                int port=packet.getPort();
                String res = getIndex("M4/data");
                byte[] data2=res.getBytes();
                //2.创建数据报，包含响应的数据信息
                DatagramPacket packet2=new DatagramPacket(data2, data2.length, address, port);
                //3.响应客户端
                socket.send(packet2);
            }else if (info.startsWith("get ")){
                InetAddress address=packet.getAddress();
                int port=packet.getPort();
                String res = getFile(info);
                if (!res.equals("No such file exist!")){
                    // byte[] okay = "ok".getBytes();
                    // DatagramPacket packet2=new DatagramPacket(okay, okay.length, address, port);
                    // socket.send(packet2);
                    byte[] data2=res.getBytes();
                    DatagramPacket packet3=new DatagramPacket(data2, data2.length, address, port);
                    socket.send(packet3);
                }
            }else{
                InetAddress address=packet.getAddress();
                int port=packet.getPort();
                byte[] data2="haha".getBytes();
                DatagramPacket packet2=new DatagramPacket(data2, data2.length, address, port);
                socket.send(packet2);
            } 
        }
    }
    public static String getIndex(String dir){
        File dirPath = new File(dir);
        File[] files = dirPath.listFiles();
        if (files.length == 0){
            return "Empty directory!";
        }else{
            System.out.println("Getting the indexes...");
            StringBuilder sb = new StringBuilder();
            for (File file: files){
                sb.append(file.getName());
                sb.append('\n');
            }
            return sb.toString();
        }
    }

    public static String getFile(String cmd) throws FileNotFoundException{
        File dirPath = new File("M4/data");
        File[] files = dirPath.listFiles();
        String fileName = cmd.substring(4,cmd.length());
        System.out.println("server>filename: "+fileName);
        for (File file:files){
            if (file.getName().equals(fileName)){
                FileReader fileReader = new FileReader(file);
                try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                    StringBuilder sb = new StringBuilder();
                    String line = bufferedReader.readLine();
                    while (line!=null){
                        sb.append(line);
                        sb.append('\n');
                        line = bufferedReader.readLine();
                        }
                    return sb.toString();
                } catch (FileNotFoundException e) {
                    throw e;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }      
        }
        return "No such file exist!";
    }
}