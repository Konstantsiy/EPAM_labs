package com.restserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EquationController {
    @RequestMapping("/equation")
    public ServerResponse EquationSolution(@RequestParam("firstSlog") double firstSlog,
                                         @RequestParam("sum") double resultSum,
                                         @RequestParam("borderFrom") double min,
                                         @RequestParam("to") double max){
        ServerResponse response = new ServerResponse(firstSlog, resultSum, min, max);
        return response;
    }
}
