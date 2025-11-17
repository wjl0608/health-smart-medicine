package com.fawnyr.healthsmartmedicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessMedicine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessMedicineVO;

/**
* @author wujialu
* @description 针对表【illness_medicine(疾病-药物)】的数据库操作Service
* @createDate 2025-11-17 15:20:00
*/
public interface IllnessMedicineService extends IService<IllnessMedicine> {
    /**
     * 新增疾病-药品
     * @param illnessMedicineAddRequest
     * @return
     */
    Long addIllnessMedicine(IllnessMedicineAddRequest illnessMedicineAddRequest);

    /**
     * 修改疾病-药品
     * @param illnessMedicineUpdateRequest
     * @return
     */
    Boolean updateIllnessMedicine(IllnessMedicineUpdateRequest illnessMedicineUpdateRequest);

    /**
     * 转vo
     * @param illnessMedicine
     * @return
     */
    IllnessMedicineVO getIllnessMedicineVO(IllnessMedicine illnessMedicine);

    /**
     * 分页查询
     * @param illnessMedicineQueryRequest
     * @return
     */
    Page<IllnessMedicineVO> listIllnessMedicineVOByPage(IllnessMedicineQueryRequest illnessMedicineQueryRequest);

    /**
     * 构造查询条件
     * @param illnessMedicineQueryRequest
     * @return
     */
    QueryWrapper<IllnessMedicine> getQueryWrapper(IllnessMedicineQueryRequest illnessMedicineQueryRequest);

}
