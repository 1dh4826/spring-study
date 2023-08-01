package com.example.springtest.controller;

import com.example.springtest.cpart.maintenance.dao.UserMainDao;
import com.example.springtest.cpart.publish.dao.UserPublishDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class UserController {

    @Value("${user.name}")
    private String userName;

    @Autowired
    private UserMainDao userMainDao;

    @Autowired
    private UserPublishDao userPublishDao;

    @PostMapping("/getUser")
    public String getUser() {
        Integer mainUserCount = userMainDao.getUserCount();
        Integer publishUserCount = userPublishDao.getUserCount();
        return userName + ":" + mainUserCount + ":" + publishUserCount;

    }
}
