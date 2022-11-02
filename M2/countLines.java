package M2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//reference: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
class CountLines{

    public static int count(String path) throws IOException{
        // Creating an object of BufferedReader class
        File file = new File(path);
    
        if (!file.exists()){
            System.out.println("File "+ path + " doesn't exist!");
            return 0;
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        int res = 0;
        while ((br.readLine()) != null)
            res++;
        System.out.println(path + ": " + res);
        br.close();
        return res;
        
        
    }
    public static void main(String[] args) throws IOException{
        for (int i = 0; i < args.length; i++)
            count(args[i]);
    }
}
