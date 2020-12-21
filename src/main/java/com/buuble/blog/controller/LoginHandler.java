package com.buuble.blog.controller;

import com.buuble.blog.entity.User;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class LoginHandler {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam("userName") String userStr,
                                     @RequestParam("userPassword") String userPassword) {
        Map<String, Object> status = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> val = new LinkedHashMap<>();
        Map<String, Object> userinfo = new LinkedHashMap<>();
        val.put("status", status);
        val.put("data", data);
        data.put("userToken", "13.33.32");
        data.put("user", userinfo);
        Integer code;
        String msg;
        User user = userService.checkUserbyPhone(userStr);
        if (user == null) {
            user = userService.checkUserbyUsername(userStr);
            if (user == null) {
                code = 404;
                msg = "用户不存在";
                status.put("code", code);
                status.put("msg", msg);
                return val;
            }
        }
        if (!user.getPassword().equals(userPassword)) {
            code = 404;
            msg = "密码错误";
        } else {
            code = 200;
            msg = "登录成功";
            userinfo.put("userId", user.getUserId());
            userinfo.put("userName", user.getUsername());
            userinfo.put("userAvatar", user.getPhoto());
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
