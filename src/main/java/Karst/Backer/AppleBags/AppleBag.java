package Karst.Backer.AppleBags;

import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

// a class to validate and store all information about a bag of apples
public class AppleBag {

    private String Id;
    private int Apples;
    private String Supplier;
    private LocalDate PackedOn;
    private double Price;

    AppleBag(String Id, int Apples, String Supplier, LocalDate PackedOn, double Price) {
        // Id is already validated on creation
        this.Id = Id;

        // validate each input
        if (Apples >= 1 && Apples <= 100) {
            this.Apples = Apples;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of Apples in a bag must be at least 1 and no more than 100.");
        }

        String[] ValidSuppliers = {"Royal Gala", "Pink Lady", "Kanzi Apple", "Elstar Apples"};
        if (Arrays.asList(ValidSuppliers).contains(Supplier)) {
            this.Supplier = Supplier;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Supplier must be one of \"Royal Gala\", \"Pink Lady\", \"Kanzi Apple\", \"Elstar Apples\".");
        }

        if (LocalDate.now().compareTo(PackedOn) >= 0) {
            this.PackedOn = PackedOn;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Packed on cannot be in the future.");
        }
        
        if (Price >= 1 && Price <= 50) {
            this.Price = Price;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be at least 1 and no more than 50.");
        }
    }

    // simple getter for Id
    public String GetId() {
        return this.Id;
    }

    // show details of this bag as a JSON string
    @Override
    public String toString() {
        String Json = "{\n";
        Json +=       "  \"Id\":\"" + Id + "\",\n";
        Json +=       "  \"Apples\":" + Integer.toString(Apples) + ",\n";
        Json +=       "  \"Supplier\":\"" + Supplier + "\",\n";
        Json +=       "  \"PackedOn\":\"" + PackedOn.toString() + "\",\n";
        Json +=       "  \"Price\":" + Double.toString(Price) + "\n";
        Json +=       "}";
        return Json;
    }
}
