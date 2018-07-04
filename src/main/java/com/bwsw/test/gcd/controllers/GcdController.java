package com.bwsw.test.gcd.controllers;

import com.bwsw.test.gcd.configuration.AppConfig;
import com.bwsw.test.gcd.entities.*;
import com.bwsw.test.gcd.rabbitmq.Producer;
import com.bwsw.test.gcd.repositories.GcdArgumentsRepository;
import com.bwsw.test.gcd.repositories.GcdResultRepository;
import com.bwsw.test.gcd.services.GcdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class GcdController {
    @Autowired
    private GcdService gcdService;

    @GetMapping
    @RequestMapping("/get-result/{id}")
    public GcdResult getResult(@PathVariable Long id) {
        return gcdService.get(id);
    }

    @PostMapping
    @RequestMapping("/calculate-gcd")
    public Long calculateGcd(@RequestBody @Valid GcdArgumentsApi arguments) {
        return gcdService.calculate(arguments.getFirst(), arguments.getSecond());
    }


}
