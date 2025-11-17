package com.fawnyr.healthsmartmedicine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.annotation.AuthCheck;
import com.fawnyr.healthsmartmedicine.common.BaseResponse;
import com.fawnyr.healthsmartmedicine.common.DeleteRequest;
import com.fawnyr.healthsmartmedicine.common.ResultUtils;
import com.fawnyr.healthsmartmedicine.constant.UserConstant;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.Illness;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessVO;
import com.fawnyr.healthsmartmedicine.service.IllnessService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/illness")
public class IllnessController {
    @Resource
    private IllnessService illnessService;
    /**
     * 新增疾病（管理员）
     * @param illnessAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addIllness(@RequestBody IllnessAddRequest illnessAddRequest){
        ThrowUtils.throwIf(illnessAddRequest==null, ErrorCode.PARAMS_ERROR);
        Long result = illnessService.addIllness(illnessAddRequest);
        return ResultUtils.success(result);
    }

    /**
     * 根据主键ID删除疾病（管理员）
     * @param deleteRequest
     * @return
     */
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteIllness(@RequestBody DeleteRequest deleteRequest){
        ThrowUtils.throwIf(deleteRequest==null, ErrorCode.PARAMS_ERROR);
        boolean result = illnessService.removeById(deleteRequest.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 根据主键ID批量删除疾病（管理员）
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/ids")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteListIllness(@RequestParam List<Long> ids){
        ThrowUtils.throwIf(ids==null, ErrorCode.PARAMS_ERROR);
        boolean result = illnessService.removeByIds(ids);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 根据主键ID修改疾病（管理员）
     * @param illnessUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateIllness(@RequestBody IllnessUpdateRequest illnessUpdateRequest){
        ThrowUtils.throwIf(illnessUpdateRequest==null, ErrorCode.PARAMS_ERROR);
        boolean result = illnessService.updateIllness(illnessUpdateRequest);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 根据主键ID查询疾病（管理员）
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Illness> getIllness(Long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        Illness illness = illnessService.getById(id);
        ThrowUtils.throwIf(illness==null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(illness);
    }

    /**
     * 根据主键ID查询疾病
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<IllnessVO> getIllnessVO(Long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        Illness illness = this.getIllness(id).getData();
        ThrowUtils.throwIf(illness==null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(illnessService.getIllnessVO(illness));
    }

    //分页查询疾病
    @PostMapping("/page")
    public BaseResponse<Page<IllnessVO>> listIllnessVOByPage(@RequestBody IllnessQueryRequest illnessQueryRequest) {
        ThrowUtils.throwIf(illnessQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<IllnessVO> page = illnessService.listIllnessVOByPage(illnessQueryRequest);
        return ResultUtils.success(page);
    }
}
