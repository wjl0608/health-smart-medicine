package com.fawnyr.healthsmartmedicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.Medicine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fawnyr.healthsmartmedicine.model.vo.medicine.MedicineVO;

/**
* @author wujialu
* @description 针对表【medicine(药品)】的数据库操作Service
* @createDate 2025-11-13 17:01:59
*/
public interface MedicineService extends IService<Medicine> {

    /**
     * 新增药品
     * @param medicineAddRequest
     * @return
     */
    Long addMedicine(MedicineAddRequest medicineAddRequest);

    /**
     * 修改药品
     * @param medicineUpdateRequest
     * @return
     */
    Boolean updateMedicine(MedicineUpdateRequest medicineUpdateRequest);

    /**
     * medicine转vo
     * @param medicine
     * @return
     */
    MedicineVO getMedicineVO(Medicine medicine);

    /**
     * 分页查询
     * @param medicineQueryRequest
     * @return
     */
    Page<MedicineVO> listMedicineVOByPage(MedicineQueryRequest medicineQueryRequest);

    /**
     * 构造查询条件
     * @param medicineQueryRequest
     * @return
     */
    QueryWrapper<Medicine> getQueryWrapper(MedicineQueryRequest medicineQueryRequest);
}
