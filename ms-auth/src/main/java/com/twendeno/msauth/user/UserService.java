package com.twendeno.msauth.user;

import com.twendeno.msauth.user.dto.UserUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        Iterable<User> Users = this.userRepository.findAll();
        Users.forEach(users::add);
        return users;
    }

    public User getUserById(String uuid) {
        return this.userRepository.findById(UUID.fromString(uuid)).orElse(null);
    }

    public User updateUser(String uuid, UserUpdateDto userUpdateDto) {
        User existingUser = this.userRepository.findById(UUID.fromString(uuid)).orElse(null);
        if (existingUser == null) {
            return null;
        }
        existingUser.setName(userUpdateDto.name());

        return this.userRepository.save(existingUser);
    }

    public void deleteUser(String uuid) {
        this.userRepository.deleteById(UUID.fromString(uuid));
    }
}
