package learn.lodging.data;

import learn.lodging.domain.Result;
import learn.lodging.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    private static final String SEED_PATH = "./data/guests-seed.csv";
    private static final String TEST_PATH = "./data/guests-test.csv";
    private GuestFileRepository repository = new GuestFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
        List<Guest> all = repository.findAll();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertTrue(all.get(0).getFirstName().equals("Andrew"));
    }

    @Test
    void shouldAdd() throws DataException {
        Guest guest = new Guest();
        guest.setFirstName("Andrew");
        guest.setLastName("Kelly");
        guest.setEmail("akelly@gmail.com");
        guest.setPhoneNum("2223335555");
        guest.setState("PA");

        Guest actual = repository.add(guest);
        assertNotNull(actual);
        assertEquals("PA", actual.getState());
    }

    @Test
    void shouldUpdateExisting() throws DataException {
        Guest guest = repository.findById(1);
        guest.setPhoneNum("9998887700");
        boolean success = repository.update(guest);
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateMissing() throws DataException {
        Guest guest = new Guest();
        boolean success = repository.update(guest);
        assertFalse(success);
    }

    @Test
    void shouldDeleteExisting() throws DataException {
        Guest guest = repository.findById(1);
        boolean success = repository.delete(guest);
        Guest test = repository.findById(1);

        assertTrue(success);
        assertNull(test.getFirstName());
    }
}