package com.buuble.blog.controller;

import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.User;
import com.buuble.blog.repository.BlogRepository;
import com.buuble.blog.repository.UserRepository;
import com.buuble.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.PseudoColumnUsage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PictureHandler {
    @Autowired
    PictureService pictureService;

    @Autowired
    UserRepository userRepository;

    @PutMapping("/blogimage/{userId}")
    public Map<String,Object> addPictures(@PathVariable Integer userId, @RequestParam("userAvatar") MultipartFile userAvatar) {
        System.out.println(111);
//        System.out.println(file.getSize());
        String url = pictureService.uploadImg(userAvatar);
        System.out.println(url);
        Map<String, Object> status = new HashMap<>();
        if (url != null) {
            status.put("code", 200);
            status.put("msg", "上传成功");

        } else {
            status.put("code", 404);
            status.put("msg", "上传失败");
        }
        return status;
    }
    //添加博客图片
    @PostMapping("/blogimages")
    public Map<String, Object> addPictures(@RequestParam("pos") MultipartFile[] pos,
                                           @RequestParam("blogId") Integer blogId) {
        Map<String, Object> map = pictureService.uploadImgs(pos, blogId);
        for(MultipartFile multipartFile : pos){
            System.out.println(multipartFile.getOriginalFilename());
        }
        Map<String, Object> status = new HashMap<>();
        if (map != null) {
            status.put("code", 200);
            status.put("msg", "上传成功");
            map.put("status", status);
            return map;
        } else {
            status.put("code", 404);
            status.put("msg", "上传失败");
            Map<String, Object> map1 = new HashMap<>();
            map1.put("status", status);
            return map1;
        }
//        return new HashMap<>();
    }
}
