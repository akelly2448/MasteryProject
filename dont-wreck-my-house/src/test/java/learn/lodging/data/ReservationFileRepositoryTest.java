package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservation-seed-test-host-id.csv";
    static final String TEST_FILE_PATH = "./data/reservation_data_test/test-host-id.csv";
    static final String TEST_DIR_PATH = "./data/reservation_data_test";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_FILE_PATH),
                Paths.get(TEST_FILE_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindByHostId() {
        List<Reservation> reservations = repository.findByHostId("test-host-id");
        assertEquals(1,reservations.size());
        assertEquals(24, reservations.get(0).getGuestId());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation res = new Reservation();
        res.setStartDate(LocalDate.of(2021,06,24));
        res.setEndDate(LocalDate.of(2021,06,30));
        res.setGuestId(33);
        res.setTotal(new BigDecimal("150.50"));
        res.setHostId("test-host-id");

        Reservation actual = repository.add(res);

        assertNotNull(actual);
        assertEquals(33,actual.getGuestId());
    }

    @Test
    void shouldUpdateExisting() throws DataException {
        //ran into error because test reservation does not have hostId
        //this won't happen in the future because reservations are given a host once added
        List<Reservation> reservations = repository.findByHostId("test-host-id");
        Reservation test = reservations.get(0);
        test.setHostId("test-host-id");
        boolean success = repository.update(reservations.get(0));
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateMissing() throws DataException {
        Reservation test = new Reservation(3,LocalDate.of(2021,01,01),LocalDate.of(2021,02,02),50,new BigDecimal("1000.00"), "test-host-id");
        boolean success = repository.update(test);
        assertFalse(success);
    }

    @Test
    void shouldDeleteExisting() throws DataException {
        List<Reservation> reservations = repository.findByHostId("test-host-id");
        Reservation test = reservations.get(0);
        test.setHostId("test-host-id");
        boolean success = repository.delete(test);
        List<Reservation> newList = repository.findByHostId("test-host-id");

        assertTrue(success);
        assertEquals(0,newList.size());
    }

    @Test
    void shouldNotDeleteMissing() throws DataException {
        Reservation test = new Reservation(3,LocalDate.of(2021,01,01),LocalDate.of(2021,02,02),50,new BigDecimal("1000.00"), "test-host-id");
        boolean success = repository.delete(test);
        assertFalse(success);
    }

    @Test
    void shouldUpdateGuestReservations() throws DataException {
        List<Reservation> reservations = repository.findByHostId("test-host-id");
        Reservation test = reservations.get(0);
        Guest guest = test.getGuest();
        guest.setPhoneNum("0009990909");
        boolean success = repository.updateGuest(guest);
        assertTrue(success);
    }

    @Test
    void shouldNotUpdateGuestWithoutReservation() throws DataException {
        Guest guest = new Guest(5,"test","guest","email","phone","state");
        boolean success = repository.updateGuest(guest);
        assertFalse(success);
    }
}