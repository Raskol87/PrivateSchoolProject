/*
 *Educational Excercise. Free to distribute.
 */
package privateschoolproject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import privateschoolAPI.PrivateSchoolAPI;
import privateschoolAPI.PrivateSchoolUI;

/**
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.2 (alpha)
 */
public class PrivateSchoolProjectRun {

    /**
     * Version 0.2 implements multiple options (end dates) per type,
     * also in version 0.2 some functionality and readability of menus (UI)
     * enhanced
     */
    public static void main(String[] args) {

        final String TITLE = "Coding Bootcamp 9";
        final String[] STREAMS = {"Java", "C#"};
        final Map<String, LocalDate> TYPES;
        {
            Map<String, LocalDate> aMap = new HashMap<>();
            aMap.put("full-time", LocalDate.of(2020, 2, 21));
            aMap.put("part-time", LocalDate.of(2020, 5, 25));
            aMap.put("elearning", LocalDate.of(2020, 8, 25));
            TYPES = Collections.unmodifiableMap(aMap);
        }
        final LocalDate START_DATE = LocalDate.of(2019, 11, 11);
        final int NUMBER_OF_TRAINERS = 8;
        final int NUMBER_OF_ASSIGNMENTS = 7;
        final int NUMBER_OF_STUDENTS = 20;
        final String[] SUBJECTS = {"Certifications", "Databases", "FrontEnd",
                                   "MVC", "OOP"};
        final float[] FEES_RANGE = {0f, 3000f};
        ArrayList<Object> initial_parameters
                = new ArrayList<>(Arrays.asList(TITLE,
                                                STREAMS, TYPES, START_DATE,
                                                NUMBER_OF_TRAINERS,
                                                NUMBER_OF_ASSIGNMENTS,
                                                NUMBER_OF_STUDENTS,
                                                SUBJECTS, FEES_RANGE));

        PrivateSchoolAPI codingBootcamp = new PrivateSchoolAPI(
                initial_parameters);
        PrivateSchoolUI CBMenus = new PrivateSchoolUI(codingBootcamp);
        CBMenus.runFirstTimeMenu();
    }
}
