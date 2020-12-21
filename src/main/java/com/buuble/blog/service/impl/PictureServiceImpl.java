package com.buuble.blog.service.impl;

import com.buuble.blog.config.ImgUploadConfig;
import com.buuble.blog.entity.Blog;
import com.buuble.blog.entity.Picture;
import com.buuble.blog.repository.BlogRepository;
import com.buuble.blog.repository.PictureRepository;
import com.buuble.blog.service.PictureService;
import com.buuble.blog.utils.FormatUtil;
import com.buuble.blog.utils.UUIDUtil;
import com.buuble.blog.utils.UploadUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UUIDUtil uuidUtil;

    @Autowired
    FormatUtil formatUtil;

    @Autowired
    ImgUploadConfig imgUploadConfig;

    public List<Picture> addPicture(List<Picture> pictures) {

        return pictureRepository.saveAll(pictures);
    }

    public List<Picture> updatePicture(List<Picture> pictures) {
        pictureRepository.deleteInBatch(pictureRepository.findByBlogId(pictures.get(0).getBlogId()));
        return pictureRepository.saveAll(pictures);
    }

    public Map<String, Object> uploadImgs(List<MultipartFile> files, Integer blogId) {
        UploadUtil uploadUtil = new UploadUtil();
        // MultipartFile file = uploadUtil.getMulFileByPath("C:\\Users\\裴绍展\\新建文件夹\\打包文件/5a5f001eN48800a0d.jpg");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        List<Object> lists = new ArrayList<>();
        if (blogId == 0) {
            Blog blog = new Blog();
            blogId = blogRepository.save(blog).getBlogId();
        }
        if (blogRepository.findByBlogId(blogId) != null) {
            data.put("blogId", blogId);
            for (int i = 0; i < files.size(); i++) {
                List<Object> list = new ArrayList<>();
                String url = uploadImg(files.get(i));
                list.add(i + 1);
                list.add(url);
                lists.add(list);
                Picture picture = new Picture();
                picture.setLocation(i + 1);
                picture.setBlogId(blogId);
                picture.setUrl(url);
                pictureRepository.save(picture);
            }
            data.put("blogPictures", lists);
        }
        map.put("data", data);
        return map;

    }

    public synchronized String uploadImg(MultipartFile file) {  //上传图片文件到文件夹
        //获取图片格式/后缀
        String format = formatUtil.getFileFormat(file.getOriginalFilename());
        //获取图片保存路径
        String savePath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\blogImages";

        //保存图片
        String fileName = uuidUtil.generateUUID() + format;
        File diskFile = new File(savePath + "/" + fileName);
        try {
            file.transferTo(diskFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将硬盘路径转换为url
        return imgUploadConfig.getStaticAccessPath().replaceAll("\\*", "") + fileName;

    }

}
