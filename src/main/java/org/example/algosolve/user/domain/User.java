package org.example.algosolve.user.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.algosolve.user.dto.IdPasswordDto;
import org.example.algosolve.user.service.UserData;
import org.springframework.security.authentication.BadCredentialsException;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String userId;
    @Embedded
    private Password password;
    private int level;
    private String githubUrl;
    private LocalDateTime localDateTime;

    public User(String userId, String password, int level, UserPasswordEncoder userPasswordEncoder) {
        this(userId, password, level, "", userPasswordEncoder);
    }

    public User(String userId, String password, int level, String githubUrl, UserPasswordEncoder userPasswordEncoder) {
        this.userId = userId;
        this.password = new Password(password, userPasswordEncoder);
        this.level = level;
        this.githubUrl = githubUrl;
        this.localDateTime = LocalDateTime.now();
    }

    public UserData login(String password,UserPasswordEncoder passwordEncoder) {
        if (!matchPassword(password, passwordEncoder)) {
            throw new BadCredentialsException("비밀 번호가 일치하지 않습니다");
        }
        return new UserData(userId);
    }

    private boolean matchPassword(String value, UserPasswordEncoder userPasswordEncoder) {
        return password.match(value, userPasswordEncoder);
    }

    public String getPassword() {
        return password.getPassword();
    }
}
