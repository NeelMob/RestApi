package controllers;

import models.Course;
import net.spy.memcached.MemcachedClient;
import play.data.validation.*;
import play.mvc.Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
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
        render(course);
    }

    //POST
    public static void add(Long id,  String name,String description) {

        Course course = new Course(id,name,description);
        course.create();
        renderJSON("Course is added successfully"+ "\n" +
        course.id +  "\n" + course.name + "\n" + course.description);
        }

    public static void MemcachedJava() throws IOException {

            // Connecting to Memcached server on localhost
            MemcachedClient mcc = new MemcachedClient(new
                    InetSocketAddress("localhost", 11211));
            renderText("Connection to server successful" + "\n" + ("set status:"+mcc.set("city", 900, "bangalore").isDone()) + "\n" + ("Get from Cache:"+mcc.get("city")) + "\n" +("Delete from Cache:"+mcc.delete("city").isDone()) + "\n" +("Get from Cache:"+mcc.get("city")));
            System.out.println("set status:"+mcc.set("city", 900, "bangalore").isDone());

            // Get value from cache
            System.out.println("Get from Cache:"+mcc.get("city"));

            // delete value from cache
            System.out.println("Delete from Cache:"+mcc.delete("city").isDone());

            // check whether value exists or not
            System.out.println("Get from Cache:"+mcc.get("city"));
        }
    }
