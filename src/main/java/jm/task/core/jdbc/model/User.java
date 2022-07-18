package jm.task.core.jdbc.model;

import javax.persistence.*;
import javax.transaction.Transactional;

@Transactional
@Entity
@Table(name = "User")

public class User {
    @Id
   //(strategy= GenerationType.SEQUENCE)
         //   generator="Users_seq")
   // @SequenceGenerator(name="Users_seq",
     //       sequenceName="SEQ_USER", allocationSize=10)
    @Column(name="id")//, updatable=false, nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="lastName")
    private String lastName;

    @Column(name="age")
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
