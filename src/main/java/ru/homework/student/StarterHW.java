package ru.homework.student;

import java.util.ArrayList;
import java.util.List;

public class StarterHW {
    public static void main (String[] args){
        StudentHW st1 = new StudentHW("vasia");
        st1.addGrade(5);
        System.out.println(st1.hashCode());

    }
}
