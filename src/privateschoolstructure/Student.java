/*
 *Educational Excercise. Free to distribute.
 */
package privateschoolstructure;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.1 (alpha)
 */
public final class Student implements Comparable<Student> {

    /*
    Instance Variables
     */
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private float tuitionFees;
    private static int instantiationCounter; //a static counter of objects potentially used as an id number also.
    private int id = 700000;

    //Constructors
    public Student(String firstName, String lastName, LocalDate dateOfBirth,
                   float tuitionFees) {
        instantiationCounter++;
        id += instantiationCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }

    @Override
    public String toString() {
        return "Student : " + lastName + " " + firstName + ", born on : "
               + dateOfBirth + ", fees agreed : " + tuitionFees + "\u20AC, [id=" + id + ']';
    }

    @Override
    public int compareTo(Student o) {
        int compareResult = this.lastName.compareTo(o.lastName);
        return compareResult == 0
               ? this.firstName.compareTo(o.firstName)
               : compareResult;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.lastName);
        hash = 89 * hash + Objects.hashCode(this.firstName);
        hash = 89 * hash + Objects.hashCode(this.dateOfBirth);
        hash = 89 * hash + Float.floatToIntBits(this.tuitionFees);
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (Float.floatToIntBits(this.tuitionFees) != Float.floatToIntBits(
                other.tuitionFees)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        return true;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public float getFees() {
        return tuitionFees;
    }

    public void setFees(float tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public static int getInstantiationCounter() {
        return instantiationCounter;
    }
}
