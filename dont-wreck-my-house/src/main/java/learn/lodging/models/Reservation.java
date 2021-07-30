package learn.lodging.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {

    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private int guestId;
    private BigDecimal total;
    private Host host;
    private String hostId;


    public Reservation(){ }

    public Reservation(int id, LocalDate startDate, LocalDate endDate, int guestId, BigDecimal total, String hostId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestId = guestId;
        this.total = total;
        this.hostId = hostId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public void setGuest(Guest guest){this.guest = guest;}

    public void setHost(Host host){this.host = host;}

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setHostId(String hostId){this.hostId = hostId;}

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getGuestId() {
        return guestId;
    }

    public Guest getGuest() {return guest;}

    public Host getHost() {return host;}

    public BigDecimal getTotal() {
        return total;
    }

    public String getHostId() {
        return hostId;
    }
}
