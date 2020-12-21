package com.buuble.blog.controller;

import com.buuble.blog.entity.User;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class UpdatemyinfoHandler {

    @Autowired
    private UserService userService;

    @PutMapping("/myinfo/{userId}")
    public Map<String, Object> Updatemyinfo(@PathVariable("userId") Integer userId,
                                            @RequestParam("userName") String userName,
                                            @RequestParam("userPhone") String userPhone,
                                            @RequestParam("userGender") String userGender,
                                            @RequestParam("userBirth") String userBirth) {
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
            if (!userService.checkUsername(userName)) {
                code = 404;
                msg = "用户名格式错误";
            } else {
                if (userService.checkUserbyUsername(userName) != null
                        && !userName.equals(user.getUsername())) {
                    code = 404;
                    msg = "用户名重复";
                } else {
                    if (!userService.checkPhone(userPhone)) {
                        code = 404;
                        msg = "手机号格式错误";
                    } else {
                        if (userService.checkUserbyPhone(userPhone) != null
                                && !userPhone.equals(user.getPhone())) {
                            code = 404;
                            msg = "手机号重复";
                        } else {
                            user.setUserId(userId);
                            user.setUsername(userName);
                            user.setPhone(userPhone);
                            user.setSex(userService.checkUserGender(userGender));
                            user.setAge(userService.strTodate(userBirth));
                            //user.setPhoto(userService.dowPhoto(userAvatar, userId));
                            User user1 = userService.addUser(user);
                            if (user1 == null) {
                                code = 404;
                                msg = "用户信息修改失败";
                            } else {
                                code = 200;
                                msg = "用户信息修改成功";
                            }
                        }
                    }
                }
            }
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }

    @PutMapping("/avatar/{userId}")
    public Map<String, Object> Updatemyinfo(@PathVariable("userId") Integer userId,
                                            @RequestParam("userAvatar") MultipartFile userAvatar) throws Exception {
        Map<String, Object> status = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> val = new LinkedHashMap<>();
        val.put("status", status);
        val.put("data", data);
        System.out.println(userAvatar);
        Integer code;
        String msg;
        User user = userService.checkUserbyUserId(userId);
        if (user == null) {
            code = 404;
            msg = "用户不存在";
        } else {
            user.setPhoto(userService.dowPhoto(userAvatar, userId));
            User user1 = userService.addUser(user);
            if (user1 == null) {
                code = 404;
                msg = "用户信息修改失败";
            } else {
                code = 200;
                msg = "用户信息修改成功";
                data.put("userAvatar",user1.getPhoto());
            }
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;

    }
}
