package com.buuble.blog.service.impl;

import com.buuble.blog.config.ImgUploadConfig;
import com.buuble.blog.entity.*;
import com.buuble.blog.entity.Collection;
import com.buuble.blog.repository.*;
import com.buuble.blog.service.BlogService;
import com.buuble.blog.service.BlogtagService;
import com.buuble.blog.utils.FormatUtil;
import com.buuble.blog.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogtagRepository blogtagRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ClassificationRepository classificationRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogtagService blogtagService;

    public Blog addBlog(Blog blog, List<Integer> tagIds) {
        blog.setViews(0);
        Blog blog1 = blogRepository.save(blog);
        List<Blogtag> blogtags = new ArrayList<>();
        for (Integer id : tagIds) {
            Blogtag blogtag = new Blogtag();
            blogtag.setBlogId(blog.getBlogId());
            blogtag.setTagId(id);
            blogtags.add(blogtag);
        }
        if (blog1 != null) {
            List<Blogtag> blogtags1 = blogtagRepository.saveAll(blogtags);
            return blog;
        } else {
            return null;
        }

    }

    public Map<String, Object> showBlog(Integer blogId,Integer userId) {
        Blog blog1 = blogRepository.findByBlogId(blogId);
        if (blog1 != null) {
            blog1.setViews(blog1.getViews() + 1);
            blogRepository.save(blog1);
            Blog blog = blogRepository.findByBlogId(blogId);

            User user = userRepository.findByUserId(blog.getUserId());

            Map<String, Object> result = new HashMap<>();

            Map<String, Object> data = new HashMap<>();

            Map<String, Object> data1 = new HashMap<>();
            Map<String, Object> data2 = new HashMap<>();

            data1.put("userId", user.getUserId());
            data1.put("userName", user.getUsername());
            data1.put("userAvatar", user.getPhoto());

            List<Object> blogTags = blogtagService.getTagsByBlogId(blogId);

            data2.put("blogTitle", blog.getTitle());
            data2.put("blogHtmlContent", blog.getBlogHtmlContent());
            data2.put("blogTags", blogTags);
            data2.put("blogClassification", classificationRepository.findClassificationNameByClassificationId(blog.getClassificationId()));
            data2.put("blogViews", blog.getViews());
            data2.put("blogCommentsCount", commentRepository.countByBlogId(blogId));
            data2.put("blogCollectionsCount", collectionRepository.countByBlogId(blogId));

            Collection collection = collectionRepository.findByBlogIdAndUserId(blogId,userId);
            if(collection!=null){
                data2.put("blogIsCollected",1);
            }else {
                data2.put("blogIsCollected",0);
            }

            data.put("user", data1);
            data.put("blog", data2);
            result.put("data", data);
            return result;
        } else {
            return null;
        }
    }

    public Map<String, Object> getClassificationAndBlog(Integer userId) {
        List<Classification> classificationList = classificationRepository.findByUserId(userId);
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        for (Classification classification : classificationList) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("classId", classification.getClassificationId());
            map1.put("className", classification.getClassificationName());
            List<Object> blogInfo = new ArrayList<>();
            List<Blog> blogList = blogRepository.findByClassificationId(classification.getClassificationId());
            for (Blog blog : blogList) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("blogId", blog.getBlogId());
                map2.put("blogTitle", blog.getTitle());
                blogInfo.add(map2);
            }
            map1.put("blogsInfo", blogInfo);
            data.add(map1);
        }
        map.put("data", data);
        return map;
    }

    public Map<String, Object> getRecentBlogs(Integer userId) {
        List<Blog> blogList = blogRepository.findByUserIdOrderByReleaseTimeDesc(userId);

        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        for (Blog blog : blogList) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("blogId", blog.getBlogId());
            map1.put("blogTitle", blog.getTitle());
            map1.put("blogCollectionsCount", collectionRepository.countByBlogId(blog.getBlogId()));
            map1.put("blogViews", blog.getViews());
            map1.put("blogCommentsCount", commentRepository.countByBlogId(blog.getBlogId()));
            List<Blogtag> blogtagList = blogtagRepository.findByBlogId(blog.getBlogId());
            List<Object> blogtags = new ArrayList<>();
            for (Blogtag blogtag : blogtagList) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("tagId", blogtag.getTagId());
                map2.put("tagName", tagRepository.findByTagId(blogtag.getTagId()).getTagName());
                blogtags.add(map2);
            }
            map1.put("blogTags", blogtags);
            map1.put("userId", userId);
            map1.put("userName", userRepository.findByUserId(userId).getUsername());
            data.add(map1);
        }
        map.put("data", data);

        return map;
    }

    public Map<String, Object> showSomeoneBlogList(Integer userId) {
        List<Blog> blogList = blogRepository.findByUserIdAndIsReleasedOrderByReleaseTimeDesc(userId, "已发布");

        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        for (Blog blog : blogList) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("blogId", blog.getBlogId());
            map1.put("blogTitle", blog.getTitle());
            map1.put("blogCollectionsCount", collectionRepository.countByBlogId(blog.getBlogId()));
            map1.put("blogViews", blog.getViews());
            map1.put("blogCommentsCount", commentRepository.countByBlogId(blog.getBlogId()));
            List<Blogtag> blogtagList = blogtagRepository.findByBlogId(blog.getBlogId());
            List<Object> blogtags = new ArrayList<>();
            for (Blogtag blogtag : blogtagList) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("tagId", blogtag.getTagId());
                map2.put("tagName", tagRepository.findByTagId(blogtag.getTagId()).getTagName());
                blogtags.add(map2);
            }
            map1.put("blogTags", blogtags);
            map1.put("userId", userId);
            map1.put("userName", userRepository.findByUserId(userId).getUsername());
            data.add(map1);
        }
        map.put("data", data);

        return map;
    }

    public boolean deleteBlog(Integer blogId) {
        blogRepository.deleteByBlogId(blogId);
        return blogRepository.findByBlogId(blogId) == null;
    }

    public Map<String, Object> getRandomBlogs() {
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        int a[] = blogRepository.findAllBlogId();
        Random random = new Random();
        Set<Integer> b = new HashSet<>();
        while (b.size() < 8) {
            b.add(a[random.nextInt(a.length)]);
        }
        for (Integer blogId : b) {
            Blog blog = blogRepository.findByBlogId(blogId);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("blogId", blog.getBlogId());
            map1.put("blogTitle", blog.getTitle());
            map1.put("blogCollectionsCount", collectionRepository.countByBlogId(blog.getBlogId()));
            map1.put("blogViews", blog.getViews());
            map1.put("blogCommentsCount", commentRepository.countByBlogId(blog.getBlogId()));
            List<Blogtag> blogtagList = blogtagRepository.findByBlogId(blog.getBlogId());
            List<Object> blogtags = new ArrayList<>();
            for (Blogtag blogtag : blogtagList) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("tagId", blogtag.getTagId());
                map2.put("tagName", tagRepository.findByTagId(blogtag.getTagId()).getTagName());
                blogtags.add(map2);
            }
            map1.put("blogTags", blogtags);
            map1.put("userId", blog.getUserId());
            map1.put("userName", userRepository.findByUserId(blog.getUserId()).getUsername());
            data.add(map1);
        }
        map.put("data", data);
        return map;
    }

}
