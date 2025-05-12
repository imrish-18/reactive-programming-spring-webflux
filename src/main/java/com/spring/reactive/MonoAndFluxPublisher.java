package com.spring.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.function.Function;

public class MonoAndFluxPublisher {
    public static void main(String[] args) {
        Mono<String> objectMono= Mono.error(new RuntimeException("Error "));
        Mono<String> m1=Mono.just("hello rishabh sharma..")
                .log()
                .then(objectMono);


       objectMono.subscribe(System.out::println);
//        Observe all Reactive Streams signals and trace them using Logger support. Default will use Level.
//        INFO and java. util. logging. If SLF4J is available, it will be used instead

        // consumer by the subscribing
        m1.subscribe(data->{
            System.out.println("data is "+data);
        });

        Mono<String> m2=Mono.just("hello rishabh");
        Mono<String> m3=Mono.just("how are you doing ...");
        Mono<Integer> integerMono=Mono.just(1234);

//       Mono<Tuple2<String,String>> tuple2Mono=Mono.zip(m2,m3);
//       tuple2Mono.subscribe(System.out::println);
//       tuple2Mono.subscribe(data->{
//           System.out.println(data.getT1());
//           System.out.println(data.getT2());
//       });
//
//        Mono<Tuple2<String,String>> tuple2MonoWithZip=m2.zipWith(m2);
//        tuple2MonoWithZip.subscribe(data->{
//            System.out.println(data.getT1());
//            System.out.println(data.getT2());
//        });
//
//        Mono.zip(m2,m3,integerMono);
//
//       Mono<String> result= m2.map(String::toUpperCase);
//       result.subscribe(System.out::println);
       Mono<String[]> res=m2.flatMap(valueM2->Mono.just(valueM2.split(" ")));

       res.subscribe(data-> {
           Arrays.stream(data).forEach(System.out::println);
       });
        Flux<String> stringFlux = m2.flatMapMany(valueM2 -> Flux.just(valueM2.split(" ")));
        stringFlux.subscribe(System.out::println);

        Function<Flux<String>,Flux<String>> fluxFluxFunction = (name) -> name.map(String::toUpperCase);
        transform(fluxFluxFunction);

    }
    public static void transform(Function<Flux<String>, Flux<String>> fluxFluxFunction) {
        Flux.just("rishabh").transform(fluxFluxFunction).log();
    }
}
