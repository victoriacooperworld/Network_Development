import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) throws IOException {
  
        InetAddress address=InetAddress.getByName("localhost");
        int port=8800;
        Scanner sc = new Scanner(System.in);
        while(true){
            String inp = sc.nextLine();
            byte[] data=inp.getBytes();
            DatagramPacket packet=new DatagramPacket(data, data.length, address, port);
            DatagramSocket socket=new DatagramSocket();
            socket.send(packet);
            byte[] data2=new byte[1024];
            DatagramPacket packet2=new DatagramPacket(data2, data2.length);
            socket.receive(packet2);
            String reply=new String(data2, 0, packet2.getLength());
            System.out.println("Server: "+reply);
        }
        
        //4.关闭资源
        // socket.close();
    }
    
    
}