package ru.homework.student;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestsHW {
    @RepeatedTest(value = 4,name = "correction grades are adding")
    public void marksInRange(RepetitionInfo repetitionInfo) {
        StudentHW stud = new StudentHW("vasia");
        int num=repetitionInfo.getCurrentRepetition()+1;
        System.out.println(num);
        stud.addGrade(num);
        Assertions.assertEquals(stud.getGrades().get(0),num);
    }

    @ParameterizedTest(name = "incorrect grades are adding")
    @MethodSource("ru.homework.student.GradesGenerators#ints")
    public void marksNotInRange(int x) {
        StudentHW stud = new StudentHW("vasia");
        System.out.println(x);
        Assertions.assertThrows(IllegalArgumentException.class, () ->stud.addGrade(x));
    }
    @ParameterizedTest(name = "incorrect grades are adding from Getter")
    @MethodSource("ru.homework.student.GradesGenerators#ints")
    public void addmarksFromGetter (int x){
        StudentHW stud = new StudentHW("vasia");
        stud.getGrades().add(x);
        Assertions.assertEquals(stud.getGrades().size(),0);
    }
    @Test
    public void getGrades (){
        StudentHW stud = new StudentHW("vasia");
        int grade=4;
        stud.addGrade(grade);
        Assertions.assertEquals(stud.getGrades().get(0),grade);
    }

    @Test
    @DisplayName("Checking getName")
    public void getName (){
        String name="vasia";
        StudentHW stud = new StudentHW(name);
        Assertions.assertEquals(stud.getName(),name);
    }
    @Test
    @DisplayName("Checking setName")
    public void setName (){
        StudentHW stud = new StudentHW("vasia");
        String namenew="peter";
        stud.setName(namenew);
        Assertions.assertEquals(stud.getName(),namenew);
    }
    @Test
    @DisplayName("Checking equals TRUE")
    public void equalsStudentTrue (){
        StudentHW stud = new StudentHW("vasia");
        stud.addGrade(5);
        StudentHW stud2 = new StudentHW("vasia");
        stud2.addGrade(5);
        Assertions.assertEquals(stud.equals(stud2),true);
    }
    @Test
    @DisplayName("Checking equals FALSE")
    public void equalsStudentFalseGrade (){
        StudentHW stud = new StudentHW("vasia");
        stud.addGrade(5);
        StudentHW stud2 = new StudentHW("vasia");
        stud2.addGrade(4);
        Assertions.assertEquals(stud.equals(stud2),false);
    }
    @Test
    @DisplayName("Checking equals FALSE")
    public void equalsStudentFalseName (){
        StudentHW stud = new StudentHW("vasia");
        stud.addGrade(5);
        StudentHW stud2 = new StudentHW("vasia1");
        stud2.addGrade(5);
        Assertions.assertEquals(stud.equals(stud2),false);
    }
    @Test
    @DisplayName("Checking HashCode")
    public void hashCodeStudent (){
        StudentHW stud = new StudentHW("vasia");
        stud.addGrade(5);
        Assertions.assertEquals(stud.hashCode(),1455729155);
    }
}
