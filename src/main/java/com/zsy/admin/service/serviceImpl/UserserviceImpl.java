package com.zsy.admin.service.serviceImpl;

import com.zsy.admin.domian.po.User;
import com.zsy.admin.mapper.UserMapper;
import com.zsy.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: admin
 * @description:
 * @author: 张思远
 * @create: 2020-05-05 15:42
 */
@Service
public class UserserviceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User login(String username) {
        User user=new User();
        user.setUsername(username);
        user.setPassword( userMapper.getPassword(username) );
        return user;
    }
}
