package com.osmaga.examples.jpa.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DISCOUNT_WITHOUT_REASON("Discount fee should include discount reason"),
    EQUAL_CAREERS("Current career is equal to changing career"),
    SUBJECTS_EMPTY("Teacher's subjects taught shouldn't be empty"),
    ;

    private final String description;

}
