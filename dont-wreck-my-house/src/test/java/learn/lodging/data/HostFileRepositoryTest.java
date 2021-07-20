package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
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
}