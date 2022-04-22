package com.spring.reactive;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
//@RequestMapping("/v1")
public class MovieInfoController {

	 @Autowired
	 MovieInfoRepo repo;
	 
	 Sinks.Many<MovieInfo> moviesInfoSink=Sinks.many().replay().all();
	
	@PostMapping("/moviesInfo")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<MovieInfo> addNewMovieInfo(@RequestBody @Valid MovieInfo movieInfo)
	{
	   return repo.save(movieInfo)
			   .doOnNext(savedInfo->moviesInfoSink.tryEmitNext(savedInfo));	
	}
	
	@GetMapping("/AllmoviesInfo")
	public Flux<MovieInfo> getAllMovie()
	{
     return repo.findAll();		
 	}
	
	
	 @GetMapping(value="/movieInfo/stream",produces=MediaType.APPLICATION_JSON_VALUE)
		
		public Flux<MovieInfo> getMovieByStream()
		{
	   return moviesInfoSink.asFlux().log();
	 	}
	
	
	 @GetMapping("/movieInfo/{id}")
	
		public Mono<ResponseEntity<MovieInfo>> getByIdMovie(@PathVariable String id)
		{
	     return repo.findById(id)
	    		 .map(ResponseEntity.ok()::body).
	    		 switchIfEmpty(Mono.just(ResponseEntity.noContent().build()));
	    	
	 	}
	 
	 
	 
	 @DeleteMapping("/deleteAll")
	 public Mono<String> deleteAllMovie()
		{
	      repo.deleteAll();	
	      return Mono.just("data has been deleted succesfull please try to insert new to new movie");
	 	}
	 @DeleteMapping("/deleteAll/{id}")
	 public Mono<Void> deleteByIdMovie(@PathVariable String id)
		{
	     
		 return repo.deleteById(id);	
	      //return Mono.just("data has been deleted succesfull please try to insert new to new movie");
	 	}
}
