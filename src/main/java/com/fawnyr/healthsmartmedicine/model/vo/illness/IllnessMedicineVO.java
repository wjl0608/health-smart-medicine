package com.fawnyr.healthsmartmedicine.model.vo.illness;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IllnessMedicineVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 病id
     */
    private Long illnessId;

    /**
     * 药品id
     */
    private Long medicineId;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}