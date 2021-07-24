package learn.lodging.ui;

public enum MainMenuOption {

    EXIT(0, "Exit"),
    VIEW_RESERVATIONS_BY_HOST(1, "View Reservations By Host"),
    MAKE_A_RESERVATION(2, "Make a Reservation"),
    EDIT_A_RESERVATION(3, "Edit a Reservation"),
    CANCEL_A_RESERVATION(4, "Cancel a Reservation"),
    VIEW_RESERVATIONS_BY_GUEST(5, "View Reservations By Guest"),
    ADD_A_GUEST(6,"Add a Guest"),
    EDIT_A_GUEST(7,"Edit a Guest"),
    DELETE_A_GUEST(8, "Delete a Guest"),
    ADD_A_HOST(9, "Add a Host"),
    EDIT_A_HOST(10, "Edit a Host"),
    DELETE_A_HOST(11, "Delete a Host");

    private int value;
    private String message;

    private MainMenuOption(int value, String message){
        this.value = value;
        this.message = message;
    }

    public static MainMenuOption fromValue(int value) {
        for (MainMenuOption option : MainMenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        return EXIT;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
