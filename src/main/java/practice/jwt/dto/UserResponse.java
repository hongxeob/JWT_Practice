package practice.jwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.jwt.model.User;

@NoArgsConstructor
@Getter
public class UserResponse {

    private String username;
    private String password;

    public UserResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getUsername(), user.getPassword());
    }
}
