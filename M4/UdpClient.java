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
        System.out.println("Please input a command: ");
        Scanner sc = new Scanner(System.in);
        
        while(true){
            String inp = sc.nextLine();
            byte[] data=inp.getBytes();
            DatagramPacket packet=new DatagramPacket(data, data.length, address, port);
            DatagramSocket socket=new DatagramSocket();
            socket.send(packet);

            //receive data
            if (inp.equals("index")){
                byte[] data2=new byte[1024];
                DatagramPacket packet2=new DatagramPacket(data2, data2.length);
                socket.receive(packet2);

                String reply=new String(data2, 0, packet2.getLength());
                System.out.println("Server: "+reply);
            }else if(inp.startsWith("get ")){
                byte[] data2=new byte[1024];
                DatagramPacket packet2=new DatagramPacket(data2, data2.length);
                socket.receive(packet2);
            
                byte[] data3=new byte[1024];
                DatagramPacket packet3=new DatagramPacket(data3, data3.length);
                socket.receive(packet3);

                String reply=new String(data2, 0, packet2.getLength());
                System.out.println("Server: "+reply);

                String reply1 = new String(packet3.getData(), packet3.getOffset(), packet3.getLength());
                System.out.println("Server: "+ reply1);
            }
            
        }
        

    }
    
    
}