package Model;

/** class for Contact Object
 *
 */
public class Contact {
    private int contactID;
    private String name;

    /** Contact constructor
     * @param contactID
     * @param name
     */
    public Contact(int contactID, String name){
        this.contactID = contactID;
        this.name = name;
    }

    /** contactID getter
     * @return contactID as int
     */
    public int getContactID() {
        return contactID;
    }

    /** contactID getter
     * @return name as String
     */
    public String getName() {
        return name;
    }

    /** name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** overrides print for name
     * @return name to string
     */
    @Override
    public String toString(){
        return name;
    }
}
