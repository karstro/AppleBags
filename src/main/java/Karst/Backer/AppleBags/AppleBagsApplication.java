package Karst.Backer.AppleBags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.ArrayList;
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
	public List<AppleBag> getAppleBags(@RequestParam(value = "amount", defaultValue = "3") int amount) {
		if (amount < 0) {
			throw new ResponseStatusException(
				HttpStatus.BAD_REQUEST, 
				"amount must be greater than 0."
			);
		}
		amount = Math.min(amount, this.bags.size());

		// construct the response
		return this.bags.subList(0, amount);
	}

	// create an applebag and add it to the list if the arguments are valid
	@PostMapping()
    public void createAppleBag(@RequestBody AppleBag bag) {
		this.bags.add(bag);
	}
}
