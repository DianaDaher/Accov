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
class Coordonne {
    private int x;
    private int y;
    private int alt;
    
    public Coordonne(int x, int y,int alt){
        this.x=x;
        this.y=y;
        this.alt=alt;
                
    }
    public int GetX(){
        return this.x;
        
    }
    public int GetY(){
        return this.y;
        
    }
    public int GetAlt(){
        return this.alt;
        
    }
    public void SetX(int x){
        this.x=x;
    }
    public void SetY(int y){
        this.y=y;
    }
    public void SetAlt(int alt){
        this.alt=alt;
    }
}
