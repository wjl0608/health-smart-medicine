package com.fawnyr.healthsmartmedicine.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.annotation.AuthCheck;
import com.fawnyr.healthsmartmedicine.common.BaseResponse;
import com.fawnyr.healthsmartmedicine.common.DeleteRequest;
import com.fawnyr.healthsmartmedicine.common.ResultUtils;
import com.fawnyr.healthsmartmedicine.constant.UserConstant;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.Medicine;
import com.fawnyr.healthsmartmedicine.model.vo.medicine.MedicineVO;
import com.fawnyr.healthsmartmedicine.service.MedicineService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/medicine")
public class MedicineController {
    @Resource
    MedicineService medicineService;
    /**
     * 新增药品（管理员）
     * @param medicineAddRequest
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addMedicine(@RequestBody MedicineAddRequest medicineAddRequest){
        ThrowUtils.throwIf(medicineAddRequest==null, ErrorCode.PARAMS_ERROR);
        Long result = medicineService.addMedicine(medicineAddRequest);
        return ResultUtils.success(result);
    }

    /**
     * 根据id删除药品（管理员）
     * @param deleteRequest
     * @return
     */
    @DeleteMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteMedicine(@RequestBody DeleteRequest deleteRequest){
        ThrowUtils.throwIf(deleteRequest==null, ErrorCode.PARAMS_ERROR);
        boolean result = medicineService.removeById(deleteRequest.getId());
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 根据id批量删除药品（管理员）
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/ids")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteListMedicine(@RequestParam List<Serializable> ids){
        ThrowUtils.throwIf(ids==null, ErrorCode.PARAMS_ERROR);
        boolean result = medicineService.removeByIds(ids);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(result);
    }

    /**
     * 修改药品（管理员）
     * @param medicineUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateMedicine(@RequestBody MedicineUpdateRequest medicineUpdateRequest){
        ThrowUtils.throwIf(medicineUpdateRequest==null, ErrorCode.PARAMS_ERROR);
        Boolean result = medicineService.updateMedicine(medicineUpdateRequest);
        return ResultUtils.success(result);
    }

    //根据id查询药品（管理员）
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Medicine> getMedicine(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        Medicine medicine = medicineService.getById(id);
        ThrowUtils.throwIf(medicine==null,ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(medicine);
    }

    //根据id查询药品
    @GetMapping("/get/vo")
    public BaseResponse<MedicineVO> getMedicineVO(long id){
        ThrowUtils.throwIf(id<=0, ErrorCode.PARAMS_ERROR);
        Medicine medicine = getMedicine(id).getData();
        MedicineVO medicineVO = medicineService.getMedicineVO(medicine);
        return ResultUtils.success(medicineVO);
    }

    /**
     * 分页查询药品
     * @param medicineQueryRequest
     * @return
     */
    @PostMapping("/page")
    public BaseResponse<Page<MedicineVO>> listMedicineVOByPage(@RequestBody MedicineQueryRequest medicineQueryRequest) {
        ThrowUtils.throwIf(medicineQueryRequest == null, ErrorCode.PARAMS_ERROR);
        Page<MedicineVO> page = medicineService.listMedicineVOByPage(medicineQueryRequest);
        return ResultUtils.success(page);
    }

}
