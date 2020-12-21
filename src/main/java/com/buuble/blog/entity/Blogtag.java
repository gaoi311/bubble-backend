package com.buuble.blog.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Blogtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示主键自增
    private Integer blogtagId;
    private Integer blogId;
    private Integer tagId;
}
