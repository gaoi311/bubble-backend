package com.buuble.blog.service.impl;

import com.buuble.blog.entity.Tag;
import com.buuble.blog.repository.TagRepository;
import com.buuble.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;

    public Map<String, Object> findAllTags() {
        List<Tag> tags = tagRepository.findAll();
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        for (Tag tag : tags) {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("tagId", tag.getTagId());
            map1.put("tagName", tag.getTagName());
            data.add(map1);
        }
        map.put("data", data);

        return map;
    }
}
