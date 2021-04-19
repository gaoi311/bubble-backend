package com.buuble.blog.controller;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Blogtag;
import com.buuble.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BlogHandler {
    @Autowired
    private BlogService blogService;

    @PostMapping("/blog/{blogId}")  //获取博客详情
    public Map<String, Object> getBlog(@PathVariable("blogId") Integer blogId, @RequestParam("userId") Integer userId) {
        Map<String, Object> map = blogService.showBlog(blogId, userId);
        Map<String, Object> status = new HashMap<>();
        if (map != null) {
            status.put("code", 200);
            status.put("msg", "获取成功");
            map.put("status", status);
            return map;
        } else {
            status.put("code", 404);
            status.put("msg", "获取失败");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("status", status);
            return map1;
        }
    }

    @GetMapping("/classifications/{userId}")  //获取分类及相应文章信息
    public Map<String, Object> getClassificationAndBlog(@PathVariable("userId") Integer userId) {
        Map<String, Object> status = new HashMap<>();
        try {
            Map<String, Object> map = blogService.getClassificationAndBlog(userId);
            if (map != null) {
                status.put("code", 200);
                status.put("msg", "获取成功");
                map.put("status", status);
                return map;
            } else {
                status.put("code", 404);
                status.put("msg", "获取失败");
                Map<String, Object> map1 = new HashMap<>();
                map1.put("status", status);
                return map1;
            }
        }catch (Exception e){
            status.put("code", 404);
            status.put("msg", "获取失败");
            Map<String, Object> map2 = new HashMap<>();
            map2.put("status", status);
            return map2;
        }
    }

    @GetMapping("/recentblogs/{userId}") //获取当前用户最近的博客列表
    public Map<String, Object> getRecentBlogs(@PathVariable("userId") Integer userId) {
        Map<String, Object> status = new HashMap<>();
        try {
            Map<String, Object> map = blogService.getRecentBlogs(userId);
            if (map != null) {
                status.put("code", 200);
                status.put("msg", "获取成功");
                map.put("status", status);
                return map;
            } else {
                status.put("code", 200);
                status.put("msg", "获取成功");
                Map<String, Object> map1 = new HashMap<>();
                map1.put("status", status);
                return map1;
            }
        }catch (Exception e){
            status.put("code", 404);
            status.put("msg", "获取失败");
            Map<String, Object> map2 = new HashMap<>();
            map2.put("status", status);
            return map2;
        }
    }

    @GetMapping("/blogsofauthor/{userId}") //获取某用户的所有博客
    public Map<String, Object> getBlogsByAthor(@PathVariable("userId") Integer userId) {
        Map<String, Object> map = blogService.showSomeoneBlogList(userId);
        Map<String, Object> status = new HashMap<>();
        if (map != null) {
            status.put("code", 200);
            status.put("msg", "获取成功");
            map.put("status", status);
            return map;
        } else {
            status.put("code", 404);
            status.put("msg", "获取失败");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("status", status);
            return map1;
        }
    }

    @DeleteMapping("/blog/{blogId}")  //删除博客
    public Map<String, Object> deleteCollection(@PathVariable("blogId") Integer blogId) {
        boolean flag = blogService.deleteBlog(blogId);
        Map<String, Object> status = new HashMap<>();
        if (flag) {
            status.put("code", 200);
            status.put("msg", "删除成功");
        } else {
            status.put("code", 404);
            status.put("msg", "删除失败");
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("status", status);
        return map1;
    }

    @PostMapping("/blog")  //添加博客
    public Map<String, Object> addBlog(@RequestParam("userId") Integer userId,
                                       @RequestParam("blogId") Integer blogId,
                                       @RequestParam("blogTags") List<Integer> tagsId,
                                       @RequestParam("blogClassification") Integer classificationId,
                                       @RequestParam("mdContent") String blogContent,
                                       @RequestParam("htmlContent") String blogHtmlContent,
                                       @RequestParam("blogTitle") String title,
                                       @RequestParam("flag") Integer flag) {
        System.out.println(blogHtmlContent);

        Blog blog = new Blog();
        blog.setUserId(userId);
        blog.setBlogId(blogId);
        blog.setTitle(title);
        blog.setClassificationId(classificationId);
        blog.setBlogContent(blogContent);
        blog.setBlogHtmlContent(blogHtmlContent);
        if (flag == 0)
            blog.setIsReleased("未发布");
        else if (flag == 1)
            blog.setIsReleased("已发布");
        Blog blog1 = blogService.addBlog(blog, tagsId);
        Map<String, Object> status = new HashMap<>();
        if (blog1 != null) {
            status.put("code", 200);
            status.put("msg", "保存成功");
        } else {
            status.put("code", 404);
            status.put("msg", "保存失败");
        }
        Map<String, Object> map1 = new HashMap<>();
        map1.put("status", status);
        return map1;
    }

    @GetMapping("/randomblogs")
    public Map<String,Object> getRandomBlogs(){
        System.out.print(123);
        Map<String,Object> map = blogService.getRandomBlogs();
        Map<String,Object> status = new HashMap<>();
        if (map!=null){
            status.put("code",200);
            status.put("msg","获取成功");
            map.put("status",status);
            return map;
        }else {
            status.put("code",404);
            status.put("msg","获取失败");
            Map<String,Object> map1 = new HashMap<>();
            map1.put("status",status);
            return map1;
        }
    }
}
