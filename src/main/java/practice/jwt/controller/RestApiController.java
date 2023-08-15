package practice.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class RestApiController {

    @GetMapping("/home")
    public String home() {
        return "<h1>Home</h1>";
    }
}
