package com.buuble.blog.service;

import com.buuble.blog.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;
import java.util.List;


public interface UserService {

    User checkUserbyPhone(String phone);

    User checkUserbyUsername(String username);

    User checkUserbyUserId(Integer userid);

    Boolean checkPhone(String phone);

    Boolean checkPassword(String password);

    Boolean checkUsername(String username);

    User addUser(User user);

    Date strTodate(String str);

    List<Blog> getBlogbyUserIdAndReleased(Integer userId);

    List<Comment> getCommentbyBlogId(Integer blogId);

    List<Collection> getCollectionbyBlogId(Integer blogId);

    //String dowPhoto(String netphoto,Integer userid) throws Exception;
    String checkUserGender(String usergender);

    String dowPhoto(MultipartFile netphoto, Integer userid) throws Exception;

    List<Comment> getCommentbyBlogIdAndNotviewed(Integer blogId);

    List<Collection> getCollectionbyAndNotviewed(Integer blogId);

    Classification addClassification(Classification classification);

    Comment addComment(Comment comment);

    Collection addCollection(Collection collection);

    User getUserbyUserId(Integer userid);

    Blog getBlogbyBlogId(Integer blogid);

    Blog checkBlogbyBlogId(Integer blogid);
}
