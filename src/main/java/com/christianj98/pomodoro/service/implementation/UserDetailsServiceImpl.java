package com.christianj98.pomodoro.service.implementation;

import com.christianj98.pomodoro.dao.UserRepository;
import com.christianj98.pomodoro.model.CustomUserDetails;
import com.christianj98.pomodoro.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
        UserInfo user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Username not found: " + username);
                    return new UsernameNotFoundException("could not found user..!!");
                });
        return new CustomUserDetails(user);
    }
}
