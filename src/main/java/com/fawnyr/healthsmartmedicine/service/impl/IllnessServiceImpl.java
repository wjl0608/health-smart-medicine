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
import com.fawnyr.healthsmartmedicine.model.entity.IllnessKind;
import com.fawnyr.healthsmartmedicine.model.entity.IllnessMedicine;
import com.fawnyr.healthsmartmedicine.model.entity.Medicine;
import com.fawnyr.healthsmartmedicine.model.vo.illness.IllnessVO;
import com.fawnyr.healthsmartmedicine.model.vo.medicine.MedicineVO;
import com.fawnyr.healthsmartmedicine.service.IllnessKindService;
import com.fawnyr.healthsmartmedicine.service.IllnessMedicineService;
import com.fawnyr.healthsmartmedicine.service.IllnessService;
import com.fawnyr.healthsmartmedicine.mapper.IllnessMapper;
import com.fawnyr.healthsmartmedicine.service.MedicineService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class IllnessServiceImpl extends ServiceImpl<IllnessMapper, Illness>
    implements IllnessService{
    @Resource
    MedicineService medicineService;
    @Resource
    IllnessKindService illnessKindService;
    @Resource
    IllnessMedicineService illnessMedicineService;
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
        return fillIllnessVO(illnessVO);
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

    /**
     * 查询疾病
     * @param id
     * @return
     */
    @Override
    public IllnessVO getIllness(Long id) {
        Illness illness = this.getById(id);
        ThrowUtils.throwIf(illness==null,ErrorCode.NOT_FOUND_ERROR);
        IllnessVO illnessVO = new IllnessVO();
        BeanUtils.copyProperties(illness,illnessVO);
        IllnessVO result = fillIllnessVO(illnessVO);
        return result;
    }

    private IllnessVO fillIllnessVO(IllnessVO illnessVO) {
        Long illNessId = illnessVO.getId();
        Long kingId = illnessVO.getKingId();
        IllnessKind illnessKind = illnessKindService.getById(kingId);
        ThrowUtils.throwIf(illnessKind==null,ErrorCode.NOT_FOUND_ERROR);
        illnessVO.setKingName(illnessKind.getName());
        illnessVO.setKingInfo(illnessKind.getInfo());
        QueryWrapper<IllnessMedicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(illNessId!=0,"illnessId",illNessId);
        List<Long> illnessMedicineIds = illnessMedicineService.listObjs(queryWrapper);
        List<MedicineVO> medicineVOList = null;
        if(illnessMedicineIds==null)
            illnessVO.setMedicineVOList(medicineVOList);
        else{
            medicineVOList = new ArrayList<>();
            for(Long illnessMedicineId:illnessMedicineIds){
                IllnessMedicine illnessMedicine = illnessMedicineService.getById(illnessMedicineId);
                Medicine medicine = medicineService.getById(illnessMedicine.getMedicineId());
                medicineVOList.add(medicineService.getMedicineVO(medicine));
            }
            illnessVO.setMedicineVOList(medicineVOList);
        }
        return illnessVO;
    }


}




