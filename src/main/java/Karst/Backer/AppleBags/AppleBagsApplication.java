package Karst.Backer.AppleBags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController
@SpringBootApplication
public class AppleBagsApplication {
	// list to keep track of all applebags that were added to the application
	List<AppleBag> bags = new ArrayList<AppleBag>();
	// initialize logger object
	Logger logger = LoggerFactory.getLogger(AppleBagsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppleBagsApplication.class, args);
	}

	// Get a given amount of applebags as JSON, default to 3
	@GetMapping()
	public String getAppleBags(@RequestBody Map<String, Object> request) {
		// retrieve and validate amount
		int amount;
		if (request.containsKey("amount")) {
			amount = (int)request.get("amount");
		} else {
			amount = 3;
		}
		if (amount < 0) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, 
				"amount must be greater than 0."
			);
		}
		amount = Math.min(amount, this.bags.size());

		// construct the response
		String bagsOutput = "{";
		for (int i = 0; i < amount; i++) {
			if (i != 0) {
				bagsOutput += ",\n";
			}
			bagsOutput += this.bags.get(i).toString();
		}
		return bagsOutput + "}";
	}

	// generates a random string of capital letters of given length
    private String randomString(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        String S = "";
        for (int i = 0; i < length; i++) {
            S += characters.charAt(rand.nextInt(26));
        }
        return S;
    }

	// get an AppleBag from the bags list if its Id matches, otherwise return null
	private AppleBag getBagById(String id) {
		for (int i = 0; i < this.bags.size(); i++) {
			if (this.bags.get(i).GetId() == id) {
				return this.bags.get(i);
			}
		}
		return null;
	}

	// create an applebag and add it to the list if the arguments are valid
	@PostMapping()
	public void createAppleBag(@RequestBody Map<String, Object> request) {
		// Generate an Id (random string), try again if it is already in use
		String id;
		do {
			id = randomString(5);
		} while (getBagById(id) != null);

		// retrieve the inputs from the request
		int apples = (int)request.get("apples");
		String supplier = (String)request.get("supplier");
		LocalDate packedOn = LocalDate.parse((String)request.get("packedOn"), DateTimeFormatter.ISO_LOCAL_DATE);
		double price = (double)request.get("price");

		// create a new bag and add it to the list
		this.bags.add(new AppleBag(id, apples, supplier, packedOn, price));
	}
}
