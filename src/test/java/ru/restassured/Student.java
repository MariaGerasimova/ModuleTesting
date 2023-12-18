package ru.restassured;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    Integer id;
    String name;
    List<Integer> marks =new ArrayList<>();//оценки

    public Student (){};
    public Student (Integer id, String name,  List<Integer> marks){
        this.id=id;
        this.name=name;
        this.marks=marks;
    }
    public Student (Integer id, String name){
        this.id=id;
        this.name=name;
        //this.marks=null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getMarks() {
        return marks;
    }

    public void setMarks(List<Integer> marks) {
        this.marks = marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(marks, student.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, marks);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", marks=" + marks +
                '}';
    }
}
