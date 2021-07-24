package learn.lodging.ui;

import learn.lodging.data.DataException;
import learn.lodging.domain.GuestService;
import learn.lodging.domain.HostService;
import learn.lodging.domain.ReservationService;
import learn.lodging.domain.Result;
import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import learn.lodging.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run(){
        view.displayHeader("Don't Wreck My House!");
        try {
            runAppLoop();
        } catch (DataException ex){
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");

    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option){
                case VIEW_RESERVATIONS_BY_HOST:
                    viewReservationsByHost();
                    break;
                case MAKE_A_RESERVATION:
                    createReservation();
                    break;
                case EDIT_A_RESERVATION:
                    updateReservation();
                    break;
                case CANCEL_A_RESERVATION:
                    deleteReservation();
                    break;
                case VIEW_RESERVATIONS_BY_GUEST:
                    viewReservationsByGuest();
                    break;
                case ADD_A_GUEST:
                    addGuest();
                    break;
                case EDIT_A_GUEST:
                    updateGuest();
                    break;
                case ADD_A_HOST:
                    addHost();
                    break;
                case EDIT_A_HOST:
                    updateHost();
                    break;
            }
        }while (option != MainMenuOption.EXIT);
    }

    private void viewReservationsByHost(){
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());
        Host host = getHost();
        if (host != null){
            List<Reservation> reservations = reservationService.findByHostID(host.getId());
            view.displayReservationsByHost(reservations, host);
        }
        view.enterToContinue();
    }

    private void createReservation() throws DataException {
        view.displayHeader(MainMenuOption.MAKE_A_RESERVATION.getMessage());
        Host host = getHost();
        Guest guest = getGuest();
        if (host == null || guest == null){
            return;
        }
        List<Reservation> reservations = reservationService.findByHostID(host.getId());
        view.displayReservationsByHost(reservations, host);
        Reservation reservation = view.makeReservation(host,guest);
        if (!confirmReservation(reservation,host)){
            return;
        }

        Result<Reservation> result = reservationService.add(reservation);
        displayResult("Reservation %s created.", reservation, result);
    }

    private void updateReservation() throws DataException {
        view.displayHeader(MainMenuOption.EDIT_A_RESERVATION.getMessage());
        Host host = getHost();
        if (host == null){
            return;
        }
        List<Reservation> reservations = reservationService.findByHostID(host.getId());
        Reservation reservation = view.updateReservation(reservations);
        if (reservation == null){
            //no reservations message
            return;
        }
        if (!confirmReservation(reservation,host)){
            return;
        }

        Result<Reservation> result = reservationService.update(reservation);
        displayResult("Reservation %s updated.", reservation, result);
    }

    private void deleteReservation() throws DataException {
        view.displayHeader(MainMenuOption.CANCEL_A_RESERVATION.getMessage());
        Host host = getHost();
        if (host == null){
            return;
        }
        List<Reservation> reservations = reservationService.findByHostID(host.getId());
        Reservation reservation = view.chooseReservation(reservations);
        if (reservation == null){
            //no reservations message
            return;
        }

        Result<Reservation> result = reservationService.delete(reservation);
        displayResult("Reservation %s deleted.", reservation, result);
    }

    private void viewReservationsByGuest(){
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_GUEST.getMessage());
        Guest guest = getGuest();
        if (guest != null){
            List<Reservation> reservations = reservationService.findByGuestId(guest.getId());
            view.displayReservationsByGuest(reservations,guest);
        }
        view.enterToContinue();

    }

    private void addGuest() throws DataException {
        view.displayHeader(MainMenuOption.ADD_A_GUEST.getMessage());
        Guest guest = view.makeGuest();
        Result<Guest> result = guestService.add(guest);
        if (!result.isSuccess()){
            view.displayStatus(false,result.getErrorMessages());
        }else{
            String successMessage = String.format("Guest %s created.", guest.getId());
            view.displayStatus(true, successMessage);
        }
    }

    private void updateGuest() throws DataException {
        view.displayHeader(MainMenuOption.EDIT_A_GUEST.getMessage());
        Guest guest = getGuest();
        if (guest == null){
            return;
        }
        guest = view.updateGuest(guest);
        Result<Guest> result = guestService.update(guest);
        boolean resRepUpdated = reservationService.updateGuest(guest);

        if (result.isSuccess() && resRepUpdated){
            String successMessage = String.format("Guest %s and their reservation(s) updated.", guest.getId());
            view.displayStatus(true, successMessage);
        }else if (result.isSuccess()) {
            String successMessage = String.format("Guest %s updated.", guest.getId());
            view.displayStatus(true, successMessage);
        }else{
            view.displayStatus(false,result.getErrorMessages());
        }
    }

    private void addHost() throws DataException {
        view.displayHeader(MainMenuOption.ADD_A_HOST.getMessage());
        Host host = view.makeHost();
        Result<Host> result = hostService.add(host);

        if (!result.isSuccess()){
            view.displayStatus(false,result.getErrorMessages());
        }else{
            String successMessage = String.format("Host %s created.", host.getId());
            view.displayStatus(true, successMessage);
        }
    }

    private void updateHost() throws DataException {
        view.displayHeader(MainMenuOption.EDIT_A_HOST.getMessage());
        Host host = getHost();
        if (host == null){
            return;
        }
        host = view.updateHost(host);
        Result<Host> result = hostService.update(host);
        boolean resRepUpdated = reservationService.updateHost(host);

        if (result.isSuccess() && resRepUpdated){
            String successMessage = String.format("Host %s and their reservation(s) updated.", host.getId());
            view.displayStatus(true, successMessage);
        }else if (result.isSuccess()) {
            String successMessage = String.format("Host %s updated.", host.getId());
            view.displayStatus(true, successMessage);
        }else {
            view.displayStatus(false,result.getErrorMessages());
        }
    }

    //support methods
    private Guest getGuest(){
        String lastNamePrefix = view.getLastNamePrefix(false);
        List<Guest> guests = guestService.findByLastName(lastNamePrefix);
        return view.chooseGuest(guests);
    }

    private Host getHost(){
        String lastNamePrefix = view.getLastNamePrefix(true);
        List<Host> hosts = hostService.findByLastName(lastNamePrefix);
        return view.chooseHost(hosts);
    }

    private boolean confirmReservation(Reservation reservation, Host host){
        boolean isConfirmed = false;
        if (reservation != null){
            LocalDate start = reservation.getStartDate();
            LocalDate end = reservation.getEndDate();
            BigDecimal total = reservationService.calculateTotal(start,end,host.getId());
            isConfirmed = view.displaySummary(start,end,total);
        }
        return isConfirmed;
    }

    private void displayResult(String success, Reservation reservation, Result<Reservation> result){
        if (!result.isSuccess()){
            view.displayStatus(false,result.getErrorMessages());
        }else{
            String successMessage = String.format(success,reservation.getId());
            view.displayStatus(true, successMessage);
        }
    }
}
