package learn.lodging.domain;

import learn.lodging.data.GuestRepositoryDouble;
import learn.lodging.data.HostRepositoryDouble;
import learn.lodging.data.ReservationRepositoryDouble;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void findByHostID() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}