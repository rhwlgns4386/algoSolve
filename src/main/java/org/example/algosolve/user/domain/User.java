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
    @Getter
    private String nickName;
    private int level;
    private String githubUrl;
    private String refreshToken;
    private LocalDateTime localDateTime;

    public User(String userId, String password,String nickName ,int level, UserPasswordEncoder userPasswordEncoder) {
        this(userId, password, nickName, level ,"", userPasswordEncoder);
    }

    public User(String userId, String password,String nickName ,int level, String githubUrl, UserPasswordEncoder userPasswordEncoder) {
        this.userId = userId;
        this.password = new Password(password, userPasswordEncoder);
        this.nickName = nickName;
        this.level = level;
        this.githubUrl = githubUrl;
        this.localDateTime = LocalDateTime.now();
    }

    public boolean matchPassword(String password,UserPasswordEncoder userPasswordEncoder){
        return this.password.match(password,userPasswordEncoder);
    }

    public String getPassword() {
        return password.getPassword();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
