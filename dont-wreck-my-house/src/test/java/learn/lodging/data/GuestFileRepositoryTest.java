package learn.lodging.data;

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
}