package com.fawnyr.healthsmartmedicine.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class LoginUserVO implements Serializable {
    /**
     * id
     */
    @TableId(type = ASSIGN_ID)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}