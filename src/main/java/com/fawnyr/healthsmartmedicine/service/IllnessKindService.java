package com.fawnyr.healthsmartmedicine.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessKind;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessKindVO;

/**
* @author wujialu
* @description 针对表【illness_kind(疾病种类)】的数据库操作Service
* @createDate 2025-11-17 15:11:33
*/
public interface IllnessKindService extends IService<IllnessKind> {
    /**
     * 新增分类
     * @param illnessKindAddRequest
     * @return
     */
    Long addIllnessKind(IllnessKindAddRequest illnessKindAddRequest);

    /**
     * 修改分类
     * @param illnessKindUpdateRequest
     * @return
     */
    Boolean updateIllnessKind(IllnessKindUpdateRequest illnessKindUpdateRequest);

    /**
     * 转vo
     * @param illnessKind
     * @return
     */
    IllnessKindVO getIllnessKindVO(IllnessKind illnessKind);

    /**
     * 分页查询
     * @param illnessKindQueryRequest
     * @return
     */
    Page<IllnessKindVO> listIllnessKindVOByPage(IllnessKindQueryRequest illnessKindQueryRequest);

    /**
     * 构造查询条件
     * @param illnessKindQueryRequest
     * @return
     */
    QueryWrapper<IllnessKind> getQueryWrapper(IllnessKindQueryRequest illnessKindQueryRequest);

}
