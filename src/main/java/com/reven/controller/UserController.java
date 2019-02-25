package com.reven.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reven.controller.common.ResResult;
import com.reven.model.entity.User;
import com.reven.service.IUserService;

/**
* Created by CodeGenerator on 2019/01/23.
*/
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    @PostMapping("/add")
    public ResResult add(User user) {
        userService.save(user);
        return ResResult.success();
    }

    @PostMapping("/delete")
    public ResResult delete(@RequestParam Integer id) {
        userService.deleteById(id);
        return ResResult.success();
    }

    @PostMapping("/update")
    public ResResult update(User user) {
        userService.update(user);
        return ResResult.success();
    }

    @GetMapping("/detail")
    public ResResult detail(@RequestParam Integer id) {
        User user = userService.findById(id);
        return ResResult.success(user);
    }
 
}
