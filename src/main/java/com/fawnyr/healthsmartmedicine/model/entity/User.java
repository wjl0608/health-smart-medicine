package com.fawnyr.healthsmartmedicine.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;


/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 名字
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 年龄
     */
    private Integer userAge;

    /**
     * 性别：0男 1女
     */
    private String userSex;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 手机号
     */
    private String userTel;

    /**
     * 用户角色：1管理员，0普通用户
     */
    private String userRole;

    /**
     * 头像
     */
    private String imgPath;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}