package Karst.Backer.AppleBags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@RestController
@SpringBootApplication
public class AppleBagsApplication {
	// list to keep track of all applebags that were added to the application
	List<AppleBag> Bags = new ArrayList<AppleBag>();

	public static void main(String[] args) {
		SpringApplication.run(AppleBagsApplication.class, args);
	}

	// Get a given amount of applebags as JSON, default to 3
	@GetMapping("/GetAppleBags")
	public String GetAppleBags(@RequestParam(value = "amount", defaultValue = "3") int Amount) {
		if (Amount < 0) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, 
				"amount must be greater than 0."
			);
		}
		Amount = Math.min(Amount, this.Bags.size());
		String BagsOutput = "{";
		for (int i = 0; i < Amount; i++) {
			BagsOutput += this.Bags.get(i).toString() + "\n";
		}
		return BagsOutput + "}";
	}

	// generates a random string of capital letters of given length
    private String RandomString(int Length) {
        final String Characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        String S = "";
        for (int i = 0; i < Length; i++) {
            S += Characters.charAt(rand.nextInt(26));
        }
        return S;
    }

	// get an AppleBag from the Bags list if its Id matches, otherwise return null
	private AppleBag GetBagById(String Id) {
		for (int i = 0; i < this.Bags.size(); i++) {
			if (this.Bags.get(i).GetId() == Id) {
				return this.Bags.get(i);
			}
		}
		return null;
	}

	// create an applebag and add it to the list if the arguments are valid
	@GetMapping("/CreateAppleBag")
	public String CreateAppleBag(
								@RequestParam(value = "apples") int Apples,
								@RequestParam(value = "supplier") String Supplier,
								@RequestParam(value = "packedOn") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate PackedOn,
								@RequestParam(value = "price") float Price
							) {
		// Generate an Id (random string), try again if it is already in use
		String Id;
		do {
			Id = RandomString(5);
		} while (GetBagById(Id) != null);

		// if an argument is invalid, return the error message to the requestor
		try {
			this.Bags.add(new AppleBag(Id, Apples, Supplier, PackedOn, Price));
		} catch(IllegalArgumentException e) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, 
				e.getMessage()
			);
		}
		return "Success";
	}
}
