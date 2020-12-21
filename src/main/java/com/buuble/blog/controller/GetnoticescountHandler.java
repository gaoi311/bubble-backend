package com.buuble.blog.controller;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Collection;
import com.buuble.blog.entity.Comment;
import com.buuble.blog.entity.User;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GetnoticescountHandler {

    @Autowired
    private UserService userService;

    @GetMapping("/noticescount/{userId}")
    public Map<String, Object> Getnoticescount(@PathVariable("userId") Integer userId) {
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
            Integer noticescount = 0;
            List<Blog> blogs = userService.getBlogbyUserIdAndReleased(userId);
            if (blogs.isEmpty()) {
            } else {
                for (Blog i : blogs) {
                    List<Comment> comments = userService.getCommentbyBlogIdAndNotviewed(i.getBlogId());
                    List<Collection> collections = userService.getCollectionbyAndNotviewed(i.getBlogId());
                    noticescount += comments.size();
                    noticescount += collections.size();
                }
            }
            data.put("noticesCount", noticescount);
            code = 200;
            msg = "获取成功";
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
