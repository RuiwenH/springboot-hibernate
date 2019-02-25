package com.reven.service.impl;

import com.reven.dao.UserDaoImpl;
import com.reven.model.entity.User;
import com.reven.service.IUserService;
import com.reven.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/23.
 */
@Service
@Transactional(readOnly=true)
public class UserServiceImpl extends AbstractService<User> implements IUserService {
    @Resource
    private UserDaoImpl userDao;


}
