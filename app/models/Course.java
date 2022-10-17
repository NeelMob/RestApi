package models;

import play.data.validation.Required;
import play.db.jpa.GenericModel;
import javax.persistence.*;

@Entity
@Table(name="course")
public class Course extends GenericModel {


    @Column
    @Id
    public Long id;

   @Column
    public String name;

    @Column
    public String description;

    public Course(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
