package com.fawnyr.healthsmartmedicine.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine.IllnessMedicineUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessMedicine;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessMedicine;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessMedicineVO;
import com.fawnyr.healthsmartmedicine.service.IllnessMedicineService;
import com.fawnyr.healthsmartmedicine.mapper.IllnessMedicineMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author wujialu
* @description 针对表【illness_medicine(疾病-药物)】的数据库操作Service实现
* @createDate 2025-11-17 15:20:00
*/
@Service
public class IllnessMedicineServiceImpl extends ServiceImpl<IllnessMedicineMapper, IllnessMedicine>
    implements IllnessMedicineService{

    /**
     * 新增疾病-药品
     * @param illnessMedicineAddRequest
     * @return
     */
    @Override
    public Long addIllnessMedicine(IllnessMedicineAddRequest illnessMedicineAddRequest) {
        //1.转成IllnessMedicine
        IllnessMedicine illnessMedicine = new IllnessMedicine();
        BeanUtils.copyProperties(illnessMedicineAddRequest,illnessMedicine);
        //2.插入数据库
        boolean result = this.save(illnessMedicine);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //3.返回结果
        return illnessMedicine.getId();
    }

    /**
     * 修改疾病-药品
     * @param illnessMedicineUpdateRequest
     * @return
     */
    @Override
    public Boolean updateIllnessMedicine(IllnessMedicineUpdateRequest illnessMedicineUpdateRequest) {
        //1.转成IllnessMedicine
        IllnessMedicine illnessMedicine = new IllnessMedicine();
        BeanUtils.copyProperties(illnessMedicineUpdateRequest,illnessMedicine);
        //2.判断是否存在
        Long id = illnessMedicine.getId();
        IllnessMedicine byId = this.getById(id);
        ThrowUtils.throwIf(byId==null, ErrorCode.PARAMS_ERROR,"数据不存在");
        //3.更新数据库
        boolean result = this.updateById(illnessMedicine);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //4.返回结果
        return true;
    }

    /**
     * illnessMedicine转vo
     * @param illnessMedicine
     * @return
     */
    @Override
    public IllnessMedicineVO getIllnessMedicineVO(IllnessMedicine illnessMedicine) {
        if(illnessMedicine==null)
            return null;
        IllnessMedicineVO illnessMedicineVO = new IllnessMedicineVO();
        BeanUtils.copyProperties(illnessMedicine,illnessMedicineVO);
        return illnessMedicineVO;
    }

    /**
     * 分页查询
     * @param illnessMedicineQueryRequest
     * @return
     */
    @Override
    public Page<IllnessMedicineVO> listIllnessMedicineVOByPage(IllnessMedicineQueryRequest illnessMedicineQueryRequest) {
        int current = illnessMedicineQueryRequest.getCurrent();
        int pageSize = illnessMedicineQueryRequest.getPageSize();
        Page<IllnessMedicine> illnessMedicinePage = this.page(new Page<>(current, pageSize), this.getQueryWrapper(illnessMedicineQueryRequest));
        Page<IllnessMedicineVO> illnessMedicineVOPage = new Page<>(current,pageSize,illnessMedicinePage.getTotal());
        List<IllnessMedicineVO> illnessMedicineVOList = getillnessMedicineVOList(illnessMedicinePage.getRecords());
        illnessMedicineVOPage.setRecords(illnessMedicineVOList);
        return illnessMedicineVOPage;
    }

    private List<IllnessMedicineVO> getillnessMedicineVOList(List<IllnessMedicine> illnessMedicineList) {
        if(illnessMedicineList==null)
            return new ArrayList<>();
        return illnessMedicineList.stream().map(this::getIllnessMedicineVO).collect(Collectors.toList());
    }

    /**
     * 构造查询条件
     * @param illnessMedicineQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<IllnessMedicine> getQueryWrapper(IllnessMedicineQueryRequest illnessMedicineQueryRequest) {
        Long id = illnessMedicineQueryRequest.getId();
        Long illnessId = illnessMedicineQueryRequest.getIllnessId();
        Long medicineId = illnessMedicineQueryRequest.getMedicineId();
        String sortField = illnessMedicineQueryRequest.getSortField();
        String sortOrder = illnessMedicineQueryRequest.getSortOrder();
        QueryWrapper<IllnessMedicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id!=0,"id",id);
        queryWrapper.eq(illnessId!=0,"illnessId",illnessId);
        queryWrapper.eq(medicineId!=0,"medicineId",medicineId);
        queryWrapper.orderBy(ObjectUtil.isNotEmpty(sortOrder),sortOrder.equals("ascend"),sortField);
        return queryWrapper;
    }
}




