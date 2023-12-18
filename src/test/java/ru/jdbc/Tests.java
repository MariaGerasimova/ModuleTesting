package ru.jdbc;

import org.junit.jupiter.api.Assertions;
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
    public void checkDeleteDepartment () throws SQLException {
        Service.createDB();
        Connection connection = DBConect.getConnection();
        Department dept = null;
        int countBeforeDelete=0;
        int countAfterDelete=0;
        // проверяем, что департамент есть (не обязательно)
        try {
            String query = "SELECT * FROM Department where Department.NAME='IT'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                dept=new Department(id,name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // сохраняем, сколько сотрудников было до удаления отдела
        System.out.println("----------Было сотрудников до удаления: ----------");
        countBeforeDelete=Conn.countITEmployee();
        //удаляем IT отдел
        Service.removeDepartment(dept);
        Assertions.assertEquals(0,Conn.checkDept());
        // сохраняем, сколько стала сотрудников после удаления отдела
        System.out.println("----------Стало сотрудников после удаления: ----------");
        countAfterDelete=Conn.countITEmployee();
        Assertions.assertEquals(0,countAfterDelete);
    }
}
