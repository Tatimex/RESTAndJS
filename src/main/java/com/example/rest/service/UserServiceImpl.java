package com.example.rest.service;

import com.example.rest.dao.UserDao;
import com.example.rest.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (userDao.findByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
        } else try {
            throw new Exception("Duplicate email!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if (user.getId() == null) {
            try {
                throw new Exception("User not have ID!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        User oldUser = getUserById(user.getId());
        if (user.getPassword().equals("") || user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.save(user);

    }

    @Override
    public User getUserById(Long id) {
        User user = null;
        Optional<User> optional = userDao.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = getUserById(id);
        if (user == null) {
            try {
                throw new Exception("There is no user with ID = " + id + " in Database");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        userDao.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.findByEmail(email);
    }
}
