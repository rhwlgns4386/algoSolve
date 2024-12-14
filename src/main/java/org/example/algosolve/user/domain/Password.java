package org.example.algosolve.user.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Password {
    private String password;

    public Password(String password,UserPasswordEncoder userPasswordEncoder) {
        this.password = userPasswordEncoder.encode(password);
    }

    public boolean match(String input,UserPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(input, password);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
