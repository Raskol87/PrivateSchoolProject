/*
 *Educational Excercise. Free to distribute.
 */
package privateschoolAPI;

import SyntheticConstruction.PrivateSchoolRandomizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import privateschoolstructure.Assignment;
import privateschoolstructure.SchoolUnit;
import privateschoolstructure.Student;
import privateschoolstructure.Trainer;

/**
 * Scope of Class is to "print" a "user inteface" on the console, in order to
 * guide user to provide necessary input and manipulate an Object of
 * PrivateSchool. Selection of objects, validation of options and printing of
 * results should be done through this class
 *
 * Aim of the Class is to provide to user multiple and reduntant "ways" to
 * proceed to options
 *
 * @since 01/01/2020
 * @author kkyriakidis
 * @version 0.1 (alpha)
 */
public class PrivateSchoolUI {

    private final Scanner sc = new Scanner(System.in).useDelimiter("\\n");
    private PrivateSchoolAPI PrivSchool;
    private boolean isExit = false;

    //Constructor
    public PrivateSchoolUI(PrivateSchoolAPI PrivSchool) {
        this.PrivSchool = PrivSchool;
    }

    //Menus
    public void runFirstTimeMenu() {
        greet();
        printFirstTimeMenu();
        if (isYesOrNo(
                "Do you want to create synthetic data for private school? [Y/N]")) {
            PrivateSchoolRandomizer PrivSchRand = new PrivateSchoolRandomizer(
                    PrivSchool);
            PrivSchRand.createRandomPrivateSchool();
        }
        runMainMenu();
    }

    //1st level menu-Main Menu
    private void runMainMenu() {
        while (!this.isExit) {
            greet();
            printMainMenu();
            int switchOption = chooseOption("[1 2 3]{1}|exit");
            switch (switchOption) {
                case 1:
                    runCreationMenu();
                    break;
                case 2:
                    runInspectionMenu();
                    break;
                case 3:
                    if (PrivSchool.getSchoolUnits().isEmpty())
                        System.out.println(
                                "No courses created. Create first school elements to proceed to modifications");  //check of list initialization
                    else {
                        runModificationMenu();
                    }
                    break;
                case -100:
                    this.isExit = true;
                    return;
                default:
                    System.out.println("Unknown Error");
                    break;

            }
        }
    }

    //2nd level menu
    private void runCreationMenu() {
        while (!this.isExit) {
            printCreationMenu();
            int switchOption = chooseOption("[1 2 3 4]{1}|exit|back");
            switch (switchOption) {
                case 1: //insert new student
                    do {
                        PrivSchool.buildStudent();
                        if (!PrivSchool.getSchoolUnits().isEmpty()) {    //check of list initialization
                            while (isYesOrNo(
                                    "Do you want also to enroll the student in an existing course? [y/n]")) {
                                SchoolUnit tempObj // user choice
                                        = (SchoolUnit) chooseFromList(
                                                (List<SchoolUnit>) PrivSchool.
                                                        getSchoolUnits());
                                tempObj.addNewStudent( //gets the last object added to list, which is the object created in the same loop
                                        PrivSchool.getStudents().get(
                                                PrivSchool.getStudents().size() - 1));
                            }
                        }
                    } while (isYesOrNo(
                            "Do you want to add additional student? [Y/N]"));
                    break;
                case 2: //insert new course
                    do {
                        PrivSchool.buildSchoolUnit();
                        if (!PrivSchool.getTrainers().isEmpty()) {
                            if (isYesOrNo(
                                    "Do you want to add already stored list of trainers to current course? [y/n]")) {
                                PrivSchool.getSchoolUnits().get(
                                        (PrivSchool.getSchoolUnits().size() - 1)) //gets the last object added to list, which is the object created in the same loop
                                        .
                                        setTrainers(PrivSchool.getTrainers());
                            }
                        }
                        if (!PrivSchool.getStudents().isEmpty()) {
                            if (isYesOrNo(
                                    "Do you want to enroll already stored list of students to current course? [y/n]")) {
                                PrivSchool.getSchoolUnits().get(
                                        (PrivSchool.getSchoolUnits().size() - 1)) //gets the last object added to list, which is the object created in the same loop
                                        .
                                        setStudents(PrivSchool.getStudents());
                            }
                        }
                        SchoolUnit tempCourse = PrivSchool.getSchoolUnits().get(
                                PrivSchool.getSchoolUnits().size() - 1); //gets the last object added to list, which is the object created in the same loop
                        if (isYesOrNo("Do you want to submit to course "
                                      + "assignment info -and create assignments- "
                                      + "that are stored (if created)?[y/n]")) {
//                            if (tempCourse.getType().equals(PrivSchool.
//                                    getTYPES()[0])) {
//                                tempCourse.setPrototypeAssignments(PrivSchool.
//                                        getPrototypeAssignments_FullTime());
//                            } else {
//                                tempCourse.setPrototypeAssignments(PrivSchool.
//                                        getPrototypeAssignments_PartTime());
//                            }
                        }

                    } while (isYesOrNo(
                            "Do you want to add additional course? [Y/N]"));
                    break;
                case 3: //insert new trainer
                    do {
                        PrivSchool.buildTrainer();
                    } while (isYesOrNo(
                            "Do you want to add additional trainer? [Y/N]"));
                    if (!PrivSchool.getSchoolUnits().isEmpty()) {
                        if (isYesOrNo(
                                "Do you want to assign created trainers to existing courses? [y/n]"
                                + "\nThis will assign ALL stored trainers to ALL stored courses.")) {
                            for (SchoolUnit course : PrivSchool.getSchoolUnits()) {
                                System.out.println(
                                        "Trainers assigned to " + course);
                                course.setTrainers(PrivSchool.getTrainers());
                            }
                        }
                    }
                    break;
                case 4: //insert new assignments
                    do {
                        System.out.println("In this menu you enter assignment "
                                           + "information \nwhich are the same for each type of "
                                           + "course and two options of deadline for each type "
                                           + "of course\nIt is strongly recommended to input "
                                           + "all assignment information now");
                        PrivSchool.insertAssignmentInfo();
                    } while (isYesOrNo(
                            "Do you want to add additional assignment info?[y/n]"));
                    if (isYesOrNo(
                            "Do you want to insert list of assignment info to stored courses?[y/n]"
                            + "\nThis will create assignments to all stored courses currently.")) {
                        for (SchoolUnit course : PrivSchool.getSchoolUnits()) {
//                            if (course.getType().
//                                    equals(PrivSchool.getTYPES()[0])) {
//                                course.setPrototypeAssignments(PrivSchool.
//                                        getPrototypeAssignments_FullTime());
//                            } else {
//                                course.setPrototypeAssignments(PrivSchool.
//                                        getPrototypeAssignments_PartTime());
//                            }
                        }
                    }
                    break;
                case -1:
                case -2:
                    runMainMenu();
                    break;
                case -100:
                    this.isExit = true;
                    return;
                default:
                    System.out.println("Unknown Error");
                    break;
            }
        }
    }

    //2nd level menu
    private void runInspectionMenu() {
        while (!this.isExit) {
            printInspectionMenu();
            int switchOption = chooseOption("[1 2 3 4 5 6 7]{1}|exit|back");
            ArrayList temp;
            switch (switchOption) {
                case 1: //view students
                    if (!PrivSchool.getStudents().isEmpty()) {
                        temp = new ArrayList(PrivSchool.getStudents());
                        Collections.sort(temp); ///sorted copy
                        printList(temp);
                    } else {
                        System.out.println("No students found");
                    }
                    break;
                case 2: //view courses
                    if (!PrivSchool.getSchoolUnits().isEmpty()) {
                        printList(PrivSchool.getSchoolUnits());
                    } else {
                        System.out.println("No courses found");
                    }
                    break;
                case 3: //view trainers
                    if (!PrivSchool.getTrainers().isEmpty()) {
                        temp = new ArrayList(PrivSchool.getTrainers());
                        Collections.sort(temp);
                        printList(temp);
                    } else {
                        System.out.println("No trainers found");
                    }
                    break;
                case 4: //course view sub-menu
                    if (!PrivSchool.getSchoolUnits().isEmpty()) {
                        runCourseMenu();
                    } else {
                        System.out.println("No courses created. Cannot proceed");
                    }
                    break;
                case 5: //view all assignments
                    ArrayList tempAssignmentList = new ArrayList();
                    for (SchoolUnit course : PrivSchool.getSchoolUnits()) {
                        tempAssignmentList.addAll(course.getAssignments());
                    }
                    if (!tempAssignmentList.isEmpty()) {
                        Collections.sort(tempAssignmentList); //copy sorted
                        printList(tempAssignmentList);
                    } else {
                        System.out.println("No assignments found");
                    }
                    break;
                case 6://view students enrolled in two or more courses
                    if (!PrivSchool.getStudents().isEmpty()) {
                        if (!PrivSchool.findMultipleEnrolledStudents().isEmpty()) {
                            printList(PrivSchool.findMultipleEnrolledStudents());
                        } else {
                            System.out.println(
                                    "No students enrolled in multi courses");
                        }
                    } else {
                        System.out.println("No students found");
                    }
                    break;
                case 7: //students with due assignment
                    System.out.println(
                            "Please provide date in order to check during "
                            + "this date's working week which student have assignment in due");
                    ArrayList tempStudents = PrivSchool.
                            studentsWithDueThisWeek();
                    if (!tempStudents.isEmpty()) {
                        Collections.sort(tempStudents); //copy sorted-shallow
                        System.out.println(
                                "Students with assignment in due this week are:");
                        printList(tempStudents);
                    } else {
                        System.out.println(
                                "No students with pending assignments during the particular week");
                    }
                    break;
                case -1:
                case -2:
                    runMainMenu();
                    break;
                case -100:
                    this.isExit = true;
                    return;
                default:
                    System.out.println("Unknown Error");
                    break;
            }
        }
    }

    //3rd level menu
    private void runCourseMenu() {
        System.out.println("Please select relevant course to view");
        SchoolUnit inspectedTempCourse = (SchoolUnit) chooseFromList(PrivSchool.
                getSchoolUnits());
        while (!this.isExit) {
            System.out.println("You view elements of " + inspectedTempCourse);
            printCourseMenu();
            int switchOption = chooseOption("[0 1 2 3 4]{1}|exit|back|main");
            ArrayList tempList;
            switch (switchOption) {
                case 0:
                    runCourseMenu();
                    break;
                case 1: //view Students of course
                    if (!inspectedTempCourse.getListOfCourseStudents().isEmpty()) {
                        tempList = new ArrayList(inspectedTempCourse.
                                getListOfCourseStudents());
                        Collections.sort(tempList);//sorted copy
                        printList(tempList);
                    } else {
                        System.out.println("No students");
                    }
                    break;
                case 2: //view assignments of course
                    if (!inspectedTempCourse.getAssignments().isEmpty()) {
                        tempList = new ArrayList(inspectedTempCourse.
                                getAssignments());
                        Collections.sort(tempList);///sorted copy
                        printList(inspectedTempCourse.getAssignments());
                    } else {
                        System.out.println("No assignments");
                    }
                    break;
                case 3: //view trainers of course
                    if (!inspectedTempCourse.getTrainers().isEmpty()) {
                        tempList = new ArrayList(inspectedTempCourse.
                                getTrainers());
                        Collections.sort(tempList);///sorted copy
                        printList(tempList);
                    } else {
                        System.out.println("No trainers");
                    }
                    break;
                case 4: //view assignments of student
                    if (!inspectedTempCourse.getListOfCourseStudents().isEmpty()) {
                        do {
                            Student inspectedTempStudent = (Student) chooseFromList(
                                    inspectedTempCourse.
                                            getListOfCourseStudents());
                            System.out.println(
                                    "You view assignments of " + inspectedTempStudent);
                            if (!inspectedTempCourse.getMapOfAssignments().get(
                                    inspectedTempStudent).isEmpty()) {
                                tempList = new ArrayList(inspectedTempCourse.
                                        getMapOfAssignments().get(
                                                inspectedTempStudent));
                                Collections.sort(tempList);
                                printList(tempList);
                            } else {
                                System.out.println("No assignments");
                            }
                        } while (isYesOrNo(
                                "Do you want to view another student's assignments? [y/n]"));
                    } else {
                        System.out.println("No students");
                    }
                    break;
                case -1:
                    runInspectionMenu();
                    break;
                case -2:
                    runMainMenu();
                    break;
                case -100:
                    this.isExit = true;
                    return;
                default:
                    System.out.println("Unknown Error");
                    break;
            }
        }
    }

    //2nd level menu
    private void runModificationMenu() {
        while (!this.isExit) {
            printModificationMenu();
            int switchOption = chooseOption("[0 1 2 3 4]{1}|exit|back");
            ArrayList temp;
            switch (switchOption) {
                case 0: //delete a course
                    System.out.println(
                            "Please select course to delete\nExisting Courses are:");
                    SchoolUnit coursetoBeRemoved = (SchoolUnit) chooseFromList(
                            PrivSchool.getSchoolUnits());
                    if (isYesOrNo(
                            "Removing Course will completely remove all relevant associated data.\nDo you want to proceed? [y/n]")) {
                        PrivSchool.getSchoolUnits().remove(coursetoBeRemoved);
                        System.out.println(coursetoBeRemoved + " removed");
                    }
                    if (PrivSchool.getSchoolUnits().isEmpty()) {
                        runMainMenu();
                    }
                    break;
                case 1: //Enroll student to course
                    if (!PrivSchool.getStudents().isEmpty()) {
                        do {
                            System.out.println(
                                    "Please select student to enroll to course \nExisting Students are:");
                            temp = new ArrayList(PrivSchool.getStudents());
                            Collections.sort(temp);//sorted shallow
                            Student toBeMoved = (Student) chooseFromList(temp);
                            System.out.println(
                                    "Please select course to enroll to");
                            SchoolUnit course = (SchoolUnit) chooseFromList(
                                    PrivSchool.getSchoolUnits());
                            course.addNewStudent(toBeMoved);
                        } while (isYesOrNo(
                                "Do you want to enroll another student?[y/n]"));
                    } else {
                        System.out.println("No students stored");
                    }
                    break;

                case 2: //completely removing a student from bootcamp
                    if (!PrivSchool.getStudents().isEmpty()) {
                        do {
                            System.out.println("Please select student");
                            temp = new ArrayList(PrivSchool.getStudents());
                            Collections.sort(temp);//sorted shallow
                            Student studentToBeRemoved = (Student) chooseFromList(
                                    temp);
                            PrivSchool.removeStudent(studentToBeRemoved);
                        } while (isYesOrNo(
                                "Do you want to delete another student?[y/n]"));
                    } else {
                        System.out.println("No students stored");
                    }
                    break;

                case 3: //completely delete trainer
                    if (!PrivSchool.getTrainers().isEmpty()) {
                        do {
                            System.out.println("Please select trainer");
                            temp = new ArrayList(PrivSchool.getTrainers());
                            Collections.sort(temp);//sorted shallow
                            Trainer trainertToBeRemoved = (Trainer) chooseFromList(
                                    temp);
                            PrivSchool.removeTrainer(trainertToBeRemoved);
                        } while (isYesOrNo(
                                "Do you want to delete another trainer?[y/n]"));
                    } else {
                        System.out.println("No trainers stored");
                    }
                    break;

                case 4: //course sub-menu
                    runCourseModMenu();
                    break;
                case -1:
                case -2:
                    runMainMenu();
                    break;
                case -100:
                    this.isExit = true;
                    return;
                default:
                    System.out.println("Unknown Error");
                    break;
            }
        }
    }

    //3rd level menu
    private void runCourseModMenu() {
        System.out.println("Please select relevant course to modify");
        SchoolUnit inspectedTempCourse = (SchoolUnit) chooseFromList(PrivSchool.
                getSchoolUnits());
        while (!this.isExit) {
            System.out.println("You view elements of " + inspectedTempCourse);
            printCourseModMenu();
            int switchOption = chooseOption("[0 1 2 3 4]{1}|exit|back|main");
            ArrayList temp;
            switch (switchOption) {
                case 0:
                    runCourseModMenu();
                    break;
                case 1: //Removing a student from the course
                    if (!inspectedTempCourse.getListOfCourseStudents().isEmpty()) {
                        do {
                            System.out.println("Please select student");
                            temp = new ArrayList(inspectedTempCourse.
                                    getListOfCourseStudents());
                            Collections.sort(temp);//sorted shallow
                            Student studentToBeRemoved = (Student) chooseFromList(
                                    temp);
                            inspectedTempCourse.
                                    removeStudent(studentToBeRemoved);
                        } while (isYesOrNo(
                                "Do you want to delete another student?[y/n]"));
                    } else {
                        System.out.println("No students enrolled in course");
                    }
                    break;
                case 2: //student sub-menu
                    if (!inspectedTempCourse.getListOfCourseStudents().isEmpty()) {
                        runStudentModMenu(inspectedTempCourse);
                    } else {
                        System.out.println("No students enrolled in course");
                    }
                    break;
                case 3: //assign trainer to course
                    do {
                        if (isYesOrNo(
                                "Do you want to assign already stored trainer? [y/n]")) {
                            if (!PrivSchool.getTrainers().isEmpty()) {
                                System.out.println("Available trainers are: ");
                                Trainer tempTrainer = (Trainer) chooseFromList(
                                        PrivSchool.getTrainers());
                                inspectedTempCourse.addTrainer(tempTrainer);
                            } else {
                                System.out.println("No trainers stored");
                            }
                        } else {
                            PrivSchool.buildTrainer();
                            inspectedTempCourse.addTrainer(PrivSchool.
                                    getTrainers().get(
                                            PrivSchool.getTrainers().size() - 1));
                        }
                    } while (isYesOrNo(
                            "Do you want to assign another trainer?[y/n]"));
                    break;
                case 4: //remove trainer from course
                    if (!inspectedTempCourse.getTrainers().isEmpty()) {
                        do {
                            System.out.println("Please select trainer");
                            temp = new ArrayList(inspectedTempCourse.
                                    getTrainers());
                            Collections.sort(temp);//sorted copy
                            Trainer trainerToBeRemoved = (Trainer) chooseFromList(
                                    temp);
                            inspectedTempCourse.
                                    removeTrainer(trainerToBeRemoved);
                        } while (isYesOrNo(
                                "Do you want to remove another trainer?[y/n]"));
                    } else {
                        System.out.println("No trainers stored");
                    }
                    break;
                case -1:
                    runModificationMenu();
                    break;
                case -2:
                    runMainMenu();
                    break;
                case -100:
                    this.isExit = true;
                    return;
                default:
                    System.out.println("Unknown Error");
                    break;
            }
        }
    }

    //4th level menu
    private void runStudentModMenu(SchoolUnit inspectedTempCourse) {
        System.out.println("You view elements of " + inspectedTempCourse);
        System.out.println("Please select relevant student to modify");
        Student inspectedTempStudent = (Student) chooseFromList(
                inspectedTempCourse.getListOfCourseStudents());
        while (!this.isExit) {
            System.out.println("You view elements of " + inspectedTempCourse);
            System.out.println("You view elements of " + inspectedTempStudent);
            printStudentModMenu();
            int switchOption = chooseOption("[0 1 2 3]{1}|exit|back|main");
            ArrayList temp;
            Assignment tempAss;
            switch (switchOption) {
                case 0:
                    runStudentModMenu(inspectedTempCourse);
                    break;
                case 1: //assign marks to student-assignment
                    if (!inspectedTempCourse.getMapOfAssignments().get(
                            inspectedTempStudent).isEmpty()) {
                        do {
                            System.out.println(
                                    "Please select assignment to assign marks");
                            temp = new ArrayList(inspectedTempCourse.
                                    getMapOfAssignments().get(
                                            inspectedTempStudent));
                            Collections.sort(temp);//sorted shallow
                            tempAss = (Assignment) chooseFromList(temp);
                            PrivSchool.gradeStudent(inspectedTempStudent,
                                                    inspectedTempCourse, tempAss);
                        } while (isYesOrNo(
                                "Do you want to assign mark to another assignment?[y/n]"));
                    } else {
                        System.out.println("No assignments stored");
                    }
                    break;
                case 2: //add new assignment to student
                    do {
                        System.out.println("Please provide assignment data:");
                        PrivSchool.addAssignmentToStudent(inspectedTempStudent,
                                                          inspectedTempCourse);
                    } while (isYesOrNo(
                            "Do you want to add another assignment?[y/n]"));
                    break;
                case 3: //delete assignment from student
                    if (!inspectedTempCourse.getMapOfAssignments().get(
                            inspectedTempStudent).isEmpty()) {
                        do {
                            System.out.println(
                                    "Please select assignment to delete");
                            temp = new ArrayList(inspectedTempCourse.
                                    getMapOfAssignments().get(
                                            inspectedTempStudent));
                            Collections.sort(temp);//sorted shallow
                            tempAss = (Assignment) chooseFromList(temp);
                            inspectedTempCourse.getMapOfAssignments().get(
                                    inspectedTempStudent).remove(tempAss);
                        } while (isYesOrNo(
                                "Do you want to delete another assignment?[y/n]"));
                    } else {
                        System.out.println("No assignments stored");
                    }
                    break;
                case -1:
                    runCourseModMenu();
                    break;
                case -2:
                    runMainMenu();
                    break;
                case -100:
                    this.isExit = true;
                    return;
                default:
                    System.out.println("Unknown Error");
                    break;
            }
        }
    }

    //Prints
    private void greet() {
        System.out.println("\nWelcome to " + PrivSchool.getTITLE());
        System.out.println("The Ultimate Coding Course where you can learn "
                           + PrivSchool.getSTREAMS()[0] + " and " + PrivSchool.
                getSTREAMS()[1]
                           + " \n on a " + PrivSchool.getTYPES().stream().
                        collect(Collectors.toList()).toString().replaceAll(
                        "[\\Q][\\E]", "")
                           + " schedule that suits your needs");
    }

    private void printFirstTimeMenu() {
        System.out.println("\nThis is the first time running this program");
        System.out.println(
                "Application can create, for your convenience, relevant"
                + " demo data for the private course structure");
    }

    private void printMainMenu() {
        System.out.println("\nPlease type number to select relevant option :");
        System.out.println("\'1\' for inserting new data to private school");
        System.out.println(
                "\'2\' for viewing and inspecting stored data of private school");
        System.out.println(
                "\'3\' for modifying data of private school [Trainer/Admin Menu]");
        System.out.println(
                "\'exit\' for exiting and stopping the current program");
    }

    private void printCreationMenu() {
        System.out.println(
                "It is strongly recommended to create first\n trainers,"
                + " assignment information and students and then the courses,\n "
                + "in order to use for you convenience the \"bulk assigning\" options");
        System.out.println("\nPlease type number to select relevant option :");
        System.out.println("\'1\' for inserting new Student entry");
        System.out.println("\'2\' for creating new Course entry");
        System.out.println("\'3\' for inserting new Trainer entry");
        System.out.println("\'4\' for inserting Assignment Entries");
        System.out.println("\'back\' for returning to previous Menu");
        System.out.println(
                "\'exit\' for exiting and stopping the current program");
    }

    private void printInspectionMenu() {
        System.out.println("\nPlease type number to select relevant option :");
        System.out.println("\'1\' for viewing list of stored students");
        System.out.println("\'2\' for viewing list of available courses");
        System.out.println("\'3\' for viewing list of available trainers");
        System.out.println("\'4\' for viewing data of a specific course");
        System.out.println("\'5\' for viewing all private school assignments");
        System.out.println(
                "\'6\' for viewing students enrolled in more than one courses");
        System.out.println(
                "\'7\' for viewing students with due assignment in a specific working week");
        System.out.println("\'back\' for returning to previous Menu");
        System.out.println(
                "\'exit\' for exiting and stopping the current program");
    }

    private void printCourseMenu() {
        System.out.println("\nPlease type number to select relevant option :");
        System.out.println("\'0\' for changing course you currently inspect");
        System.out.
                println("\'1\' for viewing list of stored students in course");
        System.out.println("\'2\' for viewing list of course assignments");
        System.out.println("\'3\' for viewing list of assigned trainers");
        System.out.
                println("\'4\' for viewing assignments of a specific student");
        System.out.println("\'back\' for returning to previous Menu");
        System.out.println("\'main\' for returning to Main Menu");
        System.out.println(
                "\'exit\' for exiting and stopping the current program");
    }

    private void printModificationMenu() {
        System.out.println("\nPlease type number to select relevant option :");
        System.out.println("***** General options *****");
        System.out.println(
                "\'0\' for completely removing a course and all of its components");
        System.out.println("*** Student options ***");
        System.out.
                println("\'1\' for enrolling an existing student to a course");
        System.out.println("\'2\' for completely removing a student");
        System.out.println("\'3\' for completely deleting a trainer");
        System.out.
                println("\'4\' for entering course sub-menu for more options");
        System.out.println("\'back\' for returning to previous Menu");
        System.out.println(
                "\'exit\' for exiting and stopping the current program");
    }

    private void printCourseModMenu() {
        System.out.println("\nPlease type number to select relevant option :");
        System.out.println("\'0\' for changing course you currently modify");
        System.out.println("***** Course options *****");
        System.out.println("\'1\' for removing a student from a course");
        System.out.println(
                "\'2\' for entering student sub-menu for more options");
        System.out.println("*** Trainer options ***");
        System.out.println("\'3\' for assigning a new trainer to a course");
        System.out.println("\'4\' for deleting a trainer from a course");
        System.out.println("\'back\' for returning to previous Menu");
        System.out.println("\'main\' for returning to Main Menu");
        System.out.println(
                "\'exit\' for exiting and stopping the current program");
    }

    private void printStudentModMenu() {
        System.out.println("\nPlease type number to select relevant option :");
        System.out.println("\'0\' for changing student you currently modify");
        System.out.println("***** Student options *****");
        System.out.println("*** Assignment options ***");
        System.out.println(
                "\'1\' for assigning grade marks to a student's assignment");
        System.out.println("\'2\' for adding a new assignment to a student");
        System.out.println("\'3\' for deleting an assignment from a student");
        System.out.println("\'back\' for returning to previous Menu");
        System.out.println("\'main\' for returning to Main Menu");
        System.out.println(
                "\'exit\' for exiting and stopping the current program");
    }

    private <T> void printList(List<T> list) {
        for (int i = 1; i <= list.size(); i++) {
            System.out.println("\"" + i + "\" " + list.get(i - 1).toString());
        }
    }

    //Choices
    private boolean isYesOrNo(String question) {
        System.out.println(question);
        while (!sc.hasNext("[yYnN]{1}")) {
            System.out.println(
                    "Invalid Input\nPlease provide valid option [Y/N] for \'yes\' or \'no\'");
            System.out.println(question);
            sc.next();
        }
        boolean result = (sc.hasNext("[yY]{1}"))
                         ? true
                         : (sc.hasNext("[nN]"))
                           ? false
                           : null; //not used !!
        sc.next();
        return result;
    }

    /**
     * Return option as an integer for a switch menu. Validation is done by
     * inserting a relevant regex string, which may result to some options not
     * to be possible to return
     *
     */
    private int chooseOption(String REGEX) {
        int option = 0;
        while (!sc.hasNext(REGEX)) {
            System.out.println("Please provide valid option");
            sc.next();
        }
        if (sc.hasNextInt()) {
            option = sc.nextInt();
        } else if (sc.hasNext("exit")) {
            option = -100;
            sc.next();
        } else if (sc.hasNext("main")) {
            option = -2;
            sc.next();
        } else if (sc.hasNext("back")) {
            option = -1;
            sc.next();
        }
        return option;
    }

    private <T> Object chooseFromList(List<T> list) {
        if (!list.isEmpty()) {
            System.out.println(
                    "Please select the relevant number.\nExisting entries to choose are : ");
            printList(list);
            int option = chooseOption(buildRegex(list.size()));
            return list.get(option - 1);
        } else {
            System.out.println("List is empty");
        }
        return null; //not used hopefully!
    }

    /**
     * Regex builder from an integer iteration in order to create a regex for
     * selecting integers
     */
    private String buildRegex(int counter) {
        StringBuilder regex = new StringBuilder("");
        for (int i = 1; i < counter; i++) {
            regex.append(i + "|");
        }
        regex.append("" + counter);
        return regex.toString();
    }

}
