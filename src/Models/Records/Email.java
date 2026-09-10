package Models.Records;

import java.util.Objects;

public record Email(String value) {
    public Email {
        Objects.requireNonNull(value, "Email cannot be null.");
        if (!value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Please provide a valid email address: " + value);
        }
    }
}