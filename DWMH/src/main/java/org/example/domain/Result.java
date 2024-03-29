package org.example.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Generic Result Class. In this case will only be used with Reservation,
//But if I create more services in the future, I could re-use this class
//On other objects.
//This result object can store success/error messages
// and also a payload, such as a reservation object being returned to the ui.
public class Result<T> {
    //Attributes
    private T payload;
    private ArrayList<String> messages = new ArrayList<>();

    //Methods
    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> result = (Result<?>) o;

        if (!Objects.equals(payload, result.payload)) return false;
        return Objects.equals(messages, result.messages);
    }

    @Override
    public int hashCode() {
        int result = payload != null ? payload.hashCode() : 0;
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        return result;
    }
}
