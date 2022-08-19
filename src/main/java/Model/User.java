package Model;

/** class for User Object
 *
 */
public class User {
    private int id;
    private String name;
    private String password;

    /** User constructor
     * @param id
     * @param name
     * @param password
     */
    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /** id getter
     * @return id as int
     */
    public int getId() {return id;}

    /** name getter
     * @return name as string
     */
    public String getName() {return name;}

    /** password getter
     * @return password as string
     */
    public String getPassword() {return password;}

}
