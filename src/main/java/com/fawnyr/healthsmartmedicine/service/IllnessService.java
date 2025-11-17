package com.fawnyr.healthsmartmedicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.Illness;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessVO;

/**
* @author wujialu
* @description 针对表【illness(疾病)】的数据库操作Service
* @createDate 2025-11-17 13:52:53
*/
public interface IllnessService extends IService<Illness> {

    /**
     * 新增药品
     * @param illnessAddRequest
     * @return
     */
    Long addIllness(IllnessAddRequest illnessAddRequest);

    /**
     * 更新疾病
     * @param illnessUpdateRequest
     * @return
     */
    boolean updateIllness(IllnessUpdateRequest illnessUpdateRequest);

    /**
     * 转IllnessVO
     * @param illness
     * @return
     */
    IllnessVO getIllnessVO(Illness illness);

    /**
     * 分页查询
     * @param illnessQueryRequest
     * @return
     */
    Page<IllnessVO> listIllnessVOByPage(IllnessQueryRequest illnessQueryRequest);

    /**
     * 获取查询条件
     * @param illnessQueryRequest
     * @return
     */
    QueryWrapper<Illness> getQueryWrapper(IllnessQueryRequest illnessQueryRequest);

    /**
     * 查询疾病
     * @param id
     * @return
     */
    IllnessVO getIllness(Long id);
}
