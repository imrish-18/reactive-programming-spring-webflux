package com.spring.reactive;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class MovieInfoControllerTest {

	@Autowired
	MovieInfoRepo repo;
	
	@Autowired
	WebTestClient client;


//	@BeforeEach
//	void setUp()
//	{
//		var movieinfos = List.of(new MovieInfo(null, "Batman Begins",
//                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
//        new MovieInfo(null, "The Dark Knight",
//                2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
//        new MovieInfo("abc", "Dark Knight Rises",
//                2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));
//		
//		repo.saveAll(movieinfos).blockLast();
//		
	//}
	
//	 @AfterEach
//	    void tearDown() {
//	        repo.deleteAll().block();
//	    }

    
	 
	 
	  @Test
	  void addNewMovieInfo()
	  {
		  var movieinfos =new MovieInfo("2", "Batman Begins",
	                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));
		  client.post()
		  .uri("/moviesInfo")
		  .bodyValue(movieinfos)
		  .exchange()
		  .expectStatus()
		  .isCreated()
		  .expectBody(MovieInfo.class)
		  .consumeWith(res->
		  {
			  var saveMovieInfo=res.getResponseBody();
			  assert saveMovieInfo !=  null;
			  
		  });
		  
	  }
	  
	  
	  @Test
	  void getAllMovie()
	  {
		
		  client.get()
		  .uri("/AllmoviesInfo")
		  .exchange()
		  .expectStatus()
		  .is2xxSuccessful()
		  .expectBodyList(MovieInfo.class)
		  .hasSize(1);
	  }
}



