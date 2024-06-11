package com.example.hdrezka.service;

import com.example.hdrezka.entity.user.Role;
import com.example.hdrezka.entity.user.User;
import com.example.hdrezka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User registerUser(String username, String password) {
        // Проверяем, существует ли пользователь с таким именем
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        // Шифруем пароль
        String encodedPassword = passwordEncoder.encode(password);

        // Создаем нового пользователя
        User newUser = new User(username, encodedPassword, Role.USER);

        // Сохраняем пользователя в базе данных
        return userRepository.save(newUser);
    }
}