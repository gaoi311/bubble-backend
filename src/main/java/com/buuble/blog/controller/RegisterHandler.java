package com.buuble.blog.controller;

import com.buuble.blog.entity.Classification;
import com.buuble.blog.entity.User;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RegisterHandler {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestParam("userPhone") String userPhone,
                                        @RequestParam("userPassword") String userPassword) {
        System.out.println(userPhone);
        Map<String, Object> status = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> val = new LinkedHashMap<>();
        val.put("status", status);
        val.put("data", data);
        Integer code;
        String msg;
        if (!userService.checkPhone(userPhone)) {
            code = 404;
            msg = "手机号格式错误";
        } else {
            if (!userService.checkPassword(userPassword)) {
                code = 404;
                msg = "密码格式错误";
            } else {
                User user = userService.checkUserbyPhone(userPhone);
                if (user != null) {
                    code = 404;
                    msg = "用户已存在";
                } else {
                    User user1 = new User();
                    user1.setPhone(userPhone);
                    user1.setUsername("A" + userPhone);
                    user1.setPassword(userPassword);
                    user1.setPhoto("userPhotos/" + "default.png");
                    User user2 = userService.addUser(user1);
                    if (user2 == null) {
                        code = 404;
                        msg = "用户添加失败";
                    } else {
                        Classification classification = new Classification();
                        classification.setUserId(user2.getUserId());
                        classification.setClassificationName("默认");
                        userService.addClassification(classification);
                        code = 200;
                        msg = "注册成功";
                    }
                }
            }
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
