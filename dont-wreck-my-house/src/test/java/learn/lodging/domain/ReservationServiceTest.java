package learn.lodging.domain;

import learn.lodging.data.DataException;
import learn.lodging.data.GuestRepositoryDouble;
import learn.lodging.data.HostRepositoryDouble;
import learn.lodging.data.ReservationRepositoryDouble;
import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import learn.lodging.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service;
    ReservationRepositoryDouble reservationRepository = new ReservationRepositoryDouble();
    GuestRepositoryDouble guestRepository = new GuestRepositoryDouble();
    HostRepositoryDouble hostRepository = new HostRepositoryDouble();

    @BeforeEach
    void setup(){
        service = new ReservationService(reservationRepository,
                hostRepository,
                guestRepository);
    }

    @Test
    void shouldFindByHostID() {
        List<Reservation> reservations = service.findByHostID("test-host-id");
        assertEquals(1,reservations.size());
        assertEquals(12,reservations.get(0).getGuestId());
    }

    @Test
    void shouldAdd() throws DataException {
        Reservation test = new Reservation();
        test.setGuestId(12);
        test.setHostId("test-host-id");
        test.setStartDate(LocalDate.of(2021,9,25));
        test.setEndDate(LocalDate.of(2021,9,30));

        Result<Reservation> actual = service.add(test);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotAddNullReservation() throws DataException {
        Reservation test = new Reservation();
        Result<Reservation> actual = service.add(test);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNonexistentGuest() throws DataException {
        Reservation test = new Reservation();
        test.setGuestId(10);
        test.setHostId("test-host-id");
        test.setStartDate(LocalDate.of(2021,9,25));
        test.setEndDate(LocalDate.of(2021,9,30));

        Result<Reservation> actual = service.add(test);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddNonexistentHost() throws DataException {
        Reservation test = new Reservation();
        test.setGuestId(12);
        test.setHostId("id");
        test.setStartDate(LocalDate.of(2021,9,25));
        test.setEndDate(LocalDate.of(2021,9,30));

        Result<Reservation> actual = service.add(test);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldNotAddInvalidDate() throws DataException {
        Reservation test = new Reservation();
        test.setGuestId(12);
        test.setHostId("test-host-id");
        test.setStartDate(LocalDate.of(2021,9,25));
        test.setEndDate(LocalDate.of(2021,9,20));

        Result<Reservation> actual = service.add(test);
        assertFalse(actual.isSuccess());
    }

    @Test
    void shouldUpdateExisting() throws DataException {
        List<Reservation> reservations = service.findByHostID("test-host-id");
        Reservation test = reservations.get(0);

        Result<Reservation> result = service.update(test);
        boolean success = result.isSuccess();

        assertTrue(success);
    }

    @Test
    void shouldNotUpdateMissing() throws DataException {
        Reservation test = new Reservation();
        Result<Reservation> result = service.update(test);
        boolean success = result.isSuccess();

        assertFalse(success);
    }

    @Test
    void shouldDeleteExisting() throws DataException {
        List<Reservation> reservations = service.findByHostID("test-host-id");
        Reservation test = reservations.get(0);
        //set a reservation with specified id
        Result<Reservation> result = service.delete(test);
        boolean success = result.isSuccess();

        assertTrue(success);
    }

    @Test
    void shouldNotDeleteMissing() throws DataException {
        Reservation test = new Reservation();
        Result<Reservation> result = service.delete(test);
        boolean success = result.isSuccess();

        assertFalse(success);
    }

    @Test
    void shouldCalculateProperTotal(){
        LocalDate start = LocalDate.of(2022, 7, 26);
        LocalDate end = LocalDate.of(2022, 7, 28);

        BigDecimal total = service.calculateTotal(start,end,"test-host-id");
        BigDecimal expected = new BigDecimal("200.00");
        assertEquals(expected,total);

        start = LocalDate.of(2022, 7, 29);
        end = LocalDate.of(2022, 7, 31);

        total = service.calculateTotal(start,end,"test-host-id");
        expected = new BigDecimal("400.00");
        assertEquals(expected,total);

    }
}