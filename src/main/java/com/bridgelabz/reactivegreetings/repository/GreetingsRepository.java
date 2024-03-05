package com.bridgelabz.reactivegreetings.repository;

import com.bridgelabz.reactivegreetings.modal.Greetings;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface GreetingsRepository extends R2dbcRepository<Greetings,Integer> {

}
