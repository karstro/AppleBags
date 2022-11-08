package Karst.Backer.AppleBags;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Arrays;
import java.util.Random;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

// a class to validate and store all information about a bag of apples
public class AppleBag implements Serializable{

    private String id;
    private int apples;
    private String supplier;
    private LocalDate packedOn;
    private double price;
    private Random rand = new Random();

    // getters and setters for serialization
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getApples() {
        return this.apples;
    }

    public void setApples(int apples) {
        this.apples = apples;
    }

    public String getSupplier() {
        return this.supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    public LocalDate getPackedOn() {
        return this.packedOn;
    }

    public void setPackedOn(LocalDate packedOn) {
        this.packedOn = packedOn;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // generates a random string of capital letters of given length
    private String randomString(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String s = "";
        for (int i = 0; i < length; i++) {
            s += characters.charAt(this.rand.nextInt(26));
        }
        return s;
    }

    AppleBag(int apples, String supplier, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate packedOn, double price) {
        // id is a random string of 5 characters
        this.id = randomString(5);

        // validate each input before setting them
        if (apples >= 1 && apples <= 100) {
            this.apples = apples;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of Apples in a bag must be at least 1 and no more than 100.");
        }

        String[] validSuppliers = {"Royal Gala", "Pink Lady", "Kanzi Apple", "Elstar Apples"};
        if (Arrays.asList(validSuppliers).contains(supplier)) {
            this.supplier = supplier;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Supplier must be one of \"Royal Gala\", \"Pink Lady\", \"Kanzi Apple\", \"Elstar Apples\".");
        }

        if (LocalDate.now().compareTo(packedOn) >= 0) {
            this.packedOn = packedOn;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Packed on cannot be in the future.");
        }
        
        if (price >= 1 && price <= 50) {
            this.price = price;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be at least 1 and no more than 50.");
        }
    }
}
