package Models;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import Models.Records.*;


public final class Customer {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int id;
    private String name;
    private Email email;
    private Phone phone;


    public Customer(String name, Email email, Phone phone) {
        this.id = ID_GENERATOR.getAndIncrement();
        setName(name);
        this.email = Objects.requireNonNull(email, "Customer email cannot be null.");
        this.phone = Objects.requireNonNull(phone, "Customer phone cannot be null.");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        String trimmedName = name.trim();

        if (trimmedName.length() < 2 || trimmedName.length() > 50) {
            throw new IllegalArgumentException("Name must be between 2 and 50 caracters");
        }
        this.name = trimmedName;
    }

    public void updateContactInfo(Email email, Phone phone) {
        this.email = Objects.requireNonNull(email, "Customer email cannot be null.");
        this.phone = Objects.requireNonNull(phone, "Customer phone cannot be null.");
    }
}