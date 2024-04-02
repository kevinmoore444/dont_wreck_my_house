package org.example.ui;

public enum MainMenuOption {
    EXIT,
    View_Reservations,
    ADD,
    UPDATE,
    DELETE;

    @Override
    public String toString() {
        return switch (this) {
            case EXIT -> "Exit";
            case View_Reservations -> "View Reservations for Host Location";
            case ADD -> "Make a Reservation";
            case UPDATE -> "Edit a Reservation";
            case DELETE -> "Cancel a Reservation";
        };
    }
}