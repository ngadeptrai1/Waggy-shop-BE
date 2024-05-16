package com.edu.authen.service.impl;

import com.edu.authen.exceptions.DataPresentException;
import com.edu.authen.model.ConfirmationToken;
import com.edu.authen.model.User;
import com.edu.authen.repository.UserRepository;
import com.edu.authen.service.MailerService;
import com.edu.authen.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userDao;

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmailEquals(email);
    }

    @Override
    public  Optional<User> findByAccountName(String account) {
        return userDao.findByAccountNameEquals(account);
    }

    @Override
    public User save(User user) {
        if(userDao.findByAccountNameEquals(user.getAccountName()).isPresent()
        || userDao.findByEmailEquals(user.getEmail()).isPresent()
        ){
            throw new DataPresentException("Account or Email name already taken !");
        }
        else
            user.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return userDao.save(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User update(User u){
        return userDao.save(u);
    }


}
