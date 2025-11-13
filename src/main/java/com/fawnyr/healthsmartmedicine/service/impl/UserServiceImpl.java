package com.fawnyr.healthsmartmedicine.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fawnyr.healthsmartmedicine.constant.UserConstant;
import com.fawnyr.healthsmartmedicine.exception.BusinessException;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.mapper.UserMapper;
import com.fawnyr.healthsmartmedicine.model.dto.user.*;
import com.fawnyr.healthsmartmedicine.model.entity.Medicine;
import com.fawnyr.healthsmartmedicine.model.entity.User;
import com.fawnyr.healthsmartmedicine.model.vo.user.LoginUserVO;
import com.fawnyr.healthsmartmedicine.model.vo.user.UserVO;
import com.fawnyr.healthsmartmedicine.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.fawnyr.healthsmartmedicine.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author wujialu
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-10-29 21:42:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    /**
     * 用户注册
     * @param userRegisterRequest
     * @return
     */
    @Override
    public long userRegister(UserRegisterRequest userRegisterRequest){
        //1.校验数据
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StrUtil.hasBlank(userAccount,userPassword,checkPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        if (userAccount.length()<4)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        if (userPassword.length()<8)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        if (!userPassword.equals(checkPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次输入密码不一致");
        //2.判断数据库是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        Long count = this.baseMapper.selectCount(queryWrapper);
        if(count>0)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复");
        //3.密码加密
        String encryptPassword = getEncryptPassword(userPassword);
        //4.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserName("无名");
        user.setUserPassword(encryptPassword);
        user.setUserRole(UserConstant.DEFAULT_ROLE);
        user.setImgPath("https://img0.baidu.com/it/u=2797888697,3452298248&fm=253&fmt=auto&app=138&f=JPEG?w=760&h=760");
        boolean result = this.save(user);
        if(!result)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"注册失败");
        //5.返回id
        return user.getId();
    }

    /**
     * 密码加密
     * @param userPassword
     * @return
     */
    @Override
    public String getEncryptPassword(String userPassword) {
        //加盐，混淆密码
        final String SALT = "Fawnyr";
        return DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @return
     */
    @Override
    public LoginUserVO userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request){
        //1.校验数据
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StrUtil.hasBlank(userAccount,userPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        //2.密码加密
        String encryptPassword = getEncryptPassword(userPassword);
        //3.数据库判断
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if(user==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码错误");
        //4.记录用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE,user);
        //5.返回结果
        return getLoginUserVO(user);
    }

    /**
     * user转LoginUserVO
     * @param user
     * @return
     */
    @Override
    public LoginUserVO getLoginUserVO(User user){
        if(user==null)
            return null;
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user,loginUserVO);
        return loginUserVO;
    }

    /**
     * 获取登录用户
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        //1.从登录状态获取用户
        Object objUser = request.getSession().getAttribute(USER_LOGIN_STATE);
        User curUser = (User)objUser;
        if(curUser==null)
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        //2.查询数据库
        User user = this.getById(curUser.getId());
        if(user==null)
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        return user;
    }

    @Override
    public UserVO getUserVO(User user) {
        if(user==null)
            return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public Boolean userLogout(HttpServletRequest request) {
        //1.判断是否登录
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        if(object==null)
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"未登录");
        //2.删除用户登录状态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        //3.返回结果
        return true;
    }

    /**
     * 创建用户
     * @param userAddRequest
     * @return
     */
    @Override
    public long addUser(UserAddRequest userAddRequest) {
        //1.转成user
        User user = new User();
        BeanUtils.copyProperties(userAddRequest,user);
        String userName = user.getUserName();
        String userPassword = user.getUserPassword();
        String userRole = user.getUserRole();
        String imgPath = user.getImgPath();
        if(ObjectUtil.isNotEmpty(userName))
            user.setUserName("无名");
        if(ObjectUtil.isNotEmpty(userPassword)){
            String encryptPassword = getEncryptPassword("12345678");
            user.setUserPassword(encryptPassword);
        }
        if(ObjectUtil.isNotEmpty(userRole))
            user.setUserRole(UserConstant.DEFAULT_ROLE);
        if(ObjectUtil.isNotEmpty(imgPath))
            user.setImgPath("https://img0.baidu.com/it/u=2797888697,3452298248&fm=253&fmt=auto&app=138&f=JPEG?w=760&h=760");
        //2.加入数据库
        boolean result = this.save(user);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        //3.返回结果
        return user.getId();
    }

    /**
     * 更新用户
     * @param userUpdateRequest
     * @return
     */
    @Override
    public Boolean updateUser(UserUpdateRequest userUpdateRequest) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest,user);
        Long id = user.getId();
        User byId = this.getById(id);
        ThrowUtils.throwIf(byId==null, ErrorCode.PARAMS_ERROR,"数据不存在");
        boolean result = this.updateById(user);
        ThrowUtils.throwIf(!result,ErrorCode.OPERATION_ERROR);
        return true;
    }

    /**
     * 分页获取用户列表（管理员）
     * @param userQueryRequest
     * @return
     */
    @Override
    public Page<UserVO> listUserVOByPage(UserQueryRequest userQueryRequest) {
        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = this.page(new Page<>(current, pageSize), this.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = this.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return userVOPage;
    }

    /**
     * 构造查询条件
     * @return
     */
    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if(userQueryRequest==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        Integer userAge = userQueryRequest.getUserAge();
        String userSex = userQueryRequest.getUserSex();
        String userEmail = userQueryRequest.getUserEmail();
        String userTel = userQueryRequest.getUserTel();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id!=0,"id",id);
        queryWrapper.eq(userAge!=0,"userAge",userAge);
        queryWrapper.eq(ObjectUtil.isNotEmpty(userRole),"userRole",userRole);
        queryWrapper.eq(ObjectUtil.isNotEmpty(userSex),"userSex",userSex);
        queryWrapper.like(ObjectUtil.isNotEmpty(userAccount),"userAccount",userAccount);
        queryWrapper.like(ObjectUtil.isNotEmpty(userName),"userName",userName);
        queryWrapper.like(ObjectUtil.isNotEmpty(userEmail),"userEmail",userEmail);
        queryWrapper.like(ObjectUtil.isNotEmpty(userTel),"userTel",userTel);
        queryWrapper.orderBy(ObjectUtil.isNotEmpty(sortOrder),sortOrder.equals("ascend"),sortField);
        return queryWrapper;
    }

    /**
     * 获取UserVOList
     * @param userList
     * @return
     */
    @Override
    public List<UserVO> getUserVOList(List<User> userList) {
        if(userList==null)
            return new ArrayList<>();
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }
}




