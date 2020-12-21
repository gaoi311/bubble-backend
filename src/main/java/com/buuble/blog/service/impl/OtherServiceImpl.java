package com.buuble.blog.service.impl;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Collection;
import com.buuble.blog.entity.Tag;
import com.buuble.blog.entity.User;
import com.buuble.blog.repository.*;
import com.buuble.blog.service.BlogtagService;
import com.buuble.blog.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OtherServiceImpl implements OtherService {
    @Autowired
    TagRepository tagRepository;

    @Autowired
    BlogtagRepository blogtagRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BlogtagService blogtagService;

    public Map<String, Object> getHotTags() {
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        int len = (int) tagRepository.count();
        int[] a = new int[len];
        for (int i = 0; i < len; i++) {
            a[i] = blogtagRepository.countByTagId(i + 1);
        }
        int[] b = new int[10];
        for (int i = 0; i < b.length; i++) {
            int max_index = -1;
            int max = 0;
            for (int j = 0; j < a.length; j++) {
                if (a[j] > max) {
                    max = a[j];
                    max_index = j;
                }
            }
            b[i] = max_index;
            a[max_index] = -1;
        }
        for (int value : b) {
            Map<String, Object> map1 = new HashMap<>();

            map1.put("tagId", value + 1);
            map1.put("tagName", tagRepository.findByTagId(value + 1).getTagName());
            data.add(map1);
        }

        map.put("data", data);
        return map;
    }

    //获取用户所有博客被收藏总量
    public int getUserCollectione(int userId) {
        List<Blog> blogs = blogRepository.findByUserId(userId);
        int sum = 0;
        for (Blog blog : blogs) {
            sum += collectionRepository.countByBlogId(blog.getBlogId());
        }
        return sum;
    }

    //获取用户所有博客被浏览总量
    public int getUserView(int userId) {
        List<Blog> blogs = blogRepository.findByUserId(userId);
        int sum = 0;
        for (Blog blog : blogs) {
            sum += blog.getViews();
        }
        return sum;
    }

    //获取推荐作者列表
    public Map<String, Object> getHotAuthor() {
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        List<User> users = userRepository.findAll();
        int n = users.size();
        Map<Integer, Integer> collectionedSum = new HashMap<>();

        for (User user : users) {
            collectionedSum.put(user.getUserId(), getUserCollectione(user.getUserId()));
        }
        int a[] = new int[3];
        for (int i = 0; i < a.length; i++) {
            int maxValue = -1;
            for (Map.Entry<Integer, Integer> map1 : collectionedSum.entrySet()) {
                if (maxValue < map1.getValue()) {
                    maxValue = map1.getValue();
                    map1.setValue(-1);
                    a[i] = map1.getKey();
                }
            }
        }

        for (int value : a) {
            Map<String, Object> map2 = new HashMap<>();
            User user = userRepository.findByUserId(value);
            map2.put("userId", user.getUserId());
            map2.put("userName", user.getUsername());
            map2.put("userAvatar", user.getPhoto());
            map2.put("userViews", getUserView(user.getUserId()));
            map2.put("userCollectionsCount", getUserCollectione(user.getUserId()));
            data.add(map2);
        }

        map.put("data", data);
        return map;
    }

    public Map<String, Object> search(String keyword) {
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();

        List<Blog> blogs = blogRepository.findByTitleLikeOrBlogContentLike("%" + keyword + "%", "%" + keyword + "%");
        for (Blog blog : blogs) {
            if (blog.getIsReleased().equals("已发布")) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("blogId", blog.getBlogId());
                map2.put("blogTitle", blog.getTitle());
                map2.put("blogCollectionsCount", collectionRepository.countByBlogId(blog.getBlogId()));
                map2.put("blogViews", blog.getViews());
                map2.put("blogCommentsCount", commentRepository.countByBlogId(blog.getBlogId()));
                map2.put("blogTags", blogtagService.getTagsByBlogId(blog.getBlogId()));
                map2.put("userId", blog.getBlogId());
                map2.put("userName", userRepository.findByUserId(blog.getUserId()).getUsername());
                data.add(map2);
            }
        }
        map.put("data", data);
        return map;
    }
}
