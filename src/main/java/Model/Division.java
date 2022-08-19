package Model;

/** class for Division Object
 *
 */
public class Division {
    private int id;
    private String name;
    private int countryID;
    private String country;

    /** Division constructor
     * @param id
     * @param name
     * @param countryID
     * @param country
     */
    public Division(int id, String name, int countryID, String country){
        this.id = id;
        this.name = name;
        this.countryID = countryID;
        this.country = country;

    }

    /** id getter
     * @return id as int
     */
    public int getId() {return id;}

    /** name getter
     * @return name as String
     */
    public String getName() {return name;}

    /** countryID getter
     * @return countryID as int
     */
    public int getCountryID(){return countryID;}

    /** country getter
     * @return country as String
     */
    public String getCountry() {return country;}

    /** Override for name toString
     * @return name as string
     */
    @Override
    public String toString(){
        return (this.name);
    }
}
