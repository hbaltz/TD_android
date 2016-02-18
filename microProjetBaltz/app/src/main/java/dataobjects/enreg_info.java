package dataobjects;

/**
 * Created by hbaltz on 10/02/2016.
 */

public class enreg_info {

    // Attributs :
    private int id;
    private double Long;
    private double Lat;
    private String Desc;
    private String Type;
    private String Photo;

    // Getter/Setter :
    public int getId(){
        return this.id;
    }

    public double getLong(){
        return this.Long;
    }

    public double getLat(){
        return this.Lat;
    }

    public String getDesc() {return this.Desc;}

    public String getType(){return this.Type;}

    public String getPhoto(){return this.Photo;}

    public void setId(int i){
        this.id = i;
    }

    public void setLong(double a){
        this.Long = a;
    }

    public void setLat(double a){
        this.Lat = a;
    }

    public void setDesc(String a){
        this.Desc = a;
    }

    public void setType(String a){
        this.Type = a;
    }

    public void setPhoto(String a){
        this.Photo = a;
    }


    // Constructor :
    public enreg_info(int id){
        setId(id);
        setLong(0);
        setLat(0);
        setDesc("");
        setType("");
        setPhoto("");
    }

    public enreg_info(int id, double Lat, double Long){
        setId(id);
        setLat(Lat);
        setLong(Long);
        setDesc("");
        setType("");
        setPhoto("");
    }

    public enreg_info(int id, double Lat, double Long , String Desc){
        setId(id);
        setLat(Lat);
        setLong(Long);
        setDesc(Desc);
        setType("");
        setPhoto("");
    }

    public enreg_info(int id, double Lat, double Long , String Desc , String Type){
        setId(id);
        setLat(Lat);
        setLong(Long);
        setDesc(Desc);
        setType(Type);
        setPhoto("");
    }

    public enreg_info(int id, double Lat, double Long , String Desc , String Type , String Photo){
        setId(id);
        setLat(Lat);
        setLong(Long);
        setDesc(Desc);
        setType(Type);
        setPhoto(Photo);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString(){
        return ("id : "+ this.getId() +   " Long : " + this.getLong() +  " Lat : " + this.getLat() + " Description : " + this.getDesc() + " Type : " + this.getType() + " Photo : " + this.getPhoto());
    }
}
