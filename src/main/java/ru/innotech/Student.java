package ru.innotech;

import com.sun.jdi.connect.spi.Connection;
import lombok.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class Student {
    @Setter
    private StudentRepo repo;
    @Getter  @Setter
    private String name;
    private List<Integer> grades = new ArrayList<>();
    public Student(String name) {
        this.name = name;
    }
    public List<Integer> getGrades() {
        return grades;
    }
    public void addGrade(int grade) {
        if (grade < 2 || grade > 5) {
            throw new IllegalArgumentException(grade + " is wrong grade");
        }
        grades.add(grade);
    }
    @SneakyThrows
    public int raiting() {
                return repo.getRaintingForGradeSum(
                   grades.stream()
                           .mapToInt(x->x)
                           .sum()
                );
    }

    /*Мой код был
    private String name;
    private List<Integer> grades =new ArrayList<>();

    public Student (String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void setGrades(List<Integer> grades) {
        this.grades = grades;
    }

    public void addGrade(int grade) {
         if(grade<2||grade>5) throw new IllegalArgumentException(grade+" is wrong grade");
        grades.add(grade);
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", marks=" + grades +
                '}';
    }*/
}
