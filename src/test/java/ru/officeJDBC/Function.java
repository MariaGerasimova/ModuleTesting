package ru.officeJDBC;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Function {

    public static int countResultSet (ResultSet rs) throws SQLException {

        int countResultSet=0;
        while (rs.next()) {
            countResultSet++;
        }
        rs.beforeFirst();
        return countResultSet;
    }
}
