package learn.lodging.ui;

import learn.lodging.models.Guest;
import learn.lodging.models.Host;
import learn.lodging.models.Reservation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class View {

    private final ConsoleIO io;
    private final int NAME_LIST_LENGTH = 20;  //control size of list displayed when choosing a host or guest

    public View(ConsoleIO io){
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption(){
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE; //set min high to ensure it is lowest option value later
        int max = Integer.MIN_VALUE; //set max low to ensure it is the highest option value later
        for (MainMenuOption option: MainMenuOption.values()){
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s - %s]: ",min,max);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public Reservation makeReservation(Host host, Guest guest){
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setGuestId(guest.getId());
        reservation.setHost(host);
        reservation.setHostId(host.getId());
        reservation.setStartDate(io.readLocalDate("Start date [MM/dd/yyyy]:"));
        reservation.setEndDate(io.readLocalDate("End date [MM/dd/yyyy]:"));
        return reservation;
    }

    public Guest makeGuest(){
        Guest guest = new Guest();
        guest.setFirstName(io.readRequiredString("Enter First Name: "));
        guest.setLastName(io.readRequiredString("Enter Last Name: "));
        guest.setEmail(io.readRequiredString("Enter Email Address: "));
        guest.setPhoneNum(io.readRequiredString("Enter Phone #: "));
        guest.setState(io.readRequiredString("Enter State: "));
        return guest;
    }

    public Host makeHost(){
        Host host = new Host();
        host.setLastName(io.readRequiredString("Enter Last Name: "));
        host.setEmail(io.readRequiredString("Enter Email Address: "));
        host.setPhoneNum(io.readRequiredString("Enter Phone #: "));
        host.setAddress(io.readRequiredString("Enter Street Address: "));
        host.setCity(io.readRequiredString("Enter City: "));
        host.setState(io.readRequiredString("Enter State: "));
        host.setPostalCode(io.readRequiredString("Enter Postal Code: "));
        host.setStandardRate(io.readBigDecimal("Enter Standard Rate: "));
        host.setWeekendRate(io.readBigDecimal("Enter Weekend Rate: "));

        return host;
    }

    public Reservation updateReservation(List<Reservation> reservations){
        Reservation reservation = chooseReservation(reservations);
        if (reservation != null){
            io.println("");
            io.printf("Editing Reservation %s%n", reservation.getId());
            io.println("");
            updateReservation(reservation);
        }
        return reservation;
    }

    public Guest updateGuest(Guest guest){
        io.println("");
        io.printf("Editing Guest %s%n", guest.getId());
        io.println("");

        guest.setFirstName(io.readString("Enter First Name: ", guest.getFirstName()));
        guest.setLastName(io.readString("Enter Last Name: ", guest.getLastName()));
        guest.setEmail(io.readString("Enter Email Address: ", guest.getEmail()));
        //fix phone #: (###) ####### -> ##########
        guest.setPhoneNum(io.readString("Enter Phone #: ", deserializePhoneNum(guest.getPhoneNum())));
        guest.setState(io.readString("Enter State: ", guest.getState()));
        return guest;
    }

    public Host updateHost(Host host){
        io.println("");
        io.printf("Editing Host %s", host.getId());
        io.println("");

        host.setLastName(io.readString("Enter Last Name: ", host.getLastName()));
        host.setEmail(io.readString("Enter Email Address: ", host.getEmail()));
        host.setPhoneNum(io.readString("Enter Phone #: ", deserializePhoneNum(host.getPhoneNum())));
        host.setAddress(io.readString("Enter Street Address: ", host.getAddress()));
        host.setCity(io.readString("Enter City: ", host.getCity()));
        host.setState(io.readString("Enter State: ", host.getState()));
        host.setPostalCode(io.readString("Enter Postal Code: ", host.getPostalCode()));
        host.setStandardRate(io.readBigDecimal("Enter Standard Rate: ", host.getStandardRate()));
        host.setWeekendRate(io.readBigDecimal("Enter Weekend Rate: ", host.getWeekendRate()));
        return host;

    }

    public String getLastNamePrefix(boolean isHost) {
        if (isHost){
            return io.readRequiredString("Host last name starts with: ");
        }else{
            return io.readRequiredString("Guest last name starts with: ");
        }
    }

    public Guest chooseGuest(List<Guest> guests){
        io.println("");
        if (guests.size() == 0){
            io.println("No guests found");
            return null;
        }
        int index = 1;
        for (Guest guest: guests.stream().limit(NAME_LIST_LENGTH).collect(Collectors.toList())){
            io.printf("[%s] - %s %s%n", index++, guest.getFirstName(), guest.getLastName());
        }
        index--;

        if (guests.size() > NAME_LIST_LENGTH){
            io.printf("More than %s guests found. Showing first %s. Please refine search.%n",NAME_LIST_LENGTH,NAME_LIST_LENGTH);
        }
        io.println("[0] Exit");
        String message = String.format("Select a guest by their index[0 - %s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0){
            return null;
        }
        return guests.get(index - 1);
    }

    public Host chooseHost(List<Host> hosts){
        io.println("");
        if (hosts.size() == 0){
            io.println("No hosts found");
            return null;
        }
        int index = 1;
        for (Host host: hosts.stream().limit(NAME_LIST_LENGTH).collect(Collectors.toList())){
            io.printf("[%s] - %s %s%n", index++, host.getLastName(), host.getEmail());
        }
        index--;

        if (hosts.size() > NAME_LIST_LENGTH){
            io.printf("More than %s hosts found. Showing first %s. Please refine search.%n",NAME_LIST_LENGTH,NAME_LIST_LENGTH);
        }
        io.println("[0] Exit");
        String message = String.format("Select a host by their index[0 - %s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0){
            return null;
        }
        return hosts.get(index - 1);
    }

    public Reservation chooseReservation(List<Reservation> reservations){
        io.println("");
        if (reservations.size() == 0){
            return null;
        }
        int index = 1;
        for (Reservation r: reservations){
            io.printf("[%s] - %s, %s - %s -> %s%n",index++,r.getGuest().getFirstName(),r.getGuest().getLastName(),r.getStartDate(),r.getEndDate());
        }
        index--;
        io.println("[0] Exit");
        String message = String.format("Select reservation by the index[0 - %s]: ", index);

        index = io.readInt(message, 0, index);
        if (index <= 0){
            return null;
        }
        return reservations.get(index - 1);
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    public boolean displaySummary(LocalDate start, LocalDate end, BigDecimal total){
        displayHeader("Summary");
        io.printf("Start date: %s%n",start);
        io.printf("End date: %s%n",end);
        io.printf("Total: $%s%n",total);
        return io.readBoolean("Confirm Reservation? [y/n]: ");
    }

    public void displayHeader(String message){
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
        io.println("");
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

    public void displayReservationsByHost(List<Reservation> reservations, Host host){
        io.println("");
        if (reservations.size() == 0){
            io.println("");
            io.println("Host does not have any reservations.");
            io.println("");
            return;
        }
        String header = String.format("%s's Reservations: %s %s, %s",host.getLastName(), host.getAddress(), host.getCity(), host.getState());
        displayHeader(header);
        //id - last name, first name - start - end
        io.printf("[%s] |   %-13s  | %12s -> %-12s |%n", "Index", "Last Name, First Name", "Start Date", "End Date");
        for (Reservation r: reservations){
            io.printf("[%s]     | %11s, %-11s | %12s -> %-12s |%n",
                    r.getId(),
                    r.getGuest().getLastName(),
                    r.getGuest().getFirstName(),
                    r.getStartDate(),
                    r.getEndDate());
        }
        io.println("");
    }

    public void displayReservationsByGuest(List<Reservation> reservations, Guest guest){
        io.println("");
        if (reservations.size() == 0){
            io.println("");
            io.println("Guest does not have any reservations.");
            io.println("");
            return;
        }
        String header = String.format("%s %s's Reservations", guest.getFirstName(), guest.getLastName());
        displayHeader(header);
        //host name, address, city, state, postal code, start, end, total
        io.printf("%-10s  |  %-15s %s %15s  | %12s -> %-12s | %-8s |%n", "Host","", "Location","", "Start Date", "End Date", "Total $");
        for (Reservation r: reservations){
            io.printf("%-10s  |  %-10s %s, %s %s  | %12s -> %-12s | $%s |%n",
                    r.getHost().getLastName(),
                    r.getHost().getAddress(),
                    r.getHost().getCity(),
                    r.getHost().getState(),
                    r.getHost().getPostalCode(),
                    r.getStartDate(),
                    r.getEndDate(),
                    r.getTotal());
        }
        io.println("");
    }

    public void displayNullReservation(String action){
        io.println("");
        io.printf("No Reservation to %s.%n", action);
        io.println("");
    }

    private Reservation updateReservation(Reservation reservation){
        LocalDate start = io.readLocalDate("Start date [MM/dd/yyyy]:", reservation.getStartDate());
        reservation.setStartDate(start);
        LocalDate end = io.readLocalDate("End date [MM/dd/yyyy]: ", reservation.getEndDate());
        reservation.setEndDate(end);

        return reservation;
    }

    private String deserializePhoneNum(String phoneNum){
        char[] digits = new char[10];
        int index = 0;
        for (int i = 0; i < phoneNum.length(); i++){
            if (Character.isDigit(phoneNum.charAt(i))){
                digits[index] = phoneNum.charAt(i);
                index++;
            }
        }
        return String.valueOf(digits);
    }

    //display hosts/guests

}
