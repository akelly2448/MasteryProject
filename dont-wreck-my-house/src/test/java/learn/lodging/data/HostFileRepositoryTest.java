package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    private static final String SEED_PATH = "./data/hosts-seed.csv";
    private static final String TEST_PATH = "./data/hosts-test.csv";
    private HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Host> all = repository.findAll();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertTrue(all.get(0).getLastName().equals("Yearnes"));
    }

    @Test
    void shouldAdd() throws DataException {
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

        Host actual = repository.add(host);
        assertNotNull(actual);
        assertEquals("Casey",host.getLastName());
    }
}