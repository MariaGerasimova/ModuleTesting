package ru.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.office.Department;
import ru.office.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tests {

    @Test
    public  void checkUpdate_Ann () throws SQLException {
        Service.createDB();
        Conn.employee1();
    }
    @Test
    public  void checkEmployee () throws SQLException {
        Service.createDB();
        Conn.checkEmployee2();
    }
    @Test
    public  void countITEmployee () throws SQLException {
        Service.createDB();
        Conn.countITEmployee();
    }

    @Test
    @DisplayName("Проверка удаления департамента")
    public void checkDeleteDepartment () throws SQLException {
        Service.createDB();
        Connection connection = DBConect.getConnection();
        Department dept = null;
        String nameDept="IT";
        int countBeforeDelete=0;
        int countAfterDelete=0;
        // проверяем, что департамент есть (не обязательно)
            dept=Conn.infoDepartment(nameDept);
        // сохраняем, сколько сотрудников было до удаления отдела
            System.out.println("----------Было сотрудников до удаления: ----------");
            countBeforeDelete=Conn.countITEmployee();
            if (countBeforeDelete==0) throw new RuntimeException("Test doesn't valid!");
        //удаляем IT отдел
            Service.removeDepartment(dept);
            Assertions.assertEquals(0,Conn.checkDept(nameDept));
        // сохраняем, сколько стала сотрудников после удаления отдела
            System.out.println("----------Стало сотрудников после удаления: ----------");
            countAfterDelete=Conn.countITEmployee();
            Assertions.assertEquals(0,countAfterDelete);
    }
}
