package com.bwsw.test.gcd.controllers;

import com.bwsw.test.gcd.entities.GcdArgumentsApi;
import com.bwsw.test.gcd.entities.GcdResult;
import com.bwsw.test.gcd.services.GcdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
