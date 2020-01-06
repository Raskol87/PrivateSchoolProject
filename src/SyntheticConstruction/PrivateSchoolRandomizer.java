/*
 *Educational Excercise. Free to distribute.
 */
package SyntheticConstruction;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import privateschoolAPI.PrivateSchoolAPI;
import privateschoolstructure.Assignment;
import privateschoolstructure.SchoolUnit;
import privateschoolstructure.Student;
import privateschoolstructure.Trainer;

/**
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.1 (alpha)
 */
public class PrivateSchoolRandomizer {

    /**
     * Class uses a "PrivateSchool" instance in order to create all its relevant
     * data. After class is "started" no further input is required
     */
    private PrivateSchoolAPI codingBootcamp;

    /**
     * String Arrays include data (names, titles etc) to be used randomly for
     * creation of "PrivateSchool" data
     */
    String[] maleFirst = {"Nick", "Michael", "Panagiotis", "George", "Kyriakos",
                          "Nikolaos", "Odysseas", "Panagiotis", "Kostas",
                          "George", "Dimitris ", "Iasonas ", "Christos",
                          "Vasilis", "Konstantinos", "Michalis", "Angelos",
                          "Nikos", "Ilias", "Stathis", "Dimitrios", "Harry"};

    String[] maleLast = {"Kyriakidis", "Despotidis", "Tasoylas", "Vournas",
                         "Balaskas", "Asimakopoulos", "Syrios", "Letsios",
                         "Chatzigeorgiou", "Baroutas", "Tzitzis", "Christou",
                         "Tzelis", "Douros", "Aleiferis", "Mageirias",
                         "Vardakis", "Loudianos", "Triantafyllou", "Tsekouras",
                         "Kladis", "Misoulis", "Papadopoulos", "Giazitzis",
                         "Xideros", "Petromichelakis", "Potter"};

    String[] femaleFirst = {"Irene", "Eleni", "Ilektra", "Nerina", "Aliki",
                            "Anastasia", "Elisavet", "Niki", "Kalliopi"};

    String[] femaleLast = {"Zaraloglou", "Nicolaides", "Spiliotaki", "Tsirozidi",
                           "Gavana", "Pyrovolikou", "Chalvatzi", "Tasoula",
                           "Rokka", "Koutsiouki"};

    String[] trainerLast = {"Irakleidis", "Karapas", "Zorbadakis", "Nicolaides",
                            "Pasparakis", "Yoda", "Dumbledore", "Splinter",
                            "ObiWanKenobi"};

    String[][] assignmentNames = {{"Private School", "Part A"},
                                  {"Private School", "Part B"},
                                  {"DataBase", "SQL exercise"},
                                  {"WebSite", "Part A"}, {"WebSite", "Part B"},
                                  {"Certification", "Foundation by PeopleCert"},
                                  {"Certification", "Advanced by PeopleCert"}};

    /**
     * Random generation part of class
     */
    Random r;

    /**
     * Constructor Class composites a "PrivateSchool" instance.
     */
    public PrivateSchoolRandomizer(PrivateSchoolAPI codingBootcamp) {
        this.codingBootcamp = codingBootcamp;
        this.r = new Random();
    }

    /**
     * Current method uses the random creation methods in correct order in order
     * to create data of one "Private School" instance.
     */
    public void createRandomPrivateSchool() {
        createRandomStudents();
        createRandomTrainers();
        createRandomSchoolUnits();
        gradAssignments();
        System.out.println(
                "Private School has been created with synthetic random data \n");
    }

    /**
     * Creates students using in random from an array of a predefined first and
     * last names. Also creates female students in percentage about 25%.
     */
    private void createRandomStudents() {
        int counter = 0;
        for (int i = 0; i < codingBootcamp.getNUMBER_OF_STUDENTS(); i++) {
            boolean isFemale = (r.nextBoolean() && r.nextBoolean()); //emulates the fact of more male than female enrolled. excepted result of 75% male.
            String firstName, lastName;
            LocalDate dateOfBirth;
            float fees;
            if (isFemale) {
                firstName = femaleFirst[r.nextInt(femaleFirst.length)];
                lastName = femaleLast[r.nextInt(femaleLast.length)];
            } else {
                firstName = maleFirst[r.nextInt(maleFirst.length)];
                lastName = maleLast[r.nextInt(maleLast.length)];
            }
            dateOfBirth = randomDate(LocalDate.now().minusYears(80), LocalDate.
                                     now().minusYears(16));
            fees = r.nextInt((int) codingBootcamp.getFEES_RANGE()[1]);
            this.codingBootcamp.getStudents().add(new Student(firstName,
                                                              lastName,
                                                              dateOfBirth, fees));
            counter++;
        }
        System.out.println(counter + " synthetic students have been created");
    }

    /**
     * Returns a random date in a specific period (up and down boundary) Through
     * recursion makes multiple runs, till to produce a date to satisfy criteria
     */
    private LocalDate randomDate(LocalDate minDate, LocalDate maxDate) {
        LocalDate tempDate;
        int minYear = minDate.getYear();
        int maxYear = maxDate.getYear();
        int deltaYear = maxYear - minYear;
        try {
            while (true) {
                tempDate = LocalDate.parse(
                        (r.nextInt(deltaYear + 1) + minYear) + "-" + r.nextInt(
                        13) + "-" + r.nextInt(32));
                if ((tempDate.compareTo(minDate) > 0) && (tempDate.compareTo(
                                                          maxDate) < 0)) {
                    return tempDate;
                }
            }
        } catch (DateTimeParseException dtpe) {

            tempDate = randomDate(minDate, maxDate); //Recursion!
        }
        return tempDate;
    }

    private void createRandomTrainers() {
        int counter = 0;
        for (int i = 0; i < codingBootcamp.getNUMBER_OF_TRAINERS(); i++) {
            String firstName = maleFirst[r.nextInt(maleFirst.length)];
            String lastName = trainerLast[r.nextInt(trainerLast.length)];
            String subjectName = codingBootcamp.getSUBJECTS()[Math.min(i,
                                                                       codingBootcamp.
                                                                               getSUBJECTS().length - 1)];

            codingBootcamp.getTrainers().add(new Trainer(firstName, lastName,
                                                         subjectName));
            counter++;
        }
        System.out.println(counter + " synthetic trainers have been created");
    }

    /**
     * Creates one set per type of private house (with maximum two) which
     * includes the necessary expected parameters for creation of all
     * assignments per student
     */
    private Set<Object[]> createRandomAssignments(String type) {
        Set<Object[]> resultAssignment = new HashSet();
        long tempPeriod = codingBootcamp.getEND_DATEperTYPE(type).until(
                codingBootcamp.getSTART_DATE(), ChronoUnit.DAYS);
        LocalDate tempDeadline = codingBootcamp.getEND_DATEperTYPE(type);
        for (int i = 0; i < codingBootcamp.getNUMBER_OF_ASSIGNMENTS(); i++) {
            int j = Math.min(i, assignmentNames.length - 1);
            Object[] temp = new Object[3];
            temp[0] = assignmentNames[j][0];
            temp[1] = assignmentNames[j][1];
            temp[2] = codingBootcamp.getEND_DATEperTYPE(type);
            resultAssignment.add(temp);
            tempDeadline = tempDeadline.plusDays(
                    tempPeriod / codingBootcamp.getNUMBER_OF_ASSIGNMENTS());
        }
        return resultAssignment;
    }

    private void createRandomSchoolUnits() {
        for (String stream : codingBootcamp.getSTREAMS()) {
            codingBootcamp.getTYPES().forEach(t -> codingBootcamp.
                    getSchoolUnits().add(new SchoolUnit(
                            codingBootcamp.getTITLE(), stream, t,
                            codingBootcamp.getSTART_DATE(),
                            codingBootcamp.getEND_DATEperTYPE(t),
                            chooseRandomTENStudents(),
                            codingBootcamp.getTrainers(),
                            createRandomAssignments(t))));
        }
    }

    /**
     * Randomly assigns 10 students at most in each School Unit.
     * There is no check of doubles
     */
    private Set<Student> chooseRandomTENStudents() {
        Set<Student> tempStudents = new TreeSet();

        for (int i = 0; i < 10; i++) {
            tempStudents.add(codingBootcamp.getStudents().get(r.nextInt(
                    codingBootcamp.getStudents().size())));
        }

        return tempStudents;
    }

    /**
     * Randomly assign marks and submission date in all assignments
     */
    private void gradAssignments() {

        for (SchoolUnit course : codingBootcamp.getSchoolUnits()) {
            LocalDate minDate = course.getStartDate();
            LocalDate maxDate = course.getEndDate().plusMonths(1);

            course.getMapOfAssignments().values().forEach(
                    (ArrayList<Assignment> assignment) -> { //den to pistevo oti "mallon" doulevei afto. an kai diskoleuomai na katallavo ta lambda....
                        assignment.forEach((assignment1) -> {
                            assignment1.setGrade(r.nextInt(101), randomDate(
                                                 minDate, maxDate));
                        });
                    });
        }
    }
}
