package com.buuble.blog.controller;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Collection;
import com.buuble.blog.entity.Comment;
import com.buuble.blog.entity.User;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class GetmyblogdetailsinfoHandler {

    @Autowired
    private UserService userService;

    @GetMapping("/userblogdetailsinfo/{userId}")
    public Map<String, Object> Getmyblogdetailsinfo(@PathVariable("userId") Integer userId) {
        Map<String, Object> status = new LinkedHashMap<>();
        Map<String, Object> val = new LinkedHashMap<>();
        List<Map<String, Object>> data = new LinkedList<>();
        Map<String, Object> data1 = new LinkedHashMap<>();
        Map<String, Object> data2 = new LinkedHashMap<>();
        Map<String, Object> data3 = new LinkedHashMap<>();
        Map<String, Object> data4 = new LinkedHashMap<>();
        val.put("status", status);
        val.put("data", data);
        Integer code;
        String msg;
        User user = userService.checkUserbyUserId(userId);
        if (user == null) {
            code = 404;
            msg = "用户不存在";
        } else {
            data.add(data1);
            data.add(data2);
            data.add(data3);
            data.add(data4);
            data1.put("info", "文章数目");
            data2.put("info", "阅读次数");
            data3.put("info", "评论次数");
            data4.put("info", "收藏次数");
            Integer blogvalue = 0;
            Integer readvalue = 0;
            Integer commentvalue = 0;
            Integer collectionvalue = 0;
            List<Blog> blogs = userService.getBlogbyUserIdAndReleased(userId);
            if (blogs.isEmpty()) {
            } else {
                blogvalue = blogs.size();
                for (Blog i : blogs) {
                    readvalue += i.getViews();
                    List<Comment> comments = userService.getCommentbyBlogId(i.getBlogId());
                    List<Collection> collections = userService.getCollectionbyBlogId(i.getBlogId());
                    commentvalue += comments.size();
                    collectionvalue += collections.size();
                }
            }
            data1.put("value", blogvalue);
            data2.put("value", readvalue);
            data3.put("value", commentvalue);
            data4.put("value", collectionvalue);
            code = 200;
            msg = "获取成功";
        }
        status.put("code", code);
        status.put("msg", msg);
        return val;
    }
}
