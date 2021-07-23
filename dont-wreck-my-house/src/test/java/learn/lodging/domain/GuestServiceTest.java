package learn.lodging.domain;

import learn.lodging.data.DataException;
import learn.lodging.data.GuestRepositoryDouble;
import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service;

    @BeforeEach
    void setup(){
        GuestRepositoryDouble repository = new GuestRepositoryDouble();
        service = new GuestService(repository);
    }

    @Test
    void shouldFindById() {
        Guest test = service.findById(12);
        assertNotNull(test);
        assertEquals("Horseman",test.getLastName());
    }

    @Test
    void shouldFindByLastName() {
        List<Guest> guests = service.findByLastName("H");
        assertEquals(1,guests.size());
        assertEquals("CA",guests.get(0).getState());
    }

    @Test
    void shouldAddValid() throws DataException {
        Guest test = new Guest();
        test.setFirstName("Todd");
        test.setLastName("Chavez");
        test.setEmail("tchavez@website.com");
        test.setPhoneNum("3332224455");
        test.setState("CA");

        Result<Guest> result = service.add(test);
        assertTrue(result.isSuccess());

    }

    @Test
    void shouldNotAddNull() throws DataException {
        Guest test = new Guest();

        Result<Guest> result = service.add(test);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidEmail() throws DataException {
        Guest test = new Guest();
        test.setFirstName("Todd");
        test.setLastName("Chavez");
        test.setEmail("tchavez@websitecom");
        test.setPhoneNum("3332224455");
        test.setState("CA");

        Result<Guest> result = service.add(test);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidPhone() throws DataException {
        Guest test = new Guest();
        test.setFirstName("Todd");
        test.setLastName("Chavez");
        test.setEmail("tchavez@website.com");
        test.setPhoneNum("33372224455");
        test.setState("CA");

        Result<Guest> result = service.add(test);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddInvalidState() throws DataException {
        Guest test = new Guest();
        test.setFirstName("Todd");
        test.setLastName("Chavez");
        test.setEmail("tchavez@website.com");
        test.setPhoneNum("3332224455");
        test.setState("XY");

        Result<Guest> result = service.add(test);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldUpdateValid() throws DataException {
        Guest bojack = service.findById(12);
        bojack.setEmail("newemail@web.com");
        Result<Guest> result = service.update(bojack);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateInvalid() throws DataException {
        Guest bojack = service.findById(12);
        bojack.setPhoneNum("invalid");
        Result<Guest> result = service.update(bojack);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateMissing() throws DataException {
        Guest test = new Guest(1,"test","guest","test@email.com","2221113344","MD");
        Result<Guest> result = service.update(test);
        assertFalse(result.isSuccess());
    }
}