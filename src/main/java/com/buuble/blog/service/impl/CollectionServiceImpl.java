package com.buuble.blog.service.impl;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Blogtag;
import com.buuble.blog.entity.Collection;
import com.buuble.blog.entity.Comment;
import com.buuble.blog.repository.*;
import com.buuble.blog.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BlogtagRepository blogtagRepository;

    @Autowired
    TagRepository tagRepository;

    public Collection addCollection(Integer userId, Integer blogId) {
        Collection collection = new Collection();
        collection.setUserId(userId);
        collection.setBlogId(blogId);
        collection.setIsSaw("未看过");
        return collectionRepository.save(collection);
    }

    public boolean deleteCollection(Integer userId, Integer blogId) {
        collectionRepository.deleteByBlogIdAndUserId(blogId, userId);
        Collection collection = collectionRepository.findByBlogIdAndUserId(blogId, userId);
        return collection == null;
    }

    public Map<String, Object> getMyCollection(Integer userId) {
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        List<Integer> blogIds = collectionRepository.findBlogIdByUserId(userId);
        for (Integer blogId : blogIds) {
            Blog blog = blogRepository.findByBlogId(blogId);
            Map<String, Object> map1 = new HashMap<>();
            map1.put("blogId", blogId);
            map1.put("blogTitle", blog.getTitle());
            map1.put("blogCollectionsCount", collectionRepository.countByBlogId(blogId));
            map1.put("blogViews", blog.getViews());
            map1.put("blogCommentsCount", commentRepository.countByBlogId(blogId));
            map1.put("userId", blog.getUserId());
            map1.put("userName", userRepository.findByUserId(userId).getUsername());
            List<Object> blogtags = new ArrayList<>();
            for (Blogtag blogtag : blogtagRepository.findByBlogId(blogId)) {
                Map<String, Object> map2 = new HashMap<>();
                map2.put("tagId", blogtag.getTagId());
                map2.put("tagName", tagRepository.findByTagId(blogtag.getTagId()).getTagName());
                blogtags.add(map2);
            }
            map1.put("blogTags", blogtags);
            data.add(map1);
        }

        map.put("data", data);
        return map;
    }
}
