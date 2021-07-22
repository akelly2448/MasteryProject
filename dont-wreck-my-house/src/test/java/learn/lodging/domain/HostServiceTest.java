package learn.lodging.domain;

import learn.lodging.data.DataException;
import learn.lodging.data.HostRepositoryDouble;
import learn.lodging.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service;

    @BeforeEach
    void setup(){
        HostRepositoryDouble repository = new HostRepositoryDouble();
        service = new HostService(repository);
    }


    @Test
    void shouldFindById() {
        Host test = service.findById("test-host-id");
        assertNotNull(test);
        assertEquals("Kelly",test.getLastName());
    }

    @Test
    void shouldFindByLastName() {
        List<Host> hosts = service.findByLastName("K");
        assertEquals(1,hosts.size());
        assertEquals("Philly",hosts.get(0).getCity());
    }

    @Test
    void shouldAddValid() throws DataException {
        Host host = new Host();
        host.setLastName("Casey");
        host.setEmail("juscasey@ursinus.edu");
        host.setPhoneNum("3334445555");
        host.setAddress("10 house road");
        host.setCity("Philly");
        host.setState("PA");
        host.setPostalCode("12345");
        host.setStandardRate(new BigDecimal("400.00"));
        host.setWeekendRate(new BigDecimal("500.00"));

        Result<Host> result = service.add(host);
        assertTrue(result.isSuccess());
    }
    @Test
    void shouldNotAddNull() throws DataException {
        Host test = new Host();

        Result<Host> result = service.add(test);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidEmail() throws DataException {
        Host host = new Host();
        host.setLastName("Casey");
        host.setEmail("juscaseyu@rsinusedu");
        host.setPhoneNum("3334445555");
        host.setAddress("10 house road");
        host.setCity("Philly");
        host.setState("PA");
        host.setPostalCode("12345");
        host.setStandardRate(new BigDecimal("400.00"));
        host.setWeekendRate(new BigDecimal("500.00"));

        Result<Host> result = service.add(host);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPhone() throws DataException {
        Host host = new Host();
        host.setLastName("Casey");
        host.setEmail("juscaseyu@rsinus.edu");
        host.setPhoneNum("4445555");
        host.setAddress("10 house road");
        host.setCity("Philly");
        host.setState("PA");
        host.setPostalCode("12345");
        host.setStandardRate(new BigDecimal("400.00"));
        host.setWeekendRate(new BigDecimal("500.00"));

        Result<Host> result = service.add(host);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPostal() throws DataException {
        Host host = new Host();
        host.setLastName("Casey");
        host.setEmail("juscaseyu@rsinus.edu");
        host.setPhoneNum("2224445555");
        host.setAddress("10 house road");
        host.setCity("Philly");
        host.setState("PA");
        host.setPostalCode("123");
        host.setStandardRate(new BigDecimal("400.00"));
        host.setWeekendRate(new BigDecimal("500.00"));

        Result<Host> result = service.add(host);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidState() throws DataException {
        Host host = new Host();
        host.setLastName("Casey");
        host.setEmail("juscaseyu@rsinus.edu");
        host.setPhoneNum("2224445555");
        host.setAddress("10 house road");
        host.setCity("Philly");
        host.setState("invalid state");
        host.setPostalCode("12345");
        host.setStandardRate(new BigDecimal("400.00"));
        host.setWeekendRate(new BigDecimal("500.00"));

        Result<Host> result = service.add(host);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidRate() throws DataException {
        Host host = new Host();
        host.setLastName("Casey");
        host.setEmail("juscaseyu@rsinus.edu");
        host.setPhoneNum("2224445555");
        host.setAddress("10 house road");
        host.setCity("Philly");
        host.setState("PA");
        host.setPostalCode("12345");
        host.setStandardRate(new BigDecimal("400.00"));
        host.setWeekendRate(new BigDecimal("-500.00"));

        Result<Host> result = service.add(host);
        assertFalse(result.isSuccess());
    }

}