package com.buuble.blog.entity;

import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data //lombok中的注解,自动生成getter、setter方法
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id //主键前加此注解
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键自增
    private Integer userId;
    private String phone;
    private String username;
    private String password;
    private String sex;
    private Date age;
    private String photo;

}
