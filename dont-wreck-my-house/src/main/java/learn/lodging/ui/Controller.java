package learn.lodging.ui;

import learn.lodging.data.DataException;
import learn.lodging.domain.GuestService;
import learn.lodging.domain.HostService;
import learn.lodging.domain.ReservationService;
import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import learn.lodging.models.Reservation;
import org.springframework.stereotype.Component;

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

    private void runAppLoop(){
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option){
                case VIEW_RESERVATIONS_BY_HOST:
                    viewReservations();
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
            }
        }while (option != MainMenuOption.EXIT);
    }

    private void viewReservations(){
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_BY_HOST.getMessage());
        List<Reservation> reservations = reservationService.findByHostID(getHost().getId());
        
    }

    private void createReservation(){

    }

    private void updateReservation(){

    }

    private void deleteReservation(){

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
}
