package Service;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mironov.bmi.DAO.DAO;
import com.mironov.bmi.DAO.MemoryDAOImpl;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceImpl;
import org.junit.*;

import java.sql.SQLException;


public class ServiceTest {

    private Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(Service.class).to(ServiceImpl.class);
            bind(DAO.class).to(MemoryDAOImpl.class);
        }
    });

    @Before
    public void setup () {
        injector.injectMembers(this);
    }

    @Inject
    public Service service;


    @Test(expected = IllegalArgumentException.class)
    public void testWrongHeight() throws SQLException {
            service.saveBmi("Vasja",-1,65);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongWeight() throws SQLException {
            service.saveBmi("Vasja",90,-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongWeightAndheight() throws SQLException {
            service.saveBmi("Vasja",0,-1);
    }


}