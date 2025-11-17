package com.fawnyr.healthsmartmedicine.model.vo.illness;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IllnessVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 种类id
     */
    private Long kingId;

    /**
     * 疾病名称
     */
    private String illnessName;

    /**
     * 诱发因素
     */
    private String includeReason;

    /**
     * 疾病症状
     */
    private String illnessSymptom;

    /**
     * 特殊症状
     */
    private String specialSymptom;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}