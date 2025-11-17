package com.fawnyr.healthsmartmedicine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.annotation.AuthCheck;
import com.fawnyr.healthsmartmedicine.common.BaseResponse;
import com.fawnyr.healthsmartmedicine.common.DeleteRequest;
import com.fawnyr.healthsmartmedicine.common.ResultUtils;
import com.fawnyr.healthsmartmedicine.constant.UserConstant;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessMedicine;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessMedicineVO;
import com.fawnyr.healthsmartmedicine.service.IllnessMedicineService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/illnessMedicine")
public class IllnessMedicineController {
    @Resource
    IllnessMedicineService illnessMedicineService;
    /**
     * 新增疾病-药品（管理员）
     * @param illnessMedicineAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addIllnessMedicine(@RequestBody IllnessMedicineAddRequest illnessMedicineAddRequest){
        ThrowUtils.throwIf(illnessMedicineAddRequest==null, ErrorCode.PARAMS_ERROR);
        Long result = illnessMedicineService.addIllnessMedicine(illnessMedicineAddRequest);
        return ResultUtils.success(result);
    }

    /**
     * 根据id删除疾病-药品（管理员）
     * @param deleteRequest
     * @return
     */
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteIllnessMedicine(@RequestBody DeleteRequest deleteRequest){
        ThrowUtils.throwIf(deleteRequest==null, ErrorCode.PARAMS_ERROR);
        boolean result = illnessMedicineService.removeById(deleteRequest.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 根据id批量删除疾病-药品（管理员）
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/ids")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteListIllnessMedicine(@RequestParam List<Serializable> ids){
        ThrowUtils.throwIf(ids==null, ErrorCode.PARAMS_ERROR);
        boolean result = illnessMedicineService.removeByIds(ids);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 修改疾病-药品（管理员）
     * @param illnessMedicineUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateIllnessMedicine(@RequestBody IllnessMedicineUpdateRequest illnessMedicineUpdateRequest){
        ThrowUtils.throwIf(illnessMedicineUpdateRequest==null, ErrorCode.PARAMS_ERROR);
        Boolean result = illnessMedicineService.updateIllnessMedicine(illnessMedicineUpdateRequest);
        return ResultUtils.success(result);
    }

    //根据id查询疾病-药品（管理员）
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<IllnessMedicine> getIllnessMedicine(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        IllnessMedicine illnessMedicine = illnessMedicineService.getById(id);
        ThrowUtils.throwIf(illnessMedicine==null,ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(illnessMedicine);
    }

    //根据id查询疾病-药品
    @GetMapping("/get/vo")
    public BaseResponse<IllnessMedicineVO> getIllnessMedicineVO(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        IllnessMedicine illnessMedicine = getIllnessMedicine(id).getData();
        IllnessMedicineVO illnessMedicineVO = illnessMedicineService.getIllnessMedicineVO(illnessMedicine);
        return ResultUtils.success(illnessMedicineVO);
    }

    /**
     * 分页查询疾病-药品
     * @param illnessMedicineQueryRequest
     * @return
     */
    @PostMapping("/page")
    public BaseResponse<Page<IllnessMedicineVO>> listIllnessMedicineVOByPage(@RequestBody IllnessMedicineQueryRequest illnessMedicineQueryRequest) {
        ThrowUtils.throwIf(illnessMedicineQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<IllnessMedicineVO> page = illnessMedicineService.listIllnessMedicineVOByPage(illnessMedicineQueryRequest);
        return ResultUtils.success(page);
    }

}
