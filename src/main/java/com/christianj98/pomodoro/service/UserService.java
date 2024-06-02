package com.christianj98.pomodoro.service;

import com.christianj98.pomodoro.dto.RegisterRequestDto;
import com.christianj98.pomodoro.model.UserInfo;

public interface UserService {
    Long getCurrentUserId();
    UserInfo getCurrentUser();
    void createUser(RegisterRequestDto registerRequestDto);
}
