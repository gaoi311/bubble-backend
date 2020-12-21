package com.buuble.blog.service;

import com.buuble.blog.entity.Picture;
import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface PictureService {
    //保存博客所有图片到picture表
    List<Picture> addPicture(List<Picture> pictures);

    //更新此博客所有图片
    List<Picture> updatePicture(List<Picture> pictures);

    Map<String, Object> uploadImgs(List<MultipartFile> files, Integer blogId);

    String uploadImg(MultipartFile file);
}
