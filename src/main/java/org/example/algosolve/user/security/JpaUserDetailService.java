package org.example.algosolve.user.security;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.example.algosolve.user.domain.User;
import org.example.algosolve.user.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class JpaUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
        return new org.springframework.security.core.userdetails.User(user.getUserId(),user.getPassword(), Collections.emptyList());
    }
}
