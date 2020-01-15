/*
 *Educational Excercise. Free to distribute.
 */
package privateschoolAPI;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import privateschoolstructure.Assignment;
import privateschoolstructure.SchoolUnit;
import privateschoolstructure.Student;
import privateschoolstructure.Trainer;

/**
 * Scope of class is: 1.to handle input operations and validation from the
 * student 2.function as builder for PrivateSchoolCohort extends Course, Student
 * and Trainer objects 3.store data [List of Students, List of Trainers, List of
 * Courses(PrivateSchoolCohort), Assignment data]
 * In version 0.2 improved functionality and readability of assignment data
 *
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.2 (alpha)
 */
public class PrivateSchoolAPI {

    private final String TITLE;
    private final String[] STREAMS;
    private final Map<String, LocalDate> TYPE_DATE; //Mapping of end dates per type
    private final LocalDate START_DATE;
    private final int NUMBER_OF_TRAINERS;
    private final int NUMBER_OF_ASSIGNMENTS;
    private final int NUMBER_OF_STUDENTS;
    private final String[] SUBJECTS;
    private final float[] FEES_RANGE;
    protected final Scanner sc;
    private List<Student> students;
    private List<Trainer> trainers;
    private List<SchoolUnit> schoolUnits;
    private Map<String, Set<Object[]>> prototypeAssignments; //Mapping of assignment data per type

    //Constructor
    public PrivateSchoolAPI(ArrayList<Object> parameters) {

        this.sc = new Scanner(System.in).useDelimiter("\\n");
        this.TITLE = (String) parameters.get(0);
        this.STREAMS = (String[]) parameters.get(1);
        this.TYPE_DATE = (Map) parameters.get(2);
        this.START_DATE = (LocalDate) parameters.get(3);
        this.NUMBER_OF_TRAINERS = (int) parameters.get(4);
        this.NUMBER_OF_ASSIGNMENTS = (int) parameters.get(5);
        this.NUMBER_OF_STUDENTS = (int) parameters.get(6);
        this.SUBJECTS = (String[]) parameters.get(7);
        this.FEES_RANGE = (float[]) parameters.get(8);

        this.prototypeAssignments = new HashMap();
        TYPE_DATE.keySet().stream().forEach((t) -> this.prototypeAssignments.
                put(t, new HashSet<>()));
        this.schoolUnits = new ArrayList();
        this.trainers = new ArrayList();
        this.students = new ArrayList();

    }

    //Builders
    /**
     * partly builds a PrivateSchoolCohort object from hard coded data and user
     * choices
     */
    public SchoolUnit buildSchoolUnit() {
        System.out.println("Please provide preferred Stream");
        String stream = chooseOption(STREAMS);
        System.out.println("Please provide preferred Type");
        String type = (String) chooseOption(TYPE_DATE.keySet().toArray());
        LocalDate endDate = TYPE_DATE.get(type);
        SchoolUnit aSchoolUnit = new SchoolUnit(TITLE, stream, type, START_DATE,
                                                endDate);
        schoolUnits.add(aSchoolUnit);
        return aSchoolUnit;
    }

    /**
     * builds a Student object with validated user data
     */
    public void buildStudent() {
        String firstName = insertValidatedString("First Name of Student");
        String lastName = insertValidatedString("Last Name of Student");
        LocalDate dateOfBirth = insertValidatedDate("Date of Birth of Student ",
                                                    LocalDate.now().minusYears(
                                                            120), LocalDate.
                                                            now().minusYears(16));
        float fees = insertValidatedNum("Tuition Fees of Student", FEES_RANGE[0],
                                        FEES_RANGE[1]);

        Student temp = new Student(firstName, lastName, dateOfBirth, fees);
        this.students.add(temp);
        System.out.println(temp + " succesfully added");
    }

    /**
     * builds a Student object with validated user data and assigns it to a
     * PrivateSchoolCohort
     */
    public void buildStudent(SchoolUnit course) {
        String firstName = insertValidatedString("First Name of Student");
        String lastName = insertValidatedString("Last Name of Student");
        LocalDate dateOfBirth = insertValidatedDate("Date of Birth of Student ",
                                                    LocalDate.now().minusYears(
                                                            120), LocalDate.
                                                            now().minusYears(16));
        float fees = insertValidatedNum("Tuition Fees of Student", FEES_RANGE[0],
                                        FEES_RANGE[1]);
        Student temp = new Student(firstName, lastName, dateOfBirth, fees);
        this.students.add(temp);
        course.addNewStudent(this.students.get(this.students.size() - 1));
        System.out.println(temp + " succesfully added");
    }

    /**
     * builds a Trainer object with validated user data
     */
    public Trainer buildTrainer() {
        String firstName = insertValidatedString("First Name of Trainer");
        String lastName = insertValidatedString("Last Name of Trainer");
        String subject = insertValidatedString(
                "Subject that the trainer teaches");
        Trainer temp = new Trainer(firstName, lastName, subject);
        System.out.println(temp + " succesfully added");
        this.trainers.add(temp);
        return temp;
    }

    /**
     * stores from validated user data, necessary data to partly construct
     * assignment objects
     */
    public void insertAssignmentInfo() {

        Object[] aTemp = new Object[(3)];
        System.out.println("Please select type you want to enter data");
        String type = (String) chooseOption(TYPE_DATE.keySet().toArray());

        System.out.printf(
                "Please provide Subject, Description and Deadlines\n for "
                + type + " of Courses "
                + "Assignments \nProposed number for Assignments is %d\n",
                NUMBER_OF_ASSIGNMENTS);

        aTemp[0] = insertValidatedString("Subject ");
        aTemp[1] = insertValidatedString("Short Description ", ".");

        aTemp[2] = insertValidatedDate("Deadline for " + type + " ",
                                       START_DATE, TYPE_DATE.get(type).
                                               plusMonths(
                                                       1));
        System.out.println(Arrays.toString(aTemp));
        addPrototypeAssignment(aTemp, type);
    }

    /**
     * Adds an assignment info to Private School
     *
     */
    protected void addPrototypeAssignment(Object[] prototype, String type) {
        if (TYPE_DATE.containsKey(type)) {
            prototypeAssignments.get(type).add(prototype);
            if (!schoolUnits.isEmpty()) {
                schoolUnits.forEach(course -> {
                    if (course.getType().equals(type))
                        course.addPrototypeAssignment(
                                prototype);
                });
            }
        }
    }

    protected void removePrototypeAssignment(Object[] prototype, String type) {
        if (TYPE_DATE.containsKey(type)) {
            if (prototypeAssignments.get(type).remove(prototype)) {
                if (!schoolUnits.isEmpty()) {
                    schoolUnits.forEach(course -> {
                        if (course.getType().equals(type))
                            course.removePrototypeAssignment(prototype);
                    });
                };
            }
        }
    }

//Various Functional
    /**
     * Overloaded, iterates PrivateSchoolCohorts stored and obtains the values
     * existing to at least two (substract) of the student collections
     */
    public List<Student> findMultipleEnrolledStudents() {
        Set<Student> result = new HashSet();
        for (int i = 0; i < schoolUnits.size(); i++) {
            for (int j = (i + 1); j < schoolUnits.size(); j++) {
                result.addAll(findMultipleEnrolledStudents(schoolUnits.get(i),
                                                           schoolUnits.get(j)));
            }
        }
        return new ArrayList(result);
    }

    /**
     * Checks two PrivateSchoolUnits and obtains the values existing in both
     * collections (substract) of the student collections
     */
    protected List<Student> findMultipleEnrolledStudents(
            SchoolUnit courseA, SchoolUnit courseB) {
        List<Student> temp = new ArrayList(courseA.getStudents());
        temp.retainAll(courseB.getStudents());
        return temp;
    }

    /**
     * Removes a Student from all lists (PrivateSchoolAPI and
     * PrivateSchoolCohort)
     */
    public void removeStudent(Student st) {
        schoolUnits.forEach(course -> course.removeStudent(st));
        students.remove(st);
        System.out.println(st + " removed");
    }

    /**
     * Removes a Trainer from all lists (PrivateSchoolAPI and
     * PrivateSchoolCohort)
     */
    public void removeTrainer(Trainer tr) {
        schoolUnits.forEach(course -> course.removeTrainer(tr));
        trainers.remove(tr);
        System.out.println(tr + " removed");
    }

    public void gradeStudent(Student st, SchoolUnit course, Assignment ass) {
        int mark;
        LocalDate submission;
        if (ass.getSubDateTime() != null) {
            System.out.println("Assignment has already been marked");
        } else {
            System.out.println(
                    "Grade will be assigned to " + ass + "\n of " + st + "\n enrolled to " + course);
            System.out.println("Please provide mark");
            mark = insertValidatedNum("Mark ", 0, 100);
            System.out.println("Please provide submission date");
            submission = insertValidatedDate("Date of submission ", course.
                                             getStartDate(),
                                             course.getEndDate().plusMonths(6));

            int tempIndex = course.getMapOfAssignments().get(st).indexOf(ass);
            course.getMapOfAssignments().get(st).get(tempIndex).setGrade(mark,
                                                                         submission);
        }
    }

    protected String insertValidatedString(String promptString) {

        System.out.print("Please provide " + promptString
                         + "\n[at least 3 characters and 20 characters at most]\n");
        while (!sc.hasNext("[\\w&&[\\D]]{3,20}")) {
            System.out.print("Invalid Input \nPlease provide valid String as "
                             + promptString
                             + "\n[at least 3 characters and 20 characters at most]\n");
            sc.next();
        }
        return sc.next("[\\w&&[\\D]]{3,20}");

    }

    protected String insertValidatedString(String promptString, String regex) {

        System.out.print("Please provide " + promptString
                         + "\n[at least 3 characters]\n");
        while (!sc.hasNext(regex + "{3,}")) {
            System.out.print("Invalid Input \nPlease provide valid String as "
                             + promptString + "\n[t least 3 characters]\n");
            sc.next();
        }
        return sc.next(regex + "{3,}");

    }

    protected LocalDate insertValidatedDate(String promptString,
                                            LocalDate minDate, LocalDate maxDate) {
        LocalDate tempDate;
        System.out.print("Please provide "
                         + promptString + " in format [YYYY-MM-DD]\n[Dates between "
                         + minDate.toString() + " and " + maxDate.toString() + " are accepted]\n");

        try {
            while (true) {
                tempDate = LocalDate.parse(sc.next());
                if ((tempDate.compareTo(minDate) > 0) && (tempDate.compareTo(
                                                          maxDate) < 0)) {
                    return tempDate;
                }
                System.out.print("Invalid Input\nPlease provide "
                                 + promptString + " in format [YYYY-MM-DD]\n[Dates between "
                                 + minDate.toString() + " and " + maxDate.
                        toString() + " are accepted]\n");
            }
        } catch (DateTimeParseException dtpe) {
            System.out.println("Invalid Input");
            tempDate = insertValidatedDate(promptString, minDate, maxDate);
        }
        return tempDate;

    }

    protected float insertValidatedNum(String promptString, float min, float max) {
        float temp;
        do {
            System.out.printf(Locale.ITALY, "Please provide " + promptString
                                            + "\n[a number with floating point from %,.2f\u20AC to "
                                            + "%,.2f\u20AC is expected]\n", min,
                              max);
            while (!sc.hasNextFloat()) {
                System.out.printf(Locale.ITALY,
                                  "Invalid Input \nPlease provide "
                                  + promptString
                                  + "\n[a number with floating point from %,.2f\u20AC to "
                                  + "%,.2f\u20AC is expected]\n", min, max);
                sc.next();
            }
            temp = sc.nextFloat();
        } while ((temp < min) || (temp > max));
        return temp;
    }

    protected int insertValidatedNum(String promptString, int min, int max) {
        int temp;
        do {
            System.out.printf("Please provide " + promptString
                              + "\n[an integer number from %d to %d is expected]\n",
                              min, max);
            while (!sc.hasNextInt()) {
                System.out.printf(
                        "Invalid Input \nPlease provide "
                        + promptString
                        + "\n[a integer number from %d to %d is expected]\n",
                        min, max);
                sc.next();
            }
            temp = sc.nextInt();
        } while ((temp < min) || (temp > max));
        return temp;
    }

    protected <T> T chooseOption(T[] array) {
        System.out.println(
                "Please select from following list by typing the respective value");
        printOptionMenuList(array);

        return array[(insertValidatedNum("choice number ", 1, array.length) - 1)];

    }

    protected void printOptionMenuList(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println((i + 1) + " for : " + array[i]);
        }
    }

    /**
     * Using validated user input (Date) and returns the students that have an
     * assignment with a deadline the same working week as date
     *
     */
    public ArrayList studentsWithDueThisWeek() {
        LocalDate checkDate;
        ArrayList<Student> studentsWithDueAssignment = new ArrayList();
        if (!students.isEmpty()) {
            if (!schoolUnits.isEmpty()) {
                checkDate = insertValidatedDate("Date to check ", START_DATE,
                                                TYPE_DATE.values().stream().max(
                                                        (t1, t2) -> t1.
                                                                compareTo(t2)).
                                                        get().plusMonths(1));

                System.out.println(
                        "Date you requested is " + checkDate + " and it is " + checkDate.
                                getDayOfWeek());
                for (Student st : students) {
                    for (SchoolUnit course : schoolUnits) {
                        if (course.isStudentWithDueThisWeek(st, checkDate)) {
                            studentsWithDueAssignment.add(st);
                            break;
                        }
                    }
                }
            } else {
                System.out.println("No courses");
            }
        } else {
            System.out.println("No students stored");
        }
        return studentsWithDueAssignment;
    }

    public void addAssignmentToStudent(Student st, SchoolUnit course) {
        String tempTitle, tempDescription;
        LocalDate tempDeadline;

        tempTitle = insertValidatedString("Subject ");
        tempDescription = insertValidatedString("Short Description ", ".");
        tempDeadline = insertValidatedDate("Deadline ", course.getStartDate(),
                                           course.getEndDate().plusMonths(1));
        course.addNewAssignment(st, tempTitle, tempDescription, tempDeadline);

    }

    //Getters and Setters
    public String getTITLE() {
        return TITLE;
    }

    public String[] getSTREAMS() {
        return STREAMS;
    }

    public Set<String> getTYPES() {
        return TYPE_DATE.keySet();
    }

    public LocalDate getSTART_DATE() {
        return START_DATE;
    }

    public LocalDate getEND_DATEperTYPE(String type) {
        return this.TYPE_DATE.get(type);
    }

    public int getNUMBER_OF_TRAINERS() {
        return NUMBER_OF_TRAINERS;
    }

    public int getNUMBER_OF_ASSIGNMENTS() {
        return NUMBER_OF_ASSIGNMENTS;
    }

    public int getNUMBER_OF_STUDENTS() {
        return NUMBER_OF_STUDENTS;
    }

    public String[] getSUBJECTS() {
        return SUBJECTS;
    }

    public float[] getFEES_RANGE() {
        return FEES_RANGE;
    }

    public List<SchoolUnit> getSchoolUnits() {
        return schoolUnits;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public Map<String, Set<Object[]>> getMapPrototypeAssignmentsType() {
        return prototypeAssignments;
    }
}
