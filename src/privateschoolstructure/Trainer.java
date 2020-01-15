/*
 *Educational Excercise. Free to distribute.
 */
package privateschoolstructure;

/**
 *
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.1 (alpha)
 */
public class Trainer implements Comparable<Trainer> {

    //Instance Variables
    private String firstName;
    private String lastName;
    private String subject;
    private static int instantiationCounter; //a static counter of objects potentially used as an id number also.
    private int id = 8000;

    //Constructors
    public Trainer(String firstName, String lastName, String subject) {
        instantiationCounter++;
        this.id += instantiationCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Trainer : " + lastName + " " + firstName + ", for subject : " + subject;
    }

    @Override
    public int compareTo(Trainer o) {
        int compareResult = this.lastName.compareTo(o.lastName);
        return compareResult == 0
               ? this.firstName.compareTo(o.firstName)
               : compareResult;
    }

    //Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static int getInstantiationCounter() {
        return instantiationCounter;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
