package ru.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Conn {
    //static Connection connection = DBConect.getConnection();

    public static void employee1() throws SQLException {
        Connection connection = DBConect.getConnection();
        try {

            String query = "SELECT * FROM Employee where Name='Ann'";
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();
            int count=Function.countResultSet(rs);
            if (count==1) {

                while (rs.next()) {
                    System.out.println(rs.getInt("DepartmentID")+"\t"+rs.getString("name"));
                    rs.updateInt("DepartmentID",2);
                    rs.updateRow();
                    //проверка
                    System.out.println(rs.getInt("DepartmentID")+"\t"+rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }
    public static void checkEmployee2() throws SQLException {
        Connection connection = DBConect.getConnection();
        try {
            int countUpd=0;
            String query = "SELECT * FROM Employee";
            PreparedStatement preparedStatement = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String name=rs.getString("name");
                    char firstLetter=rs.getString("name").charAt(0); //первый символ
                    if(!(firstLetter>='A' && firstLetter<='Z')){
                        name=name.substring(0,1).toUpperCase()+name.substring(1);
                        System.out.println(name);
                        rs.updateString("Name",name);
                        rs.updateRow();
                        countUpd++;
                    }
                    //проверка
                    System.out.println(rs.getInt("DepartmentID")+"\t"+rs.getString("name"));
                }
            System.out.println("Количество измененных пользователей: " + countUpd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public static int countITEmployee () throws SQLException {
        Connection connection = DBConect.getConnection();
        int countITEmployee=0;
        try {
            String query = "SELECT count(*) FROM Employee join Department on Employee.DepartmentID=Department.ID where Department.NAME='IT'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()==true){
                countITEmployee=rs.getInt(1);
                System.out.println("Количество сотрудников в IT отделе: " + countITEmployee);

            }
            else {
                System.out.println("Нет сотрудников в IT отделе.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countITEmployee;
    }

    public static int checkDept () throws SQLException {
        Connection connection = DBConect.getConnection();
        int countITDept=0;
        try {
            String query = "SELECT * FROM Department where Department.NAME='IT'";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()==true){
                countITDept++;
                System.out.println("Департамент есть: " + countITDept);

            }
            else {
                System.out.println("Департамент IT удален");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countITDept;
    }








    /*public static List<Student> getALL() {

        List<Student> students = new ArrayList<>();

        try {
            String query = "SELECT * FROM STUDENT";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int mark = rs.getInt("mark");

                students.add(new Student(id, name, mark));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }*/
}
