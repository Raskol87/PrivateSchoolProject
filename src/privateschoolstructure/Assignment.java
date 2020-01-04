/*
 *Educational Excercise. Free to distribute.
 */
package privateschoolstructure;

import java.time.LocalDate;


/**
 *
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.1 (alpha)
 */
public class Assignment implements Comparable<Assignment> {
// 

    /* Instance Variables 
     */
    private String title;
    private String description;
    private LocalDate subDateTime;
    private LocalDate deadline;
    private int oralMark;
    private int totalMark;
    private Student student;
    private static int instantiationCounter; //a static counter of objects potentially used as an id number also.
    private int id;

    public Assignment(String title, String description, LocalDate deadline, Student student) {
        instantiationCounter++;
        this.id = instantiationCounter;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.student = student;
    }

    public Assignment(String title, String description, Student student) {
        instantiationCounter++;
        this.id = instantiationCounter;
        this.title = title;
        this.description = description;
        this.student = student;
    }

    public void setGrade(int oralMark, LocalDate subDate) {
        this.oralMark = oralMark;
        this.subDateTime = subDate;
        if (subDate.compareTo(this.deadline) > 0) {  //if assignment submitted after deadline 70% of the mark is reducted
            this.totalMark = (int) (this.oralMark / 3);
        } else {
            this.totalMark = this.oralMark;
        }
    }

    @Override
    public String toString() {
        return "Assignment : " + title + ", " + description + ", deadline: "
                + deadline + "\n submitted: " + subDateTime
                + ", grade (oral/final): " + oralMark + "% / "
                + totalMark + "%, responsible student: " + this.student.getLastName() + " " + this.student.getFirstName()
                + " [id=" + id + ']';
    }

    @Override
    public int compareTo(Assignment o) {
        return this.deadline.compareTo(o.deadline);
    }

    //Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubDateTime() {
        return subDateTime;
    }

    public void setSubDateTime(LocalDate subDateTime) {
        this.subDateTime = subDateTime;
    }

    public int getOralMark() {
        return oralMark;
    }

    public void setOralMark(int oralMark) {
        this.oralMark = oralMark;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setStudent(Student student) {
        if (this.student == null) {
            this.student = student;
        }
        System.out.println("Cannot reassign assignment to another student");
    }
}
