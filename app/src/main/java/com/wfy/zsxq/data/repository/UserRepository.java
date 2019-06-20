package com.wfy.zsxq.data.repository;

import com.wfy.zsxq.data.protocol.User;

public class UserRepository {
    public User getUser() {
        User user = new User();
        user.setAge(100);
        user.setName("FY");
        return user;
    }
}
