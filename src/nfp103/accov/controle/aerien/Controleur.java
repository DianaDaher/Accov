
package nfp103.accov.controle.aerien;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;



public class Controleur extends Thread {
    Socket soc;
    PrintStream out;
    BufferedReader in;
    BufferedReader entry;
    
    @Override
    public void run(){
        try {
            entry = new BufferedReader(new InputStreamReader(System.in));
            soc= new Socket("localhost",3065);
               
            out= new PrintStream(soc.getOutputStream());
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            
            while (true){
                String uresp= entry.readLine();
                out.println(uresp);
                
                String sresp=in.readLine();
                out.println(sresp);
            }
         } catch (IOException ex) {
            System.out.println("Socket Erreur"+ex);
        }
        
    
    
}
    
}
