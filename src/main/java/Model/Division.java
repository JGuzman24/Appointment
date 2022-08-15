package Model;

public class Division {
    private int id;
    private String name;
    private int countryID;
    private String country;

    public Division(int id, String name, int countryID, String country){
        this.id = id;
        this.name = name;
        this.countryID = countryID;
        this.country = country;

    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getCountryID(){return countryID;}
    public String getCountry() {return country;}

    @Override
    public String toString(){
        return (this.name);
    }
}
