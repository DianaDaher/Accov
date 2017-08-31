
package nfp103.accov.controle.aerien;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.tan;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SACA extends Thread{
   // static final Object k = new Object();
    
    public static ArrayList<Avion> AvionArray= new ArrayList<Avion>();
    
    public SACA(){
    }
    void checkForCrash(){
    for(int i=0;i<AvionArray.size();i++){
        Avion Temp=(Avion) AvionArray.get(i);
        float a1 = (float) tan(Temp.dep.GetCap()*Math.PI/360);
        float b1 = Temp.pos.GetY() - a1* Temp.pos.GetX();
        
        for(int j=i+1;j<AvionArray.size();j++){
             Avion Temp2=(Avion) AvionArray.get(i);
            if(Temp.pos.GetAlt()==Temp2.pos.GetAlt()){
                //same height, maybe a crash will happen
                float a2 = (float) tan(Temp2.dep.GetCap()*Math.PI/360);
                float b2 = Temp2.pos.GetY() - a1* Temp2.pos.GetX();
                
                if(Temp.dep.GetCap()%180 != Temp2.dep.GetCap()%180){
                    //2 lines are not parrallel, find intersection
                    double x3 = (b2-b1)/(a1-a2);
                    double y3 = a1*x3+b1;
                    
                    if((Temp.dep.GetCap()<180 && y3>Temp.pos.GetY()) || (Temp.dep.GetCap()>180 && y3<Temp.pos.GetY())){
                        //plane 1 eligible for a crash
                        if((Temp2.dep.GetCap()<180 && y3>Temp2.pos.GetY()) || (Temp2.dep.GetCap()>180 && y3<Temp2.pos.GetY())){
                           
                            System.out.println("Plane" +Temp.Vol+" and plane " +Temp2.Vol+" will crash at position : "+ x3+ " ," +y3);
                        }
                    }
                }
                
            }
        }
    }
}
    public  void checkInMessages(BufferedReader buf) throws IOException{
       
               String[] Command;
               Command=buf.readLine().split(" ");
               if(Command.length ==3){
                    for(int i=0;i<AvionArray.size();i++) {
                       Avion AV= (Avion) AvionArray.get(i);
                        if(AV.Vol.equals(Command[0])){
                            if(!AV.busy){
                                AV.busy= true;
                           switch (Command[1]) {
                               case "cap":AV.changer_cap(Integer.parseInt(Command[2]));
                               case "alt":AV.changer_altitude(Integer.parseInt(Command[2]));
                               case "vit": AV.changer_vitesse(Integer.parseInt(Command[2]));
                               case "free":AV.busy= false;
                               default: System.out.println("Commande non comprise");
                           }
                           this.checkForCrash();
                            }
                            else{
                              System.out.println("L'avion est entrain d'etre controler par un autre controleur");
                            }
                        }else{
                           System.out.println("Avion non trouve");
                        }
                       
                    }
                }else{
                   System.out.println("Commande non comprise");
                }
               
        
}
    @Override
    public void  run(){
        try {
            
            ServerSocket servavion= new ServerSocket(3060);
            Socket soc= servavion.accept();
           
            ServerSocket servcontr= new ServerSocket(3065);
            Socket sock= servcontr.accept();
            
            
             BufferedReader buf= new BufferedReader(new InputStreamReader(soc.getInputStream()));
             BufferedReader buf2= new BufferedReader(new InputStreamReader(sock.getInputStream()));
        
             while(true){
            String msg = buf.readLine();
            
                 System.out.println(msg);
                 this.checkInMessages(buf2);
                 
        
            }
        }
        
    
    catch (IOException ex) {
           
            Logger.getLogger(SACA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
