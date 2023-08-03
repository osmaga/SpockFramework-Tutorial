package com.osmaga.examples.jpa.config;

import com.osmaga.examples.jpa.util.CostsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class StudentsConfig {

    @Value("${fees.career.engineering:0}")
    public Long engineeringFee;

    @Value("${fees.career.lawyer:0}")
    public Long lawyerFee;

    @Value("${fees.career.journalist:0}")
    public Long journalistFee;

    @Value("${fees.career.lateFeePercentage:0}")
    public Long lateFeePercentage;

    @PostConstruct
    public void setup() {
        CostsUtil.init(engineeringFee, lawyerFee, journalistFee, lateFeePercentage);
    }
}