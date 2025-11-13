package com.fawnyr.healthsmartmedicine.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserAddRequest implements Serializable {
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

    private static final long serialVersionUID = 1L;
}
