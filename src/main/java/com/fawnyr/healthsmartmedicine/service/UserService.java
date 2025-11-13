package com.fawnyr.healthsmartmedicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.model.dto.user.*;
import com.fawnyr.healthsmartmedicine.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fawnyr.healthsmartmedicine.model.vo.user.LoginUserVO;
import com.fawnyr.healthsmartmedicine.model.vo.user.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author wujialu
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-10-29 21:42:54
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 密码加密
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);

    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    LoginUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    /**
     * user转LoginUserVO
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取登录用户
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取用户脱敏数据
     * @param user
     * @return
     */
    UserVO getUserVO(User user);


    /**
     * 用户注销
     * @param request
     * @return
     */
    Boolean userLogout(HttpServletRequest request);

    /**
     * 创建用户（管理员）
     * @param userAddRequest
     * @return
     */
    long addUser(UserAddRequest userAddRequest);

    /**
     * 更新用户
     * @param userUpdateRequest
     * @return
     */
    Boolean updateUser(UserUpdateRequest userUpdateRequest);

    /**
     * 分页获取用户列表（管理员）
     * @param userQueryRequest
     * @return
     */
    Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest);

    /**
     * 构造查询条件
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 获取UserVOList
     * @param userList
     * @return
     */
    List<UserVO> getUserVOList(List<User> userList);

}
