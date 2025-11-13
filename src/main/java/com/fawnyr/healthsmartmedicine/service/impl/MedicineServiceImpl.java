package com.fawnyr.healthsmartmedicine.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fawnyr.healthsmartmedicine.exception.ErrorCode;
import com.fawnyr.healthsmartmedicine.exception.ThrowUtils;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineAddRequest;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineQueryRequest;
import com.fawnyr.healthsmartmedicine.model.dto.medicine.MedicineUpdateRequest;
import com.fawnyr.healthsmartmedicine.model.entity.Medicine;
import com.fawnyr.healthsmartmedicine.model.vo.medicine.MedicineVO;
import com.fawnyr.healthsmartmedicine.service.MedicineService;
import com.fawnyr.healthsmartmedicine.mapper.MedicineMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* @author wujialu
* @description 针对表【medicine(药品)】的数据库操作Service实现
* @createDate 2025-11-13 17:01:59
*/
@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine>
    implements MedicineService{

    /**
     * 新增药品
     * @param medicineAddRequest
     * @return
     */
    @Override
    public Long addMedicine(MedicineAddRequest medicineAddRequest) {
        //1.转成Medicine
        Medicine medicine = new Medicine();
        BeanUtils.copyProperties(medicineAddRequest,medicine);
        //2.插入数据库
        boolean result = this.save(medicine);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //3.返回结果
        return medicine.getId();
    }

    /**
     * 修改药品
     * @param medicineUpdateRequest
     * @return
     */
    @Override
    public Boolean updateMedicine(MedicineUpdateRequest medicineUpdateRequest) {
        //1.转成Medicine
        Medicine medicine = new Medicine();
        BeanUtils.copyProperties(medicineUpdateRequest,medicine);
        //2.判断是否存在
        Long id = medicine.getId();
        Medicine byId = this.getById(id);
        ThrowUtils.throwIf(byId==null, ErrorCode.PARAMS_ERROR,"数据不存在");
        //3.更新数据库
        boolean result = this.updateById(medicine);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        //4.返回结果
        return true;
    }

    /**
     * medicine转vo
     * @param medicine
     * @return
     */
    @Override
    public MedicineVO getMedicineVO(Medicine medicine) {
        if(medicine==null)
            return null;
        MedicineVO medicineVO = new MedicineVO();
        BeanUtils.copyProperties(medicine,medicineVO);
        return medicineVO;
    }

    /**
     * 分页查询
     * @param medicineQueryRequest
     * @return
     */
    @Override
    public Page<MedicineVO> listMedicineVOByPage(MedicineQueryRequest medicineQueryRequest) {
        int current = medicineQueryRequest.getCurrent();
        int pageSize = medicineQueryRequest.getPageSize();
        Page<Medicine> medicinePage = this.page(new Page<>(current, pageSize), this.getQueryWrapper(medicineQueryRequest));
        Page<MedicineVO> medicineVOPage = new Page<>(current,pageSize,medicinePage.getTotal());
        List<MedicineVO> medicineVOList = getmedicineVOList(medicinePage.getRecords());
        medicineVOPage.setRecords(medicineVOList);
        return medicineVOPage;
    }

    private List<MedicineVO> getmedicineVOList(List<Medicine> medicineList) {
        if(medicineList==null)
            return new ArrayList<>();
        return medicineList.stream().map(this::getMedicineVO).collect(Collectors.toList());
    }

    /**
     * 构造查询条件
     * @param medicineQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<Medicine> getQueryWrapper(MedicineQueryRequest medicineQueryRequest) {
        Long id = medicineQueryRequest.getId();
        String medicineName = medicineQueryRequest.getMedicineName();
        String keyword = medicineQueryRequest.getKeyword();
        String medicineEffect = medicineQueryRequest.getMedicineEffect();
        String medicineBrand = medicineQueryRequest.getMedicineBrand();
        String interaction = medicineQueryRequest.getInteraction();
        String taboo = medicineQueryRequest.getTaboo();
        String useAge = medicineQueryRequest.getUseAge();
        String medicineType = medicineQueryRequest.getMedicineType();
        BigDecimal medicinePriceMin = medicineQueryRequest.getMedicinePriceMin();
        BigDecimal medicinePriceMax = medicineQueryRequest.getMedicinePriceMax();
        String sortField = medicineQueryRequest.getSortField();
        String sortOrder = medicineQueryRequest.getSortOrder();
        QueryWrapper<Medicine> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id!=0,"id",id);
        queryWrapper.like(ObjectUtil.isNotEmpty(medicineName),"medicineName",medicineName);
        queryWrapper.like(ObjectUtil.isNotEmpty(keyword),"keyword",keyword);
        queryWrapper.like(ObjectUtil.isNotEmpty(medicineEffect),"medicineEffect",medicineEffect);
        queryWrapper.eq(ObjectUtil.isNotEmpty(medicineBrand),"medicineBrand",medicineBrand);
        queryWrapper.like(ObjectUtil.isNotEmpty(interaction),"interaction",interaction);
        queryWrapper.like(ObjectUtil.isNotEmpty(taboo),"taboo",taboo);
        queryWrapper.like(ObjectUtil.isNotEmpty(useAge),"useAge",useAge);
        queryWrapper.eq(ObjectUtil.isNotEmpty(medicineType),"medicineType",medicineType);
        queryWrapper.ge(!medicinePriceMin.equals(BigDecimal.ZERO),"medicinePrice",medicinePriceMin);
        queryWrapper.le(!medicinePriceMax.equals(BigDecimal.ZERO),"medicinePrice",medicinePriceMax);
        queryWrapper.orderBy(ObjectUtil.isNotEmpty(sortOrder),sortOrder.equals("ascend"),sortField);
        return queryWrapper;
    }


}




