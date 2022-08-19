package Model;

/** class for Country Object
 *
 */
public class Country {
    private int id;
    private String name;

    /** Country constructor
     * @param id
     * @param name
     */
    public Country(int id, String name){
        this.id = id;
        this.name = name;

    }

    /** id getter
     * @return id as int
     */
    public int getId() {return id;}

    /** name getter
     * @return name as string
     */
    public String getName() {return name;}

    /** override for print name
     * @return name to string
     */
    @Override
    public String toString(){
        return (this.name);
    }
}
