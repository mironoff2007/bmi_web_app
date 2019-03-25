package DAO;


import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mironov.bmi.DAO.DAO;
import com.mironov.bmi.DAO.DAOImpl;
import com.mironov.bmi.DAO.TableManager;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class DAOImplTest {

    private Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(DAO.class).to(DAOImpl.class);
        }
    });

    @Inject
    public DAO dao;

    @Before
    public void setup () {
        injector.injectMembers(this);
    }

    @Test
    public void testGetBmiList() throws SQLException {
        TableManager.dropTable();
        TableManager.createTable();
        TableManager.populateTable();
        System.out.println(dao.getBmiList().toString());
    }
}
