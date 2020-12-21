package com.buuble.blog.controller;

import com.buuble.blog.entity.User;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class GetmyinfoHandler {

    @Autowired
    private UserService userService;

    @GetMapping("/myinfo/{userId}")
    public Map<String, Object> Getmyinfo(@PathVariable("userId") Integer userId) {
        Map<String, Object> status = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> val = new LinkedHashMap<>();
        val.put("status", status);
        val.put("data", data);
        Integer code;
        String msg;
        User user = userService.checkUserbyUserId(userId);
        if (user == null) {
            code = 404;
            msg = "用户不存在";
        } else {
            code = 200;
            msg = "用户信息获取成功";
            data.put("userId", user.getUserId());
            data.put("userName", user.getUsername());
            data.put("userPhone", user.getPhone());
            data.put("userGender", user.getSex());
            data.put("userBirth", user.getAge());
            data.put("userAvatar", user.getPhoto());
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
