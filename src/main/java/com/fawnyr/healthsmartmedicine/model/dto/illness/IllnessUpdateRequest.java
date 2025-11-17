package com.fawnyr.healthsmartmedicine.model.dto.illness;

import lombok.Data;

import java.io.Serializable;

@Data
public class IllnessUpdateRequest implements Serializable {
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

    private static final long serialVersionUID = 1L;
}