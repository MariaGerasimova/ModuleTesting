package ru.mock.student;

import ru.innotech.Student;

public interface StudentRepoMock {
    boolean getaddGradeBool(int grade);
    int getRaintingForGradeSum(int sum);
    long count();
    void delete(Student entity);
    void deleteAll(Iterable<Student> entities);
    Iterable<Student> findAll();
    Student save(Student entity);
    Iterable<Student> saveAll(Iterable<Student> entities);
}
