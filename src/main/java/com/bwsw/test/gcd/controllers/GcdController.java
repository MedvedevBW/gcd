package com.bwsw.test.gcd.controllers;

import com.bwsw.test.gcd.entities.GcdArgumentsApi;
import com.bwsw.test.gcd.entities.GcdArgumentsBase;
import com.bwsw.test.gcd.entities.GcdResult;
import com.bwsw.test.gcd.repositories.GcdArgumentsRepository;
import com.bwsw.test.gcd.repositories.GcdResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GcdController {

    @Autowired
    private GcdArgumentsRepository gcdArgumentsRepository;

    @Autowired
    private GcdResultRepository gcdResultRepository;

    @GetMapping
    @RequestMapping("/get-result/{id}")
    public GcdResult getResult(@PathVariable Long id) {
        //return "Congratulations from BlogController.java " + id;
        return gcdResultRepository.findByGcdArgumentsId(id);
    }

    @PostMapping
    @RequestMapping("/calculate-gcd")
    public Long calculateGcd(@RequestBody GcdArgumentsApi arguments) {
        GcdArgumentsBase argumentsBase = new GcdArgumentsBase(arguments.getFirst(), arguments.getSecond());
        return gcdArgumentsRepository.findByGcdArguments(argumentsBase.getFirst(), argumentsBase.getSecond())
                .map(GcdArgumentsBase::getId).orElseGet(
                        () -> {
                            Long newArgumentsId = gcdArgumentsRepository.save(argumentsBase).getId();
                            GcdResult result = new GcdResult(newArgumentsId, "simple status");
                            gcdResultRepository.save(result);
                            return newArgumentsId;
                        }
                );
    }
}
