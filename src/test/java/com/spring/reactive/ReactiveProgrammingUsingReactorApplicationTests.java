package com.spring.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.test.StepVerifier;

@SpringBootTest
class ReactiveProgrammingUsingReactorApplicationTests {

	ReactiveProgrammingUsingReactorApplication programming=new ReactiveProgrammingUsingReactorApplication();
	@Test
	void contextLoads() {
	}

	
	@Test
	void namesFlux()
	{
	
		  var namesFlx=programming.namesFlux();
		  
		  StepVerifier.create(namesFlx).
		  expectNext("rish","sachin","sattu")
		  .verifyComplete();
	}
}
