package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import learn.lodging.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    List<Reservation> findByHostId(String hostId);

    Reservation add(Reservation reservation) throws DataException;

    boolean update(Reservation reservation) throws DataException;

    boolean delete(Reservation reservation) throws DataException;

    boolean updateGuest(Guest guest) throws DataException;

    boolean deleteGuest(Guest guest) throws DataException;

    List<Reservation> findByGuestId(int guestId);

    boolean updateHost(Host host) throws DataException;

    boolean deleteHost(Host host) throws DataException;
}
