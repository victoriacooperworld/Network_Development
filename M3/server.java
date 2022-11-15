package M3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        String dir = args[0];
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            Socket socket =serverSocket.accept();
            System.out.println("Connect to the server!");
            
            while(true){
                InputStream in = socket.getInputStream();
                System.out.println(in.toString());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                if (new File(dir).isDirectory()){
                    File dirPath = new File(dir);
                    File[] files = dirPath.listFiles();
                    //check the client command
                    //since the file path is valid, we either return indexes or the content
                    String cmd = br.readLine();
                    if (cmd.equals("index")){
                        System.out.println("hello index");
                        if (files.length == 0){
                            bw.write("Empty directory!!");
                            bw.flush();
                        }else{
                            System.out.println("Getting the indexes...");
                            StringBuilder sb = new StringBuilder();
                            for (File file: files){
                                sb.append(file.getName());
                                sb.append('\n');
                            }
                            bw.write(sb.toString());
                            bw.flush();
                        }
                    }else if (cmd.startsWith("get ")){
                        System.out.println("Getting file content....");
                        if (files.length == 0){
                            bw.write("Empty directory!");
                            bw.flush();
                        }else{
                            String fileName = cmd.substring(4,cmd.length());
                            System.out.println("server>filename: "+fileName);
                            for (File file:files){
                                if (file.getName().equals(fileName)){
                                    //find the file, return ok first
                                    bw.write("ok\n");
                                    bw.flush();
                                    //then read the file
                                    FileReader fileReader = new FileReader(file);
                                    try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                                        StringBuilder sb = new StringBuilder();
                                        String line = bufferedReader.readLine();
                                        while (line!=null){
                                            sb.append(line);
                                            sb.append('\n');
                                            line = bufferedReader.readLine();
                                        }
                                        bw.write(sb.toString());
                                    }
                                    bw.flush();
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
