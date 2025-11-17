package com.fawnyr.healthsmartmedicine.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.illness.IllnessUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.Illness;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessVO;
import com.fawnyr.healthsmartmedicine.service.IllnessService;
import com.fawnyr.healthsmartmedicine.mapper.IllnessMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wujialu
* @description 针对表【illness(疾病)】的数据库操作Service实现
* @createDate 2025-11-17 13:52:53
*/
@Service
public class IllnessServiceImpl extends ServiceImpl<IllnessMapper, Illness>
    implements IllnessService{

    /**
     * 新增药品
     * @param illnessAddRequest
     * @return
     */
    @Override
    public Long addIllness(IllnessAddRequest illnessAddRequest) {
        //1.数据转换
        Illness illness = new Illness();
        BeanUtils.copyProperties(illnessAddRequest,illness);
        //2.加入数据库
        boolean result = this.save(illness);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //3.返回id
        return illness.getId();
    }

    /**
     * 更新疾病
     * @param illnessUpdateRequest
     * @return
     */
    @Override
    public boolean updateIllness(IllnessUpdateRequest illnessUpdateRequest) {
        //1.数据转化
        Illness illness = new Illness();
        BeanUtils.copyProperties(illnessUpdateRequest,illness);
        //2.判断数据是否存在
        Illness byId = this.getById(illness.getId());
        ThrowUtils.throwIf(byId==null, ErrorCode.NOT_FOUND_ERROR);
        //3.更新数据
        boolean result = this.updateById(illness);
        ThrowUtils.throwIf(!result, ErrorCode.NOT_FOUND_ERROR);
        //3.返回结果
        return result;
    }

    /**
     * 转IllnessVO
     * @param illness
     * @return
     */
    @Override
    public IllnessVO getIllnessVO(Illness illness) {
        if(illness==null)
            return null;
        IllnessVO illnessVO = new IllnessVO();
        BeanUtils.copyProperties(illness,illnessVO);
        return illnessVO;
    }

    /**
     * 分页查询
     * @param illnessQueryRequest
     * @return
     */
    @Override
    public Page<IllnessVO> listIllnessVOByPage(IllnessQueryRequest illnessQueryRequest) {
        int current = illnessQueryRequest.getCurrent();
        int pageSize = illnessQueryRequest.getPageSize();
        Page<Illness> illnessPage = this.page(new Page<>(current, pageSize), getQueryWrapper(illnessQueryRequest));
        Page<IllnessVO> illnessVOPage = new Page<>(current,pageSize,illnessPage.getTotal());
        List<IllnessVO> illnessVOList = getIllnessVOList(illnessPage.getRecords());
        illnessVOPage.setRecords(illnessVOList);
        return illnessVOPage;
    }

    private List<IllnessVO> getIllnessVOList(List<Illness> illnessList) {
        if(illnessList==null)
            return new ArrayList<>();
        return illnessList.stream().map(this::getIllnessVO).collect(Collectors.toList());
    }

    /**
     * 获取查询条件
     * @param illnessQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Illness> getQueryWrapper(IllnessQueryRequest illnessQueryRequest) {
        QueryWrapper<Illness> queryWrapper = new QueryWrapper<>();
        Long id = illnessQueryRequest.getId();
        Long kingId = illnessQueryRequest.getKingId();
        String illnessName = illnessQueryRequest.getIllnessName();
        String includeReason = illnessQueryRequest.getIncludeReason();
        String illnessSymptom = illnessQueryRequest.getIllnessSymptom();
        String specialSymptom = illnessQueryRequest.getSpecialSymptom();
        String sortField = illnessQueryRequest.getSortField();
        String sortOrder = illnessQueryRequest.getSortOrder();
        queryWrapper.eq(id>0,"id",id);
        queryWrapper.eq(kingId>0,"kingId",kingId);
        queryWrapper.like(ObjectUtil.isNotEmpty(illnessName),"illnessName",illnessName);
        queryWrapper.like(ObjectUtil.isNotEmpty(includeReason),"includeReason",includeReason);
        queryWrapper.like(ObjectUtil.isNotEmpty(illnessSymptom),"illnessSymptom",illnessSymptom);
        queryWrapper.like(ObjectUtil.isNotEmpty(specialSymptom),"specialSymptom",specialSymptom);
        queryWrapper.like(ObjectUtil.isNotEmpty(illnessName),"illnessName",illnessName);
        queryWrapper.orderBy(ObjectUtil.isNotEmpty(sortOrder),sortOrder.equals("ascend"),sortField);
        return queryWrapper;
    }

}




