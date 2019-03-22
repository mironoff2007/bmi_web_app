import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mironov.bmi.DAO.MockDAO;
import com.mironov.bmi.DAO.MockDAOImpl;
import com.mironov.bmi.Service.Service;
import com.mironov.bmi.Service.ServiceImpl;
import com.mironov.bmi.WrongNumberException;
import org.junit.*;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ServiceTest {

    private Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
            bind(Service.class).to(ServiceImpl.class);
            bind(MockDAO.class).to(MockDAOImpl.class);
        }
    });

    @Before
    public void setup () {
        injector.injectMembers(this);
    }

    @Inject
    public Service service;


    @Test
    public void testWrongHeight()  {
        try {
            service.saveBmi("Vasja",-1,65);
        } catch (WrongNumberException e) {
            assertThat(e.getMessage(), is("Wrong number"));
        }
    }

    @Test
    public void testWrongWeight()  {
        try {
            service.saveBmi("Vasja",90,-1);
        } catch (WrongNumberException e) {
            assertThat(e.getMessage(), is("Wrong number"));
        }
    }

    @Test
    public void testWrongWeightAndheight()  {
        try {
            service.saveBmi("Vasja",0,-1);
        } catch (WrongNumberException e) {
            assertThat(e.getMessage(), is("Wrong number"));
        }
    }


}