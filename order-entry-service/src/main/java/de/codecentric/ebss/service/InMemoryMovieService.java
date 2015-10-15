package de.codecentric.ebss.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.ebss.model.Movie;

public class InMemoryMovieService implements MovieService {

	private Log log = LogFactory.getLog(getClass());
	private Map<UUID, Movie> movies = new ConcurrentHashMap<UUID, Movie>();
	private String defaultMovies = "src/main/resources/movies.json";

	public InMemoryMovieService(ObjectMapper objectMapper) {
		ObjectMapper mapper = new ObjectMapper();
		List<Movie> movieList = new ArrayList<Movie>();
		try {
			log.info("Read default movies from JSON file " + defaultMovies);
			movieList = mapper.readValue(new File(defaultMovies), new TypeReference<List<Movie>>() {});
			for (Movie movie : movieList) {
				movies.put(movie.getId(), movie);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Movie> findMovies(String searchString) {
		return movies.values();
	}
}
