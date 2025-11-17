package com.fawnyr.healthsmartmedicine.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.mapper.IllnessKindMapper;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessKind.IllnessKindUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessKind;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessKindVO;
import com.fawnyr.healthsmartmedicine.service.IllnessKindService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wujialu
* @description 针对表【illness_kind(疾病种类)】的数据库操作Service实现
* @createDate 2025-11-17 15:11:33
*/
@Service
public class IllnessKindServiceImpl extends ServiceImpl<IllnessKindMapper, IllnessKind>
    implements IllnessKindService {

    /**
     * 新增分类
     * @param illnessKindAddRequest
     * @return
     */
    @Override
    public Long addIllnessKind(IllnessKindAddRequest illnessKindAddRequest) {
        //1.转成IllnessKind
        IllnessKind illnessKind = new IllnessKind();
        BeanUtils.copyProperties(illnessKindAddRequest,illnessKind);
        //2.插入数据库
        boolean result = this.save(illnessKind);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //3.返回结果
        return illnessKind.getId();
    }

    /**
     * 修改分类
     * @param illnessKindUpdateRequest
     * @return
     */
    @Override
    public Boolean updateIllnessKind(IllnessKindUpdateRequest illnessKindUpdateRequest) {
        //1.转成IllnessKind
        IllnessKind illnessKind = new IllnessKind();
        BeanUtils.copyProperties(illnessKindUpdateRequest,illnessKind);
        //2.判断是否存在
        Long id = illnessKind.getId();
        IllnessKind byId = this.getById(id);
        ThrowUtils.throwIf(byId==null, ErrorCode.PARAMS_ERROR,"数据不存在");
        //3.更新数据库
        boolean result = this.updateById(illnessKind);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //4.返回结果
        return true;
    }

    /**
     * illnessKind转vo
     * @param illnessKind
     * @return
     */
    @Override
    public IllnessKindVO getIllnessKindVO(IllnessKind illnessKind) {
        if(illnessKind==null)
            return null;
        IllnessKindVO illnessKindVO = new IllnessKindVO();
        BeanUtils.copyProperties(illnessKind,illnessKindVO);
        return illnessKindVO;
    }

    /**
     * 分页查询
     * @param illnessKindQueryRequest
     * @return
     */
    @Override
    public Page<IllnessKindVO> listIllnessKindVOByPage(IllnessKindQueryRequest illnessKindQueryRequest) {
        int current = illnessKindQueryRequest.getCurrent();
        int pageSize = illnessKindQueryRequest.getPageSize();
        Page<IllnessKind> illnessKindPage = this.page(new Page<>(current, pageSize), this.getQueryWrapper(illnessKindQueryRequest));
        Page<IllnessKindVO> illnessKindVOPage = new Page<>(current,pageSize,illnessKindPage.getTotal());
        List<IllnessKindVO> illnessKindVOList = getillnessKindVOList(illnessKindPage.getRecords());
        illnessKindVOPage.setRecords(illnessKindVOList);
        return illnessKindVOPage;
    }

    private List<IllnessKindVO> getillnessKindVOList(List<IllnessKind> illnessKindList) {
        if(illnessKindList==null)
            return new ArrayList<>();
        return illnessKindList.stream().map(this::getIllnessKindVO).collect(Collectors.toList());
    }

    /**
     * 构造查询条件
     * @param illnessKindQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<IllnessKind> getQueryWrapper(IllnessKindQueryRequest illnessKindQueryRequest) {
        Long id = illnessKindQueryRequest.getId();
        String name = illnessKindQueryRequest.getName();
        String info = illnessKindQueryRequest.getInfo();
        String sortField = illnessKindQueryRequest.getSortField();
        String sortOrder = illnessKindQueryRequest.getSortOrder();
        QueryWrapper<IllnessKind> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id!=0,"id",id);
        queryWrapper.like(ObjectUtil.isNotEmpty(name),"name",name);
        queryWrapper.like(ObjectUtil.isNotEmpty(info),"info",info);
        queryWrapper.orderBy(ObjectUtil.isNotEmpty(sortOrder),sortOrder.equals("ascend"),sortField);
        return queryWrapper;
    }


}




