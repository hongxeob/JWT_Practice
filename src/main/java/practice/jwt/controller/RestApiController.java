package practice.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import practice.jwt.dto.UserJoinRequest;
import practice.jwt.service.UserService;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserService userService;

    @GetMapping("/home")
    public String home() {
        return "<h1>Home</h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody UserJoinRequest request) {
        userService.join(request);
        return "회원가입완료";
    }
}
