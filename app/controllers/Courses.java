package controllers;

import models.Course;
import play.data.validation.*;
import play.mvc.Controller;

import java.util.List;

public class Courses extends Controller {



  //GET
    public static void list(int pageSize, int pageNumber) {

        if(pageSize == 0) {
            pageSize = 5;
        }

        List<Course> course = Course.find("order by Id desc").fetch(pageNumber,pageSize);
        renderJSON(course);

    }

    //GET by specific id
    public static void listC(Long id) {
        Course course = Course.findById(id);
        renderJSON(course);
    }

    //POST
    public static void add(Long id,  String name,String description) {

        Course course = new Course(id,name,description);
        course.create();
        renderJSON("Course is added successfully"+ "\n" +
        course.id +  "\n" + course.name + "\n" + course.description);
        }
}
