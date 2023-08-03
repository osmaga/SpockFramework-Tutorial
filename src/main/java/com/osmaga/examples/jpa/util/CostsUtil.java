package com.osmaga.examples.jpa.util;

import com.osmaga.examples.jpa.constants.Career;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CostsUtil {

    private Long engineeringFee;

    private Long lawyerFee;

    private Long journalistFee;

    private Long lateFeePercentage;

    public static void init(Long engineeringFeeV, Long lawyerFeeV, Long journalistFeeV, Long lateFeePercentageV) {
        engineeringFee = engineeringFeeV;
        lawyerFee = lawyerFeeV;
        journalistFee = journalistFeeV;
        lateFeePercentage = lateFeePercentageV;
    }

    public Long getSemesterFee(Career career) {
        switch (career) {
            case ENGINEERING:
                return engineeringFee;
            case LAWYER:
                return lawyerFee;
            case JOURNALIST:
                return journalistFee;
            default:
                throw new RuntimeException("Unsupported career");
        }
    }

    public Long getLateSemesterFee(Career career) {
        Long semesterFee = getSemesterFee(career);
        return semesterFee + semesterFee * lateFeePercentage / 100;
    }
}
