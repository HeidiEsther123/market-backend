package mx.tecdesoftware.market_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/saludar")
    public String HelloWorld() {
        return "Hello World";
    }
}