package com.buuble.blog.service.impl;

import com.buuble.blog.entity.*;
import com.buuble.blog.repository.*;
import com.buuble.blog.service.BlogtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogtagServiceImpl implements BlogtagService {
    @Autowired
    BlogtagRepository blogtagRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;
    @Autowired
    BlogRepository blogRepository;

    //  public List<Blogtag> addBlogtags(List<Blogtag> blogtags){

    //  }
    public Map<String, Object> getBlogsByTag(Integer tagId) { //获取某标签下的所有博客

        List<Blogtag> blogtagList = blogtagRepository.findByTagId(tagId);

        List<Blog> blogList = new ArrayList<>();
        for (Blogtag blogtag : blogtagList) {
            blogList.add(blogRepository.findByBlogId(blogtag.getBlogId()));
        }
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        for (Blog blog : blogList) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("blogId", blog.getBlogId());
            map1.put("blogTitle", blog.getTitle());
            map1.put("blogCollectionsCount", collectionRepository.countByBlogId(blog.getBlogId()));
            map1.put("blogViews", blog.getViews());
            map1.put("blogCommentsCount", commentRepository.countByBlogId(blog.getBlogId()));
            List<Blogtag> blogtagList1 = blogtagRepository.findByBlogId(blog.getBlogId());
            List<Object> blogtags = new ArrayList<>();
            for (Blogtag blogtag : blogtagList1) {
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

    public List<Object> getTagsByBlogId(Integer blogId){
        List<Object> list = new ArrayList<>();
        List<Integer> tagIds = blogtagRepository.findTagIdByBlogId(blogId);
        for(Integer value : tagIds){
            Map<String,Object> map = new HashMap<>();
            Tag tag = tagRepository.findByTagId(value);
            map.put("tagId",tag.getTagId());
            map.put("tagName",tag.getTagName());
            list.add(map);
        }

        return list;
    }
}
