package com.bridgelabz.reactivegreetings.controller;


import com.bridgelabz.reactivegreetings.modal.Greetings;
import com.bridgelabz.reactivegreetings.modal.Response;
import com.bridgelabz.reactivegreetings.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GreetingsController {

    @Autowired
    GreetingService greetingService;

    @PostMapping("/greets")
    public Mono<ResponseEntity<Response>> SaveGreets(@RequestBody Greetings greets){
        return this.greetingService.saveGreeting(greets)
                .map(saveGreeting-> new ResponseEntity<>(new Response(200,"Greeting saved successfully"),HttpStatus.OK))
                .onErrorResume(e->Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @GetMapping("/greets")
    public Mono<ResponseEntity<Flux<Greetings>>> getGreetings(){
        return this.greetingService.getGreeting()
                .collectList().map(greetingsList -> !greetingsList.isEmpty()?
                        new ResponseEntity<>(Flux.fromIterable(greetingsList),HttpStatus.OK):
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/greets/{greetId}")
    public Mono<ResponseEntity<?>> getGreetingById(@PathVariable String greetId){
        String message = "Greeting with ID " + greetId + " not found";
        return this.greetingService.getGreetingById(Long.parseLong(greetId))
                .flatMap(greetingdById -> {
                    if(greetingdById != null){
                        return Mono.just(new ResponseEntity<>(greetingdById,HttpStatus.OK));
                    }else{
                        return Mono.just(new ResponseEntity<>(message,HttpStatus.NOT_FOUND));
                    }
                } )
                .switchIfEmpty(Mono.just(new ResponseEntity<>(message,HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/greets/{greetId}")
    public Mono<ResponseEntity<Response>> deleteGreetingsById(@PathVariable String greetId){

        return this.greetingService.deleteGreetingById(Long.parseLong(greetId))
                .flatMap(deletionResult -> {
                    if (deletionResult) {
                        return Mono.just(new ResponseEntity<>(new Response(204, "Greeting removed successfully"), HttpStatus.OK));
                    } else {
                        return Mono.just(new ResponseEntity<>(new Response(404, "Greeting doesn't exist"), HttpStatus.NOT_FOUND));
                    }
                })
                .onErrorResume(error -> Mono.just(new ResponseEntity<>(new Response(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
