package learn.lodging.domain;

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
}