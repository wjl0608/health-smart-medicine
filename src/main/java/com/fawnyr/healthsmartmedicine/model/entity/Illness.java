package com.fawnyr.healthsmartmedicine.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 疾病
 * @TableName illness
 */
@TableName(value ="illness")
@Data
public class Illness implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 编辑时间
     */
    private Date editTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}