package learn.lodging.ui;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import learn.lodging.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io){
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption(){
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE; //set min high to ensure it is lowest option value later
        int max = Integer.MAX_VALUE; //set max low to ensure it is the highest option value later
        for (MainMenuOption option: MainMenuOption.values()){
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s - %s]: ",min,max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public String getLastNamePrefix(boolean isHost) {
        if (isHost){
            return io.readRequiredString("Host last name starts with: ");
        }else{
            return io.readRequiredString("Guest last name starts with: ");
        }
    }

    //public guest/host chooseGuest/Host(List)
    //looks like we should really implement some inheritance here

    public Guest chooseGuest(List<Guest> guests){
        if (guests.size() == 0){
            io.println("No guests found");
        }
        int index = 1;
        for (Guest guest: guests.stream().limit(10).collect(Collectors.toList())){
            io.printf("[%s] - %s %s%n", index++, guest.getFirstName(), guest.getLastName());
        }
        index--;

        if (guests.size() > 10){
            io.println("More than 10 guests found. Showing first 10. Please refine search.");
        }
        io.println("0. Exit");
        String message = String.format("Select a guest by their index[0 - %s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0){
            return null;
        }
        return guests.get(index -1);
    }

    public Host chooseHost(List<Host> hosts){
        if (hosts.size() == 0){
            io.println("No hosts found");
        }
        int index = 1;
        for (Host host: hosts.stream().limit(10).collect(Collectors.toList())){
            io.printf("[%s] - %s %s%n", index++, host.getLastName(), host.getEmail());
        }
        index--;

        if (hosts.size() > 10){
            io.println("More than 10 hosts found. Showing first 10. Please refine search.");
        }
        io.println("0. Exit");
        String message = String.format("Select a host by their index[0 - %s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0){
            return null;
        }
        return hosts.get(index -1);
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public void displayHeader(String message){
        io.println("");
        io.println(message);
        io.println("#".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayReservations(List<Reservation> reservations){
        //id - guest - start - end
    }

    //display hosts/guests

    //display reservation summary
        //-Guest name
        //-start and end dates
        //-total $



}