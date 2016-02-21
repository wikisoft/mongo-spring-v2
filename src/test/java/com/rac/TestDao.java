package com.rac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rac.config.AppConfig;
import com.rac.dao.UserRepository;

@SpringApplicationConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDao {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAll() {
	System.err.println(userRepository.findAll().size());
    }

}
