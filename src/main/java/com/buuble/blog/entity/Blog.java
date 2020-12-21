package com.buuble.blog.entity;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键自增
    private Integer blogId;
    private Integer userId;
    private Integer classificationId;
    private String title;
    private String blogContent;


    @LastModifiedDate
    private Timestamp releaseTime;
    private Integer views;
    private String isReleased;
    private String blogHtmlContent;
}
