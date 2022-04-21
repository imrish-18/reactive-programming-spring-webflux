package com.spring.reactive;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MovieInfo {

	
	@Id
	@javax.validation.constraints.NotBlank
	private String movieInfoId;
	
	private String name;
	@NotNull
	@Positive
	private Integer year;
	
	private List<String> cast;
	private LocalDate releaseDate;
	
	
	public MovieInfo()
	{
		
	}
	public String getMovieInfoId() {
		return movieInfoId;
	}

	public void setMovieInfoId(String movieInfoId) {
		this.movieInfoId = movieInfoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<String> getCast() {
		return cast;
	}

	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}


	
	public MovieInfo(String movieInfoId, String name, Integer year, List<String> cast, LocalDate releaseDate) {
		super();
		this.movieInfoId = movieInfoId;
		this.name = name;
		this.year = year;
		this.cast = cast;
		this.releaseDate = releaseDate;
	}
	
}
