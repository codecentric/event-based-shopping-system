package de.codecentric.ebss.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.codecentric.ebss.kafka.OrderEntryProducerConfiguration;
import de.codecentric.ebss.model.Address;
import de.codecentric.ebss.model.Movie;
import de.codecentric.ebss.model.Order;
import de.codecentric.ebss.model.Recipient;
import de.codecentric.ebss.service.MovieService;

@RequestMapping("/movies")
public class MoviesController {

	private Log log = LogFactory.getLog(getClass());

	private MovieService movieService;

	@Autowired
	@Qualifier(OrderEntryProducerConfiguration.OUTBOUND_ID + ".input")
	private MessageChannel messageChannel;
	
	public MoviesController(MovieService movieService, IntegrationFlow flow) {
		this.movieService = movieService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getMovies(Model model, @RequestParam(required = false) String searchString) {
		log.info("getMovies with searchString " + searchString);
		Collection<Movie> movies = movieService.findMovies(searchString);
		model.addAttribute("movies", movies);
		model.addAttribute("searchString", searchString);
		return "movies";
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String getMovieDetails(@PathVariable UUID id, Model model) {
		log.info("getMovieDetails with id " + id);
		model.addAttribute("movie", movieService.getMovieById(id));
		return "movieDetails";
	}
	
	@RequestMapping(value = "{id}/order", method = RequestMethod.GET)
	public String orderMovie(@PathVariable UUID id, Model model) {
		log.info("orderMovie with id " + id);
		
		Movie movie = movieService.getMovieById(id);
		
		// Create some default shipping details
		Address address = new Address(Locale.GERMANY.getDisplayCountry(), "Cologne", "50667", "Domkloster", "4");
		Recipient recipient = new Recipient("Alexander", "Mustermann", address, address);
		Integer amount = ThreadLocalRandom.current().nextInt(1, 15);
		Order order = new Order(amount, movie.getId().toString(), recipient);
		
		BigDecimal totalPrice = movie.getPrice().multiply(BigDecimal.valueOf(amount));
		
		model.addAttribute("movie", movie);
		model.addAttribute("order", order);
		model.addAttribute("totalPrice", totalPrice);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String orderAsJson = ow.writeValueAsString(order);
			messageChannel.send(new GenericMessage<String>(orderAsJson));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "order";
	}
}