package com.fawnyr.healthsmartmedicine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.fawnyr.healthsmartmedicine.mapper")
@SpringBootApplication
public class HealthSmartMedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthSmartMedicineApplication.class, args);
    }

}
