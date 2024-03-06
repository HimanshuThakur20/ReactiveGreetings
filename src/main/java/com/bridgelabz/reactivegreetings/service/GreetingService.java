package com.bridgelabz.reactivegreetings.service;

import com.bridgelabz.reactivegreetings.modal.Greetings;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GreetingService {
    Mono<Greetings> saveGreeting(Greetings greeting);
    Flux<Greetings> getGreeting();
    Mono<Greetings> getGreetingById(long id);
    Mono<Boolean> deleteGreetingById(long id);
}
