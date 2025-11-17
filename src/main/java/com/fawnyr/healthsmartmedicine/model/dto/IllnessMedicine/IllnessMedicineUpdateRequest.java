package com.fawnyr.healthsmartmedicine.model.dto.IllnessMedicine;

import com.fawnyr.healthsmartmedicine.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class IllnessMedicineUpdateRequest implements Serializable {
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

    private static final long serialVersionUID = 1L;
}