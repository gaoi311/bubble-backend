package com.buuble.blog.controller;

import com.buuble.blog.entity.Comment;
import com.buuble.blog.entity.User;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class GetcommentsHandler {
    @Autowired
    private UserService userService;

    @GetMapping("/comments/{blogId}")
    public Map<String, Object> Getcomments(@PathVariable("blogId") Integer blogId) {
        Map<String, Object> status = new LinkedHashMap<>();
        List<Map<String, Object>> data = new LinkedList<>();
        Map<String, Object> val = new LinkedHashMap<>();
        val.put("status", status);
        val.put("data", data);
        Integer code;
        String msg;
        if (userService.checkBlogbyBlogId(blogId) == null) {
            code = 404;
            msg = "博客不存在";
        } else {
            List<Comment> comments = userService.getCommentbyBlogId(blogId);
            for (Comment j : comments) {
                Map<String, Object> userdata = new LinkedHashMap<>();
                User user1 = userService.getUserbyUserId(j.getUserId());
                userdata.put("userId", user1.getUserId());
                userdata.put("userName", user1.getUsername());
                userdata.put("userAvatar", user1.getPhoto());
                Map<String, Object> Notices = new LinkedHashMap<>();
                Notices.put("user", userdata);
                Notices.put("comment", j.getCommentContent());
                data.add(Notices);
            }
            code = 200;
            msg = "获取成功";
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
