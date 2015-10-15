package de.codecentric.ebss.service;

import java.util.Collection;

import de.codecentric.ebss.model.Movie;

public interface MovieService {

	Collection<Movie> findMovies(String searchString);

}
