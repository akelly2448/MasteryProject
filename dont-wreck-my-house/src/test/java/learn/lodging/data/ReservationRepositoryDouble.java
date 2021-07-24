package learn.lodging.data;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import learn.lodging.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    final String hostId = "test-host-id";
    private final ArrayList<Reservation> reservations = new ArrayList<>();
    private final LocalDate start = LocalDate.of(2021,7,24);
    private final LocalDate end = LocalDate.of(2021,7,25);

    public ReservationRepositoryDouble(){
        //add some random reservations to the list
        Reservation one = new Reservation();
        one.setId(1);
        one.setGuestId(GuestRepositoryDouble.GUEST.getId());
        one.setHostId(HostRepositoryDouble.HOST.getId());
        one.setStartDate(start);
        one.setEndDate(end);
        one.setTotal(new BigDecimal("100.00"));
        reservations.add(one);

    }
    @Override
    public List<Reservation> findByHostId(String hostId) {
        return reservations.stream()
                .filter(r -> r.getHostId().equals(hostId))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        reservations.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return findByHostId(reservation.getHostId()) != null;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        return findByHostId(reservation.getHostId()) != null;
    }

    @Override
    public boolean updateGuest(Guest guest) throws DataException {
        return findByGuestId(guest.getId()) != null;
    }

    @Override
    public boolean deleteGuest(Guest guest) throws DataException {
        return false;
    }

    @Override
    public List<Reservation> findByGuestId(int guestId) {
        return reservations.stream()
                .filter(r -> r.getGuestId() == guestId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateHost(Host host) throws DataException {
        return false;
    }

    @Override
    public boolean deleteHost(Host host) throws DataException {
        return false;
    }
}
