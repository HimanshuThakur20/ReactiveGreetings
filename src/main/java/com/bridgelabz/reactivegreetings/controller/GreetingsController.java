package com.bridgelabz.reactivegreetings.controller;


import com.bridgelabz.reactivegreetings.modal.Greetings;
import com.bridgelabz.reactivegreetings.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GreetingsController {

    @Autowired
    GreetingService greetingService;

    @PostMapping("/greets")
    public Mono<Greetings> SaveGreets(@RequestBody Greetings greets){
        return this.greetingService.saveGreeting(greets);

    }

    @GetMapping("/greets")
    public Flux<Greetings> getGreetings(){
        return this.greetingService.getGreeting();
    }

    @GetMapping("/greets/{greetId}")
    public Mono<Greetings> getGreetingById(@PathVariable String greetId){
        return this.greetingService.getGreetingById(Long.parseLong(greetId));
    }

    @DeleteMapping("/greets/{greetId}")
    public Mono<Greetings> deleteGreetingsById(@PathVariable String greetId){
        return this.greetingService.deleteGreetingById(Long.parseLong(greetId));
    }
}
