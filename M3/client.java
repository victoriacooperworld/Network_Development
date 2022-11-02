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
                OutputStream output = socket.getOutputStream();
                BufferedWriter clientOutput = new BufferedWriter(new OutputStreamWriter(output,StandardCharsets.UTF_8));
                StringBuilder cmd = new StringBuilder();
                while(true){
                    System.out.println("Please type in a command: ");
                    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                    String keyIn = keyboard.readLine();
                    cmd.append(keyIn);
                    System.out.println("User command: " + cmd.toString());
                    clientOutput.write(cmd.toString()+"\n");
                    clientOutput.flush();

                    InputStream clientIn = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientIn,StandardCharsets.UTF_8));
                    //read the content 
                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        System.out.println(line);
                    }
                    System.out.println("Over!");

                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
