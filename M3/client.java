package M3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


//reference: https://www.codejava.net/java-se/networking/java-socket-server-examples-tcp-ip
public class client {
    public static void main(String[] args) {
        try{
            try (Socket socket = new Socket("localhost", 8000)) {
                while(true){
                    OutputStream output = socket.getOutputStream();
                    BufferedWriter clientOutput = new BufferedWriter(new OutputStreamWriter(output,StandardCharsets.UTF_8));
                    StringBuilder cmd = new StringBuilder();
                    System.out.println("Please type in a command: ");
                    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                    String keyIn = keyboard.readLine();
                    cmd.append(keyIn);
                    clientOutput.write(cmd.toString()+"\n");
                    clientOutput.flush();

                    InputStream clientIn = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientIn,StandardCharsets.UTF_8));
                    //read the content 
                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        System.out.println(line);
                    }
    
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
