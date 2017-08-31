/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nfp103.accov.controle.aerien;

/**
 *
 * @author user
 */
class Deplacement {
    int cap;
    int vitesse;
    public Deplacement(int cap,int vitesse){
        this.cap=cap;
        this.vitesse = vitesse;
        
    }
    public int GetCap(){
        return this.cap;
        
    }
    public int GetVitesse(){
        return this.vitesse;
        
    }
    public void SetCap(int x){
        this.cap=x;
    }
    public void SetVitesse(int y){
        this.vitesse=y;
    }    
}
