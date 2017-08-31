
package nfp103.accov.controle.aerien;

import static nfp103.accov.controle.aerien.SACA.AvionArray;



public class NFP103ACCOVCONTROLEAERIEN {
  

    public static void main(String[] args) {
        
    SACA Server= new SACA();
    
    Server.start();
    
    
    Avion AV1= new Avion();
    Avion AV2= new Avion();
    Avion AV3= new Avion();
    
    AV1.afficher_donnees();
    AV2.afficher_donnees();
    AV3.afficher_donnees();
    // on quitte si on arrive à pas contacter le gestionnaire de vols
//    if (!AV3.ouvrir_communication()||!AV2.ouvrir_communication()||!AV1.ouvrir_communication() ) {
//        System.out.println("Impossible de contacter le gestionnaire de vols\n");
//        
//    }
//    AV3.ouvrir_communication();
//    AV2.ouvrir_communication();
//    AV1.ouvrir_communication();
//     
    AvionArray.add(AV1);
    AvionArray.add(AV2);
    AvionArray.add(AV3);
   System.out.println(AvionArray.size());
    // on se déplace une fois toutes les initialisations faites
    while (true){
        for(int i=0; i<AvionArray.size(); i++){
            AvionArray.get(i).se_deplacer();
    }
    }
    

    
    }
}
    
