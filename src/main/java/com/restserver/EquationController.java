package com.restserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquationController {
    @RequestMapping("/equation")
    public ServerResponse processEquation(@RequestParam("firstSlog") double firstSlog,
                                         @RequestParam("sum") double resultSum,
                                         @RequestParam("rangeFrom") double min,
                                         @RequestParam("to") double max){
        EquationService equation = new EquationService(firstSlog, resultSum, min, max);
        ServerResponse response = new ServerResponse();
        response.setEquationRoot(equation.getEquationRoot());
        response.setComment(equation.getComment());
        return response;
    }
}
