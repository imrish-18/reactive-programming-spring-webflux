package com.spring.reactive;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveProgrammingUsingReactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProgrammingUsingReactorApplication.class, args);
		
		ReactiveProgrammingUsingReactorApplication programming=new ReactiveProgrammingUsingReactorApplication();
		programming.namesFlux().subscribe(res->System.out.println(res));
		programming.namesMono().subscribe(res->System.out.println(res));
		programming.namesFlux_FlatMap().subscribe(res->System.out.println(res));
		
		
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