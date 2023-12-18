package ru.innotech;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.innotech.Student;

import javax.management.ConstructorParameters;
import java.util.List;


class Tests {
    @Test
    public void testRaiting () {
        Student stud = new Student("vasia");
        stud.addGrade(4);
        //stud.setRepo (new StudentRepositoryMock());
        StudentRepo repo=Mockito.mock(StudentRepo.class); // создание mock класса StudentRepo
        stud.setRepo(repo);
        Mockito.when(repo.getRaintingForGradeSum(Mockito.anyInt()))
                                .thenReturn(10);
        System.out.println(stud);
        Assertions.assertEquals(10,stud.raiting());
    }

    @RepeatedTest(value = 4,name = "корректные оценки добавляются")
    public void marksInRange(RepetitionInfo repetitionInfo) {
        Student stud = new Student("vasia");
        int num=repetitionInfo.getCurrentRepetition()+1;
        System.out.println(num);
        stud.addGrade(num);
        Assertions.assertEquals(stud.getGrades().get(0),num);
    }

    @ParameterizedTest(name = "добавление неверных оценок")
    @MethodSource("ru.innotech.MarksGenerator#ints")
    public void marksNotInRange(int x) {
        Student stud = new Student("vasia");
        System.out.println(x);
        Assertions.assertThrows(IllegalArgumentException.class, () ->stud.addGrade(x));
    }
}