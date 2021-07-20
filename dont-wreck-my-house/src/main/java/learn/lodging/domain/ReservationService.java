package learn.lodging.domain;

import learn.lodging.data.DataException;
import learn.lodging.data.GuestRepository;
import learn.lodging.data.HostRepository;
import learn.lodging.data.ReservationRepository;
import learn.lodging.models.Reservation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationRepository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> findByHostID(String hostId){
        return reservationRepository.findByHostId(hostId);
    }

    public Reservation findReservation(int id){
        //maybe refactor for all the times you needed to find a reservation just do it here.
        return null;
    }

    //add
    //update
    //delete
    //validation
    //getReservationList
        //take hostId
        //loop through host repo.findAll()
        //return host's reservation list

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()){
            return result;
        }
        reservation.setTotal(calculateTotal(reservation));

        result.setPayload(reservationRepository.add(reservation));
        return result;
    }

    public Result<Reservation> update(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        List<Reservation> reservations = reservationRepository.findByHostId(reservation.getHostId());
        Reservation existing = null;
        for (Reservation r: reservations){
            if (r.getId() == reservation.getId()){
                existing = r;
            }
        }
        if (existing == null){
            result.addErrorMessage("Reservation not found.");
            return result;
        }
        reservation.setTotal(calculateTotal(reservation));

        boolean success = reservationRepository.update(reservation);

        if (!success){
            result.addErrorMessage("Reservation not found.");
        }
        return result;
    }

    public Result<Reservation> delete(Reservation reservation) throws DataException {
        Result<Reservation> result = new Result<>();
        if (reservation == null){
            result.addErrorMessage("Reservation not found.");
        }

        boolean success = reservationRepository.delete(reservation);
        if (!success){
            result.addErrorMessage("Reservation not found.");
        }
        return result;
    }

    public BigDecimal calculateTotal (Reservation reservation){
        BigDecimal standard = hostRepository.findById(reservation.getHostId()).getStandardRate();
        BigDecimal weekend = hostRepository.findById(reservation.getHostId()).getWeekendRate();
        LocalDate start = reservation.getStartDate();
        LocalDate end = reservation.getEndDate();
        long days = ChronoUnit.DAYS.between(start,end);
        int numStandard = 0;
        int numWeekend = 0;

        for (;start.compareTo(end) < 0; start = start.plusDays(1)){
            if (start.getDayOfWeek() == DayOfWeek.SATURDAY || start.getDayOfWeek() == DayOfWeek.SUNDAY){
                numWeekend++;
            }else{
                numStandard++;
            }
        }

        return standard.multiply(new BigDecimal(numStandard)).add(weekend.multiply(new BigDecimal(numWeekend)));
    }


    private Result<Reservation> validate(Reservation reservation){
        Result<Reservation> result = validateNulls(reservation);
        if (!result.isSuccess()){
            return result;
        }
        validateDates(reservation,result);

        return result;
    }

    private Result<Reservation> validateNulls(Reservation reservation){
        Result<Reservation> result = new Result<>();

        if (reservation == null){
            result.addErrorMessage("Nothing to save.");
            return result;
        }
        if (guestRepository.findById(reservation.getGuestId()) == null){
            result.addErrorMessage("Guest is required.");
        }
        if (hostRepository.findById(reservation.getHostId()) == null){
            result.addErrorMessage("Host is required.");
        }
        if (reservation.getStartDate() == null){
            result.addErrorMessage("Start date is required.");
        }
        if (reservation.getEndDate() == null){
            result.addErrorMessage("End date is required.");
        }
        return result;
    }

    private void validateDates(Reservation reservation, Result<Reservation> result){
        LocalDate start = reservation.getStartDate();
        LocalDate end = reservation.getEndDate();

        if (start.isAfter(end)){
            result.addErrorMessage("Start date must come before end date.");
        }

        if (start.isBefore(LocalDate.now())){
            result.addErrorMessage("Start date must be in the future.");
        }
        //check overlapping
        List<Reservation> reservations = reservationRepository.findByHostId(reservation.getHostId());
        for (Reservation r: reservations){
            if (!(start.isBefore(r.getStartDate()) && end.isBefore(r.getStartDate())) || !(start.isAfter(r.getEndDate()) && end.isAfter(r.getEndDate()))){
                result.addErrorMessage("Reservation dates cannot overlap.");
            }
        }
    }

}
