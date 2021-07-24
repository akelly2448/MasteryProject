package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Reservation;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();

    Guest findById(int id);

    Guest add(Guest guest) throws DataException;

    boolean update(Guest guest) throws DataException;

    boolean delete(Guest guest) throws DataException;
}
