package Models.Records;

import java.util.Objects;

public record Phone(String value) {
    public Phone {
        Objects.requireNonNull(value, "Phone number cannot be null.");
        if (!value.matches("^\\+994\\d{10}$")) {
            throw new IllegalArgumentException("Phone number must be in +994XXXXXXXXXX format: " + value);
        }
    }
}