package learn.lodging.data;

import learn.lodging.models.Guest;

import java.util.List;

public interface GuestRepository {
    List<Guest> findAll();
}
