package DAO;


import com.mironov.bmi.DAO.TableManager;
import org.junit.Test;

import java.sql.SQLException;

public class TableManagerTest {
    @Test
    public void testTableManager() throws SQLException {
        TableManager.dropTable();
        TableManager.createTable();
        TableManager.populateTable();
    }
}
