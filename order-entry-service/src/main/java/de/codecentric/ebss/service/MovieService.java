package de.codecentric.ebss.service;

import java.util.Collection;
import java.util.UUID;

import de.codecentric.ebss.model.Movie;

public interface MovieService {

	Collection<Movie> findMovies(String searchString);

	Movie getMovieById(UUID id);

}
