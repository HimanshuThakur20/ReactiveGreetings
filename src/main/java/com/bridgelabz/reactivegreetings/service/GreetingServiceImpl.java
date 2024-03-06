package com.bridgelabz.reactivegreetings.service;

import com.bridgelabz.reactivegreetings.modal.Greetings;
import com.bridgelabz.reactivegreetings.repository.GreetingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GreetingServiceImpl implements GreetingService{

    @Autowired
    private GreetingsRepository greetingsRepository;
    List<Greetings> greets;
    public void GreetingServiceImp() {
    }

    @Override
    public Mono<Greetings> saveGreeting(Greetings greeting) {
        return greetingsRepository.save(greeting);
    }

    @Override
    public Flux<Greetings> getGreeting() {
        return greetingsRepository.findAll();
    }

    @Override
    public Mono<Greetings> getGreetingById(long id) {
    return greetingsRepository.findById((int) id);
    }

    @Override
    public Mono<Boolean> deleteGreetingById(long id) {
        return greetingsRepository.findById((int) id)
                .flatMap(greetings -> {
                    // Record exists, delete it and return true
                    return greetingsRepository.delete(greetings)
                            .then(Mono.just(true));
                })
                .switchIfEmpty(Mono.just(false));
    }
}
