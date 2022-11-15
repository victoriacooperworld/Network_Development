import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.xml.transform.SourceLocator;


public class UDPServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket=new DatagramSocket(8800);
        while(true){
            byte[] data =new byte[1024];
            DatagramPacket packet=new DatagramPacket(data, data.length);
            System.out.println("Server has started!");
            socket.receive(packet);
            String info=new String(data, 0, packet.getLength());
            System.out.println("Client:"+info);
            if (info.equals("index")){
                InetAddress address=packet.getAddress();
                int port=packet.getPort();
                String res = getIndex("M4/data");
                byte[] data2=res.getBytes();
                DatagramPacket packet2=new DatagramPacket(data2, data2.length, address, port);
                socket.send(packet2);
            }else if (info.startsWith("get ")){
                InetAddress address=packet.getAddress();
                int port=packet.getPort();
                byte[] okay="ok".getBytes();
                DatagramPacket okayPacket=new DatagramPacket(okay, okay.length, address, port);
                socket.send(okayPacket);

                String res = getFile(info);
                System.out.println(res);
                // if (!res.equals("No such file exist!")){
                byte[] data2=res.getBytes();
                DatagramPacket packet3=new DatagramPacket(data2, data2.length, address, port);
                socket.send(packet3);
                System.out.println("Sent content!");
                // }
            }else{
                InetAddress address=packet.getAddress();
                int port=packet.getPort();
                byte[] data2="No such command".getBytes();
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