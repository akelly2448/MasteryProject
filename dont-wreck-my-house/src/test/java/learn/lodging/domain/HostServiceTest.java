package learn.lodging.domain;

import learn.lodging.data.HostRepositoryDouble;
import learn.lodging.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}