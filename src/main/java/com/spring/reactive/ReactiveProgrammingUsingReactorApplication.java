package com.spring.reactive;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.bson.Document;

@SpringBootApplication
public class ReactiveProgrammingUsingReactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProgrammingUsingReactorApplication.class, args);
		
		ReactiveProgrammingUsingReactorApplication programming=new ReactiveProgrammingUsingReactorApplication();
		programming.namesFlux().subscribe(res->System.out.println(res));
		programming.namesMono().subscribe(res->System.out.println(res));
		programming.namesFlux_FlatMap().subscribe(res->System.out.println(res));

		try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
			// Access the database
			MongoDatabase database = mongoClient.getDatabase("MovieInfo");

			// Access the collection
			MongoCollection<org.bson.Document> collection=database.getCollection("inventory");

			// Insert multiple documents
			collection.insertMany(Arrays.asList(
					 org.bson.Document.parse("{ item: 'journal', qty: 25, size: { h: 14, w: 21, uom: 'cm' }, status: 'A' }"),
					Document.parse("{ item: 'notebook', qty: 50, size: { h: 8.5, w: 11, uom: 'in' }, status: 'A' }"),
					Document.parse("{ item: 'paper', qty: 100, size: { h: 8.5, w: 11, uom: 'in' }, status: 'D' }"),
					Document.parse("{ item: 'planner', qty: 75, size: { h: 22.85, w: 30, uom: 'cm' }, status: 'D' }"),
					Document.parse("{ item: 'postcard', qty: 45, size: { h: 10, w: 15.25, uom: 'cm' }, status: 'A' }")
			));

			System.out.println("Documents inserted successfully!");
			System.out.printf("collections is "+collection.find());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  Flux<String> namesFlux()
	{
		return Flux.fromIterable(List.of("rish","sachin","sattu")).log();
	}
	
	public  Flux<String> namesFluxC_zip()
	{
		var abcFlux=Flux.just("a","b","c");
		var defFlux=Flux.just("d","e","f");
		return Flux.zip(abcFlux, defFlux,(first,second)->first+second);
	}
	
	public  Flux<String> namesFlux_FlatMap()
	{
		return Flux.fromIterable(List.of("rish","sachin","sattu"))
				.map(res->res.toUpperCase())
				.filter(res->res.length()>2)
				.flatMap(res->splitString(res))
				.log();
				
	}
	
	public  Flux<String> namesFlux_ConcatMap()
	{
		return Flux.fromIterable(List.of("rish","sachin","sattu"))
				.map(res->res.toUpperCase())
				.filter(res->res.length()>2)
				.concatMap(res->splitString(res))
				.log();
	}
	 public Flux<String> splitString(String name)
	 {
		 var charArray=name.split("");
		 return Flux.fromArray(charArray).
				 delayElements(Duration.ofSeconds(1));
		 
	 }
	
	public  Mono<String> namesMono()
	{
		return Mono.just("rishabh");
}
}