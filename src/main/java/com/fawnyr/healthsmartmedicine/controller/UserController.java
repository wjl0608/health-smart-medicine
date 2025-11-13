package com.fawnyr.healthsmartmedicine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.annotation.AuthCheck;
import com.fawnyr.healthsmartmedicine.common.BaseResponse;
import com.fawnyr.healthsmartmedicine.common.DeleteRequest;
import com.fawnyr.healthsmartmedicine.common.ResultUtils;
import com.fawnyr.healthsmartmedicine.constant.UserConstant;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.user.*;
import com.fawnyr.healthsmartmedicine.model.entity.User;
import com.fawnyr.healthsmartmedicine.model.vo.user.LoginUserVO;
import com.fawnyr.healthsmartmedicine.model.vo.user.UserVO;
import com.fawnyr.healthsmartmedicine.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        //1.检验请求是否为null
        ThrowUtils.throwIf(userRegisterRequest==null, ErrorCode.PARAMS_ERROR);
        //2.注册
        long result = userService.userRegister(userRegisterRequest);
        //3.返回数据库id
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        //1.检验请求是否为null
        ThrowUtils.throwIf(userLoginRequest==null, ErrorCode.PARAMS_ERROR);
        //2.注册
        LoginUserVO loginUserVO = userService.userLogin(userLoginRequest,request);
        //3.返回数据库id
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    @PostMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(loginUser));
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request){
        Boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 创建用户（管理员）
     * @param userAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest){
        ThrowUtils.throwIf(userAddRequest==null, ErrorCode.PARAMS_ERROR);
        Long result = userService.addUser(userAddRequest);
        return ResultUtils.success(result);
    }

    //根据id获取用户（管理员）
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<User> getUser(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user==null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    //根据id获取用户vo
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVO(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        User user = getUser(id).getData();
        UserVO userVO = userService.getUserVO(user);
        return ResultUtils.success(userVO);
    }

    //删除用户（管理员）
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest){
        ThrowUtils.throwIf(deleteRequest==null, ErrorCode.PARAMS_ERROR);
        Boolean result = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    //更新用户（管理员）
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        ThrowUtils.throwIf(userUpdateRequest == null, ErrorCode.PARAMS_ERROR);
        Boolean result = userService.updateUser(userUpdateRequest);
        return ResultUtils.success(result);
    }

    /**
     * 分页获取用户列表（管理员）
     * @param userQueryRequest
     * @return
     */
    @PostMapping("/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<UserVO> page = userService.listUserVOByPage(userQueryRequest);
        return ResultUtils.success(page);
    }

}
