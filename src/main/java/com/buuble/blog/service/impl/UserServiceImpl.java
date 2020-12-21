package com.buuble.blog.service.impl;

import com.buuble.blog.entity.*;
import com.buuble.blog.repository.*;
import com.buuble.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private ClassificationRepository classificationRepository;

    @Override
    public Boolean checkPhone(String phone) {
        String phoneregex =
                "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$";
        return phone.matches(phoneregex);
    }

    @Override
    public Boolean checkPassword(String password) {
        String passwordregex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return password.matches(passwordregex);
    }

    @Override
    public Boolean checkUsername(String username) {
        Boolean bool = true;
        if (username.isEmpty() || Character.isDigit(username.charAt(0))) {
            bool = false;
        }
        return bool;
    }

    @Override
    public User checkUserbyUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User checkUserbyPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public User checkUserbyUserId(Integer userid) {
        return userRepository.findByUserId(userid);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Date strTodate(String str) {
        Date date;
        if (str.isEmpty()) date = null;
        else date = Date.valueOf(str);
        return date;
    }

    @Override
    public List<Blog> getBlogbyUserIdAndReleased(Integer userId) {
        return blogRepository.findAllByUserIdAndIsReleased(userId, "已发布");
    }

    @Override
    public List<Comment> getCommentbyBlogId(Integer blogId) {
        return commentRepository.findAllByBlogId(blogId);
    }

    @Override
    public List<Collection> getCollectionbyBlogId(Integer blogId) {
        return collectionRepository.findAllByBlogId(blogId);
    }

//    @Override
//    public String dowPhoto(String netphoto,Integer userid) throws Exception{
//        String localphoto;
//        if (netphoto.isEmpty()) {
//            localphoto =  "userphoto/"+"default.jpg";
//        }else {
//            URL url = new URL(netphoto);
//            URLConnection con = url.openConnection();
//            InputStream is = con.getInputStream();
//            byte[] bs = new byte[1024];
//            int len;
//            String filename = System.getProperty("user.dir") +
//                    "\\src\\main\\resources\\static\\userphoto/" +
//                    userid + ".jpg";   //下载路径及下载图片名称
//            File file = new File(filename);
//            if (file.exists()){
//                file.delete();
//            }
//            FileOutputStream os = new FileOutputStream(file, true);
//            while ((len = is.read(bs)) != -1) {
//                os.write(bs, 0, len);
//            }
//            os.close();
//            is.close();
//            localphoto = "userphoto/" + userid + ".jpg";
//        }
//        return localphoto;
//    }

    @Override
    public String checkUserGender(String usergender) {
        if (usergender.isEmpty()) usergender = null;
        return usergender;
    }

    @Override
    public String dowPhoto(MultipartFile netphoto, Integer userid) throws Exception {
        String localphoto;
        if (netphoto.isEmpty()) {
            localphoto = "userPhotos/" + "default.png";
        } else {
//            FileInputStream is = new FileInputStream((File) netphoto);
//            byte[] bs = new byte[1024];
//            int len;
            String filename = System.getProperty("user.dir") +
                    "\\src\\main\\resources\\static\\userPhotos/" +
                    userid + ".png";   //下载路径及下载图片名称
            File file = new File(filename);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream os = new FileOutputStream(file, true);
//            while ((len = is.read(bs)) != -1) {
//                os.write(bs, 0, len);
//            }
            BufferedOutputStream out = new BufferedOutputStream(os);
            //is.close();
            out.write(netphoto.getBytes());
            out.flush();
            os.close();
            out.close();
            localphoto = "userPhotos/" + userid + ".png";
        }
        return localphoto;
    }

    @Override
    public List<Comment> getCommentbyBlogIdAndNotviewed(Integer blogId) {
        return commentRepository.findAllByBlogIdAndIsSaw(blogId, "未看过");
    }

    @Override
    public List<Collection> getCollectionbyAndNotviewed(Integer blogId) {
        return collectionRepository.findAllByBlogIdAndAndIsSaw(blogId, "未看过");
    }

    @Override
    public Classification addClassification(Classification classification) {
        return classificationRepository.save(classification);
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Collection addCollection(Collection collection) {
        return collectionRepository.save(collection);
    }

    @Override
    public User getUserbyUserId(Integer userid) {
        return userRepository.findByUserId(userid);
    }

    @Override
    public Blog getBlogbyBlogId(Integer blogid) {
        return blogRepository.findByBlogId(blogid);
    }

    @Override
    public Blog checkBlogbyBlogId(Integer blogid) {
        return blogRepository.findByBlogId(blogid);
    }
}
