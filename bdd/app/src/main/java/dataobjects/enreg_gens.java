package dataobjects;

/**
 * Created by formation on 10/02/2016.
 */

public class enreg_gens {

    // Attributs :
    private int id;
    private String Nom;
    private String Prenom;

    // Getter/Setter :
    public int getId(){
        return this.id;
    }

    public String getNom(){
        return this.Nom;
    }

    public String getPrenom(){
        return this.Prenom;
    }

    public void setId(int i){
        this.id = i;
    }

    public void setNom(String a){
        this.Nom = a;
    }

    public void setPrenom(String a){
        this.Prenom = a;
    }

    // Constructor :
    public enreg_gens(int id){
        setId(id);
        setNom("");
        setPrenom("");
    }

    public enreg_gens(int id, String Nom, String Prenom){
        setId(id);
        setNom(Nom);
        setPrenom(Prenom);
    }

}
