package Karst.Backer.AppleBags;

import java.time.LocalDate;
import java.util.Arrays;

public class AppleBag {

    private String Id;
    private int Apples;
    private String Supplier;
    private LocalDate PackedOn;
    private float Price;

    AppleBag(String Id, int Apples, String Supplier, LocalDate PackedOn, float Price) {
        this.Id = Id;

        if (Apples >= 1 || Apples <= 100) {
            this.Apples = Apples;
        } else {
            throw new IllegalArgumentException("Number of Apples in a bag must be at least 1 and no more than 100.");
        }

        String[] ValidSuppliers = {"Royal Gala", "Pink Lady", "Kanzi Apple", "Elstar Apples"};
        if (Arrays.asList(ValidSuppliers).contains(Supplier)) {
            this.Supplier = Supplier;
        } else {
            throw new IllegalArgumentException("Supplier must be one of \"Royal Gala\", \"Pink Lady\", \"Kanzi Apple\", \"Elstar Apples\".");
        }

        if (LocalDate.now().compareTo(PackedOn) >= 0) {
            this.PackedOn = PackedOn;
        } else {
            throw new IllegalArgumentException("Packed on cannot be in the future.");
        }
        
        if (Price >= 1 || Price <= 50) {
            this.Price = Price;
        } else {
            throw new IllegalArgumentException("Price must be at least 1 and no more than 50.");
        }
    }

    public String GetId() {
        return this.Id;
    }

    // @Override
    // public String toString() {
    //     JSONObject obj = ;
    //     return obj.toString();
    //     Integer.toString(Amount)
    // }
}
