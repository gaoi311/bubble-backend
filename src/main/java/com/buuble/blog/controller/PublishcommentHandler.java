package com.buuble.blog.controller;

import com.buuble.blog.entity.Comment;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class PublishcommentHandler {
    @Autowired
    private UserService userService;

    @PostMapping("/comment")
    public Map<String, Object> Publishcomment(@RequestParam("userId") Integer userId,
                                              @RequestParam("blogId") Integer blogId,
                                              @RequestParam("comment") String comment) {
        Map<String, Object> status = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> val = new LinkedHashMap<>();
        val.put("status", status);
        val.put("data", data);
        Integer code;
        String msg;
        if (userService.checkUserbyUserId(userId) == null) {
            code = 404;
            msg = "用户不存在";
        } else {
            if (userService.checkBlogbyBlogId(blogId) == null) {
                code = 404;
                msg = "博客不存在";
            } else {
                if (comment == null || comment.isEmpty()) {
                    code = 404;
                    msg = "评论为空";
                } else {
                    Comment comment1 = new Comment();
                    comment1.setUserId(userId);
                    comment1.setBlogId(blogId);
                    comment1.setCommentContent(comment);
                    comment1.setIsSaw("未看过");
                    Comment comment2 = userService.addComment(comment1);
                    if (comment2 == null) {
                        code = 404;
                        msg = "评论添加失败";
                    } else {
                        code = 200;
                        msg = "评论成功";
                    }
                }
            }
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
