package learn.lodging.ui;

public enum MainMenuOption {

    EXIT(0, "Exit"),
    VIEW_RESERVATIONS_BY_HOST(1, "View Reservations By Host"),
    MAKE_A_RESERVATION(2, "Make a Reservation"),
    EDIT_A_RESERVATION(3, "Edit a Reservation"),
    CANCEL_A_RESERVATION(4, "Cancel a Reservation"),
    ADD_A_GUEST(5,"Add a Guest"),
    EDIT_A_GUEST(6,"Edit a Guest"),
    ADD_A_HOST(7, "Add a Host"),
    EDIT_A_HOST(8, "Edit a Host");

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
