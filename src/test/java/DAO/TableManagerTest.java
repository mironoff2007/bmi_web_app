package DAO;


import com.mironov.bmi.DAO.TableManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableManagerTest {
    @Test
    public void testADropTable() throws SQLException {
        TableManager.dropTable();
    }
    @Test
    public void testBCreateTable() throws SQLException {
        TableManager.createTable();
    }

    @Test
    public void testCPopulateTable() throws SQLException {
        TableManager.populateTable();
        TableManager.resetTable();
    }
}
