package nfp103.accov.controle.aerien;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import static java.lang.Math.random;
import static java.lang.Thread.sleep;
import java.net.Socket;
//import static nfp103.accov.controle.aerien.SACA.AvionArray;


public class Avion {
    //static final Object k = new Object();
    int MaxAlt=20000;
    int MinAlt=0;
    int MaxVit= 1000;
    int MinVit =200;
    Boolean busy = false;
    String Vol;
    
    Coordonne pos= new Coordonne(0,0,0);
    Deplacement dep=new Deplacement(0,0);
    
    Socket soc;
    PrintStream request;
    BufferedReader response;
    
    public Avion(){
        int rand = (int)(random())*100;
        pos.SetX(1000+rand%1000);     
        pos.SetY(1000+rand%1000); 
        pos.SetAlt(900+rand%100);
        
        dep.SetCap(rand%360);
        dep.SetVitesse(600+rand%200);
        
        char numVol=(char) ('A' + Math.random() * ('z'-'a' + 1));
        Vol = Character.toString(numVol);   
        numVol = (char) ('A' + Math.random() * ('z'-'a' + 1));
        Vol += Character.toString(numVol);
        int Low = 0;
        int High = 9;
        int r = (int) (Math.random() * (High - Low)) + Low;
        Vol+=r;
        int rr = (int) (Math.random() * (High - Low)) + Low;
        Vol+=rr;
        int rrr = (int) (Math.random() * (High - Low)) + Low;
        Vol+=rrr;
       ouvrir_communication(); 
    }
   
    public boolean ouvrir_communication() {
    try{
        soc= new Socket("localhost",3060);
        request= new PrintStream(soc.getOutputStream());
        response = new BufferedReader(new InputStreamReader(soc.getInputStream()));      
       
       return true; 
    }catch(IOException e){
        System.out.println("Communication non ouverte");
    }
        return false;
    
    }

    public void fermer_communication() throws IOException {
        if(soc.isConnected()){
            soc.close();
        }
    }
        
    public void envoyer_caracteristiques() {
        
        String situation= "@#: "+Vol + ";Position:("+pos.GetX()+","+pos.GetY()+","+pos.GetAlt()+
                "); Vitesse :"+dep.GetVitesse() +";Cap: "+dep.GetCap()+".";
        request.println(situation);
          
    }
    
    public void changer_vitesse(int vitesse) {
    if (vitesse < 0)
        dep.SetVitesse(0);
    else if (vitesse > MaxVit)
        dep.SetVitesse(MaxVit);
    else dep.SetVitesse(vitesse);
    }
    
    public void changer_cap(int cap) {
    if ((cap >= 0) && (cap < 360))
        dep.SetCap(cap);
    }
    
    public void changer_altitude(int alt) {
    if (alt < 0)
        pos.SetAlt(0);
    else if (alt > MaxAlt)
        pos.SetAlt(MaxAlt);
    else pos.SetAlt(alt);
    }
    
    public void afficher_donnees() {
       
   System.out.println("@#: "+Vol + ";Position:("+pos.GetX()+","+pos.GetY()+","+pos.GetAlt()+
                "); Vitesse :"+dep.GetVitesse() +";Cap: "+dep.GetCap()+".");
   
    }
    public void calcul_deplacement() {
    float cosinus, sinus;
    float dep_x, dep_y;
    int nb;

    if (dep.vitesse < MinVit) {
        System.out.println("Vitesse trop faible : crash de l'avion\n");
        try {
            this.fermer_communication();
          
        } catch (IOException ex) {
            System.out.println("Erreur");
        }
       
    }
    if (pos.GetAlt() == 0) {
        System.out.println("L'avion s'est ecrase au sol\n");
        try {
            this.fermer_communication();
        } catch (IOException ex) {
             System.out.println("Erreur");
        }
       
    }
    //cos et sin ont un paramétre en radian, dep.cap en degré nos habitudes francophone
    /* Angle en radian = pi * (angle en degré) / 180 
       Angle en radian = pi * (angle en grade) / 200 
       Angle en grade = 200 * (angle en degré) / 180 
       Angle en grade = 200 * (angle en radian) / pi 
       Angle en degré = 180 * (angle en radian) / pi 
       Angle en degré = 180 * (angle en grade) / 200 
     */

    cosinus = (float) Math.cos(dep.cap * 2 * Math.PI / 360);
    sinus = (float) Math.sin(dep.cap * 2 * Math.PI / 360);

    //newPOS = oldPOS + Vt
    dep_x = cosinus * dep.vitesse * 10 / MinVit;
    dep_y = sinus * dep.vitesse * 10 / MinVit;

    // on se d�place d'au moins une case quels que soient le cap et la vitesse
    // sauf si cap est un des angles droit
    if ((dep_x > 0) && (dep_x < 1)) dep_x = 1;
    if ((dep_x < 0) && (dep_x > -1)) dep_x = -1;

    if ((dep_y > 0) && (dep_y < 1)) dep_y = 1;
    if ((dep_y < 0) && (dep_y > -1)) dep_y = -1;

    //printf(" x : %f y : %f\n", dep_x, dep_y);

    pos.SetX(pos.GetX() + (int) dep_x);
    pos.SetY(pos.GetY() + (int) dep_y);

    afficher_donnees();
    
    }
    public synchronized void se_deplacer() {
        
   
        try {
           
            sleep(500);
        } catch (InterruptedException ex) {
           System.out.println("Erreur");
        }
        calcul_deplacement();
        envoyer_caracteristiques();
    }

}
 
        
        
        
        
    
   
    
   
    

