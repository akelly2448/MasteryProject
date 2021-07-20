package learn.lodging.data;

import learn.lodging.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository{

    final String hostId = "test-host-id";
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble(){
        //add some random reservations to the list

    }
    @Override
    public List<Reservation> findByHostId(String hostId) {
        return null;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        return false;
    }
}
