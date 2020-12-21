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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class GetunsawnoticesHandler {

    @Autowired
    private UserService userService;

    @GetMapping("/unsawnotices/{userId}")
    public Map<String, Object> Getunsawnotices(@PathVariable("userId") Integer userId) {
        Map<String, Object> status = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> val = new LinkedHashMap<>();
        List<Map<String, Object>> blogCollectionNotices = new LinkedList<>();
        List<Map<String, Object>> blogCommentNotices = new LinkedList<>();
        val.put("status", status);
        val.put("data", data);
        data.put("blogCollectionNotices", blogCollectionNotices);
        data.put("blogCommentNotices", blogCommentNotices);
        Integer code;
        String msg;
        User user = userService.checkUserbyUserId(userId);
        if (user == null) {
            code = 404;
            msg = "用户不存在";
        } else {
            List<Blog> blogs = userService.getBlogbyUserIdAndReleased(userId);
            if (blogs.isEmpty()) {
            } else {
                for (Blog i : blogs) {
                    Map<String, Object> blogdata = new LinkedHashMap<>();
                    blogdata.put("blogId", i.getBlogId());
                    blogdata.put("blogTitle", i.getTitle());
                    List<Comment> comments = userService.getCommentbyBlogIdAndNotviewed(i.getBlogId());
                    List<Collection> collections = userService.getCollectionbyAndNotviewed(i.getBlogId());
                    for (Collection j : collections) {
                        Map<String, Object> userdata = new LinkedHashMap<>();
                        User user1 = userService.getUserbyUserId(j.getUserId());
                        userdata.put("userId", user1.getUserId());
                        userdata.put("userName", user1.getUsername());
                        userdata.put("userAvatar", user1.getPhoto());
                        Map<String, Object> Notices = new LinkedHashMap<>();
                        Notices.put("user", userdata);
                        Notices.put("blog", blogdata);
                        blogCollectionNotices.add(Notices);
                        j.setIsSaw("已看过");
                        userService.addCollection(j);
                    }
                    for (Comment j : comments) {
                        Map<String, Object> userdata = new LinkedHashMap<>();
                        User user1 = userService.getUserbyUserId(j.getUserId());
                        userdata.put("userId", user1.getUserId());
                        userdata.put("userName", user1.getUsername());
                        userdata.put("userAvatar", user1.getPhoto());
                        Map<String, Object> Notices = new LinkedHashMap<>();
                        Notices.put("user", userdata);
                        Notices.put("blog", blogdata);
                        Notices.put("comment", j.getCommentContent());
                        blogCommentNotices.add(Notices);
                        j.setIsSaw("已看过");
                        userService.addComment(j);
                    }
                }
            }
            code = 200;
            msg = "获取成功";
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
