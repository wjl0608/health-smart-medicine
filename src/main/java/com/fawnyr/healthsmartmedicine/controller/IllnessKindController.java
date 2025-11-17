package com.fawnyr.healthsmartmedicine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.annotation.AuthCheck;
import com.fawnyr.healthsmartmedicine.common.BaseResponse;
import com.fawnyr.healthsmartmedicine.common.DeleteRequest;
import com.fawnyr.healthsmartmedicine.common.ResultUtils;
import com.fawnyr.healthsmartmedicine.constant.UserConstant;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessKind;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessKindVO;
import com.fawnyr.healthsmartmedicine.service.IllnessKindService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/illnessKind")
public class IllnessKindController {
    @Resource
    IllnessKindService illnessKindService;
    /**
     * 新增分类（管理员）
     * @param illnessKindAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addIllnessKind(@RequestBody IllnessKindAddRequest illnessKindAddRequest){
        ThrowUtils.throwIf(illnessKindAddRequest==null, ErrorCode.PARAMS_ERROR);
        Long result = illnessKindService.addIllnessKind(illnessKindAddRequest);
        return ResultUtils.success(result);
    }

    /**
     * 根据id删除分类（管理员）
     * @param deleteRequest
     * @return
     */
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteIllnessKind(@RequestBody DeleteRequest deleteRequest){
        ThrowUtils.throwIf(deleteRequest==null, ErrorCode.PARAMS_ERROR);
        boolean result = illnessKindService.removeById(deleteRequest.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 根据id批量删除分类（管理员）
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/ids")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteListIllnessKind(@RequestParam List<Serializable> ids){
        ThrowUtils.throwIf(ids==null, ErrorCode.PARAMS_ERROR);
        boolean result = illnessKindService.removeByIds(ids);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 修改分类（管理员）
     * @param illnessKindUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateIllnessKind(@RequestBody IllnessKindUpdateRequest illnessKindUpdateRequest){
        ThrowUtils.throwIf(illnessKindUpdateRequest==null, ErrorCode.PARAMS_ERROR);
        Boolean result = illnessKindService.updateIllnessKind(illnessKindUpdateRequest);
        return ResultUtils.success(result);
    }

    //根据id查询分类（管理员）
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<IllnessKind> getIllnessKind(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        IllnessKind illnessKind = illnessKindService.getById(id);
        ThrowUtils.throwIf(illnessKind==null,ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(illnessKind);
    }

    //根据id查询分类
    @GetMapping("/get/vo")
    public BaseResponse<IllnessKindVO> getIllnessKindVO(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        IllnessKind illnessKind = getIllnessKind(id).getData();
        IllnessKindVO illnessKindVO = illnessKindService.getIllnessKindVO(illnessKind);
        return ResultUtils.success(illnessKindVO);
    }

    /**
     * 分页查询分类
     * @param illnessKindQueryRequest
     * @return
     */
    @PostMapping("/page")
    public BaseResponse<Page<IllnessKindVO>> listIllnessKindVOByPage(@RequestBody IllnessKindQueryRequest illnessKindQueryRequest) {
        ThrowUtils.throwIf(illnessKindQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<IllnessKindVO> page = illnessKindService.listIllnessKindVOByPage(illnessKindQueryRequest);
        return ResultUtils.success(page);
    }

}
