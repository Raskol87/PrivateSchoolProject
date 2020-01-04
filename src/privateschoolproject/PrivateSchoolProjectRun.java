/*
 *Educational Excercise. Free to distribute.

checklist, βαθμολογούμενα αιτήματα της άσκησης και "απαντήσεις"
-"Ask to input data"-όλο το μενού "1-creation" και τμήμα του "3-modification"
-"Synthetic data"-αρχική ερώτηση στην έναρξη (yes or no)
outputs
-"list of student" διαδρομή μενού 2->1
-"list of trainers" διαδρομή μενού 2->3
-"list of assignments" διαδρομή μενού 2->5
-"list of courses" διαδρομή μενού 2->2
-"students per course" διαδρομή μενού 2->4->1(αλλαγή course με 0)
-"trainers per course" διαδρομή μενού 2->4->3 (αλλαγή course με 0)
-"assignments per course" διαδρομή μενού 2->4->2 (αλλαγή course με 0)
-"assignments per student" διαδρομή μενού 2->4->4
-"list of student in multiple courses"διαδρομή μενού 2->6
-"students with due assignment on specific date" διαδρομή μενού 2->7
 */
package privateschoolproject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import privateschoolAPI.PrivateSchoolAPI;
import privateschoolAPI.PrivateSchoolUI;

/**
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.1 (alpha)
 */
public class PrivateSchoolProjectRun {

    /**
     * Στις μεταβλητές καταγράφονται hard-coded τα βασικά χαρακτηριστικά του Coding Bootcamp
     * τα οποία περνάνε στις κλάσεις. Το πρόγραμμα θα μπορούσε να δουλέψει με αλλαγμένα τα ορίσματα
     * και κάποιο intergration.
     * Δίνονται δύο χρονολογίες τέλους, μία για κάθε type (full και part time).Αν δωθούν παραπάνω types σε αριθμό 
     * το πρόγραμμα θα αστοχήσει.
     * 
     */
    public static void main(String[] args) {

        final String TITLE = "Coding Bootcamp 9";
        final String[] STREAMS = {"Java", "C#"};
        final String[] TYPES = {"full-time", "part-time"}; //uniform with "END_DATE". Maybe it was better to implement a Map (Type-End Date)
        final LocalDate START_DATE = LocalDate.of(2019, 11, 11);
        final LocalDate END_DATE_PARTTIME = LocalDate.of(2020, 05, 25);
        final LocalDate END_DATE_FULLTIME = LocalDate.of(2020, 02, 21);
        final int NUMBER_OF_TRAINERS = 8;
        final int NUMBER_OF_ASSIGNMENTS = 7;
        final int NUMBER_OF_STUDENTS = 20;
        final String[] SUBJECTS = {"Certifications", "Databases", "FrontEnd",
            "MVC", "OOP"};
        final float[] FEES_RANGE = {0f, 3000f};
        ArrayList<Object> initial_parameters
                = new ArrayList<>(Arrays.asList(TITLE,
                        STREAMS, TYPES, START_DATE, END_DATE_PARTTIME, END_DATE_FULLTIME,
                        NUMBER_OF_TRAINERS, NUMBER_OF_ASSIGNMENTS,
                        NUMBER_OF_STUDENTS, SUBJECTS, FEES_RANGE));

        PrivateSchoolAPI codingBootcamp = new PrivateSchoolAPI(initial_parameters); 
        PrivateSchoolUI CBMenus = new PrivateSchoolUI(codingBootcamp);
        CBMenus.runFirstTimeMenu();
    }
}
