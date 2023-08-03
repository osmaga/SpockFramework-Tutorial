package com.osmaga.examples.jpa.service.impl

import com.osmaga.examples.jpa.constants.Career
import com.osmaga.examples.jpa.service.utils.TestConstants
import com.osmaga.examples.jpa.util.CostsUtil
import spock.lang.Specification

class SetupMethods extends Specification {


    def setupSpec() {
        println("Setting up the specification...")
        CostsUtil.init(TestConstants.DEFAULT_ENGINEERING_FEE, TestConstants.DEFAULT_LAWYER_FEE, TestConstants.DEFAULT_JOURNALIST_FEE, TestConstants.DEFAULT_LATE_FEE_PERCENTAGE)
    }

    def cleanupSpec() {
        println("Cleaning up after the specification...")
    }

    def setup() {
        println("Setting up the test...")
    }

    def cleanup() {
        println("Cleaning up after the test...")
    }

    def "Test 1: With default values"() {
        when:
        // Stimulus
        def result = CostsUtil.getSemesterFee(Career.ENGINEERING)

        then:
        // Asserts
        result == TestConstants.DEFAULT_ENGINEERING_FEE
    }

    def "Test 2: We could override what was defined in Setup method"() {
        given:
        CostsUtil.init(10000,11000,12000,10)

        when:
        // Stimulus
        def result = CostsUtil.getSemesterFee(Career.ENGINEERING)

        then:
        // Asserts
        result == 10000
    }

    def "Test 3: Attention! Previous test changes CostsUtil values for subsequent tests"() {
        when:
        def result = CostsUtil.getSemesterFee(Career.LAWYER)

        then:
        // The assert should be '==' since we are using LAWYER career in stimulus
        // but the previous test changed CostsUtil values
        result != TestConstants.DEFAULT_LAWYER_FEE
    }

}
