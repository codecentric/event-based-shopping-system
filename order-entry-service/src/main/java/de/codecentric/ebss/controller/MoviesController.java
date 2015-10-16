package de.codecentric.ebss.controller;

import java.util.Collection;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.ebss.model.Movie;
import de.codecentric.ebss.service.MovieService;

@RequestMapping("/movies")
public class MoviesController {

	private Log log = LogFactory.getLog(getClass());
	protected MovieService movieService;

	public MoviesController(MovieService movieService) {
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
	
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public String orderMovie(@PathVariable UUID id, Model model) {
		log.info("orderMovie with id " + id);
		return "order";
	}
}