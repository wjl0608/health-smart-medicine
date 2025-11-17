package com.fawnyr.healthsmartmedicine.model.dto.IllnessKind;

import lombok.Data;

import java.io.Serializable;

@Data
public class IllnessKindUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

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