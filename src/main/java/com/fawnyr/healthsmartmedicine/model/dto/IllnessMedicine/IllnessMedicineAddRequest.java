package com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IllnessMedicineAddRequest implements Serializable {
    /**
     * 病id
     */
    private Long illnessId;

    /**
     * 药品id
     */
    private Long medicineId;

    private static final long serialVersionUID = 1L;
}