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
public class Course implements Comparable<Course> {

    //<editor-fold defaultstate="collapsed" desc="instance variables">
    /*
    *title will be CBx
    * stream is "Java" or "C#"
    *type is "full-time" or "part-time"
    *start on 11/11/2019 and end on 25/5/2020
     */
//</editor-fold>
    private String title;
    private String stream;
    private String type;
    private LocalDate start_date;
    private LocalDate end_date;
    private static int instantiationCounter; //a static counter of objects potentially used as an id number also.
    private int id = 9000;

    public Course(String title, String stream, String type, LocalDate start_date,
                  LocalDate end_date) {
        instantiationCounter++;
        id += instantiationCounter;
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;

    }

    @Override
    public String toString() {
        return "Course : " + title + " " + stream + " " + type + ", starts on " + start_date + " and ends on " + end_date;
    }

    @Override
    public int compareTo(Course o) {
        return (this.id - o.id);
    }

    //Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }

    public static int getInstantiationCounter() {
        return instantiationCounter;
    }
}
