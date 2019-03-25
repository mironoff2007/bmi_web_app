package DAO;

import com.mironov.bmi.DAO.ConnectionManager;
import org.junit.Test;

import java.sql.Connection;


import static org.junit.Assert.assertNotNull;

public class ConnectionManagerTest {
    @Test
    public void testConnection()  {
        Connection con=ConnectionManager.getConnection();
        assertNotNull(con);
    }

}
