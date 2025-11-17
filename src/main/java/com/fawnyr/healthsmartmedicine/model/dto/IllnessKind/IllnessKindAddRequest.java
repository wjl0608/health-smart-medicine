package com.fawnyr.healthsmartmedicine.model.dto.IllnessKind;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IllnessKindAddRequest implements Serializable {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 描述
     */
    private String info;

    private static final long serialVersionUID = 1L;
}