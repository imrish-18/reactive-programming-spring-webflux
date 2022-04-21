package com.spring.reactive;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieInfoRepo extends ReactiveMongoRepository<MovieInfo,String> {

}
