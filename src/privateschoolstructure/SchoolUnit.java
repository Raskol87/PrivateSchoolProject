/*
 *Educational Excercise. Free to distribute.
 */
package privateschoolstructure;

import java.time.LocalDate;
import java.util.*;

/**
 * Scope of class is to: 1.Store data of Set of Students (enrolled in course),
 * List of Trainers, Map of "connecting" a List of Assignments to each Student.
 * 2. Build necessary Assignments
 *
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.1 (alpha)
 */
public class SchoolUnit extends Course {

    private List<Trainer> trainers;
    private Set<Object[]> prototypeAssignments;
    private List<Assignment> assignments = new ArrayList();
    private Map<Student, ArrayList<Assignment>> studentAssignments = new TreeMap();

    //Constructors
    public SchoolUnit(String title, String stream, String type,
                      LocalDate start_date, LocalDate end_date,
                      Set<Student> students,
                      List<Trainer> trainers, Set prototypeAssignments) {
        super(title, stream, type, start_date, end_date);
        this.trainers = new ArrayList(trainers);
        this.prototypeAssignments = new HashSet(prototypeAssignments);
        for (Student st : students) {
            this.studentAssignments.put(st, buildListOfAssignments(st));
        }
        System.out.println(super.toString());
    }

    public SchoolUnit(String title, String stream, String type,
                      LocalDate start_date, LocalDate end_date) {
        super(title, stream, type, start_date, end_date);
        this.trainers = new ArrayList();
        this.prototypeAssignments = new HashSet();
        System.out.println(super.toString());
    }

    //Builders
    private ArrayList<Assignment> buildListOfAssignments(Student st) {
        System.out.println("I start a buildAssign");
        ArrayList<Assignment> tempList = new ArrayList();
        System.out.println(this.prototypeAssignments.size());
        for (Object[] assignment : this.prototypeAssignments) {

            Assignment tempAssignment = new Assignment((String) assignment[0],
                                                       (String) assignment[1],
                                                       (LocalDate) assignment[2],
                                                       st);
            tempList.add(tempAssignment);
            this.assignments.add(tempAssignment);
            System.out.println("I put an assign " + tempAssignment);
            System.out.println("List is " + tempAssignment);
        }
        System.out.println("I finish");
        return tempList;
    }

    //Various Functional
    public void removeStudent(Student st) {
        studentAssignments.remove(st);
        System.out.println(st + " removed from " + this);
    }

    public void removeTrainer(Trainer tr) {
        trainers.remove(tr);
        System.out.println(tr + " removed from " + this);
    }

    public boolean isStudentWithDueThisWeek(Student st, LocalDate checkDate) {
        boolean checkStudent = false;
        LocalDate startWeek = findMinMaxForWeek(checkDate)[0];
        LocalDate endWeek = findMinMaxForWeek(checkDate)[1];
        if (studentAssignments.containsKey(st)) {
            if (!studentAssignments.get(st).isEmpty()) {
                for (Assignment Ass : studentAssignments.get(st)) {
                    if ((startWeek.compareTo(Ass.getDeadline()) <= 0)
                        && (endWeek.compareTo(Ass.getDeadline()) >= 0)) {
                        checkStudent = true;
                    }
                }
            } else {
                System.out.println("Assignments not set");
            }
        }
        return checkStudent;
    }

    protected LocalDate[] findMinMaxForWeek(LocalDate checkDate) {
        LocalDate[] startEndDate = new LocalDate[2];
        int weekDayIndex = checkDate.getDayOfWeek().getValue();
        startEndDate[0] = checkDate.minusDays(weekDayIndex - 1); //sets start date to start of current week
        startEndDate[1] = checkDate.plusDays(5 - weekDayIndex); //sets end date to friday(5th day) of current week
        return startEndDate;
    }

    //Getters and Setters
    public List<Student> getListOfCourseStudents() {

        return new ArrayList(studentAssignments.keySet());
    }

    public void setStudents(List<Student> students) {

        if (!this.studentAssignments.keySet().isEmpty()) {
            if (!this.prototypeAssignments.isEmpty()) {
                for (Student st : students) {
                    this.studentAssignments.put(st, buildListOfAssignments(st));
                }
            }
        } else {
            System.out.println("Potential loss of Data. Action NOT executed");
        }
    }

    public void setPrototypeAssignments(Set<Object[]> prototypeAssignments) {
        if (this.prototypeAssignments.isEmpty()) {
            this.prototypeAssignments = new HashSet(prototypeAssignments);
            if (!this.studentAssignments.keySet().isEmpty()) {
                for (Student student : this.studentAssignments.keySet()) {
                    studentAssignments.put(student, buildListOfAssignments(
                                           student));
                }
            }
        } else {
            System.out.println("Potential data loss. Action NOT executed");
        }
    }

    public void addNewStudent(Student st) {
        if (!studentAssignments.containsKey(st)) {
            if (!this.prototypeAssignments.isEmpty()) {
                this.studentAssignments.put(st, buildListOfAssignments(st));
            } else {
                this.studentAssignments.put(st, null);
            }
            System.out.println(st + " enrolled to " + this);
        } else {
            System.out.println("Student already enrolled");
        }
    }

    public Map<Student, ArrayList<Assignment>> getMapOfAssignments() {
        return studentAssignments;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setTrainers(List<Trainer> trainers) {
        if (this.trainers.isEmpty()) {
            this.trainers = new ArrayList(trainers);
        } else {
            System.out.println("List of trainers already initialized. "
                               + "\n Potential loss of data from bulk operation. Action NOT performed");
        }
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void addTrainer(Trainer tr) {
        if (this.trainers.contains(tr)) {
            System.out.println("Trainer already assigned");
        } else {
            this.trainers.add(tr);
            System.out.println(tr + " added to" + this);
        }
    }

    /**
     * Adds on Assignment to a specific Student. Do not copy to other students
     * or lists.
     *
     */
    public void addNewAssignment(Student st, String title, String description,
                                 LocalDate deadline) {
        Assignment ass;
        if (studentAssignments.containsKey(st)) {
            ass = new Assignment(title, description, deadline, st);
            studentAssignments.get(st).add(ass);
        } else {
            System.out.println("Student does not exist to course");
        }
    }

}
