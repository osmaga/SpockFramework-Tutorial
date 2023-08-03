package com.osmaga.examples.jpa.service.impl;

import com.osmaga.examples.jpa.constants.Career;
import com.osmaga.examples.jpa.constants.ErrorCode;
import com.osmaga.examples.jpa.exceptions.BadRequestException;
import com.osmaga.examples.jpa.exceptions.StudentNotFoundException;
import com.osmaga.examples.jpa.model.Student;
import com.osmaga.examples.jpa.repository.StudentRepository;
import com.osmaga.examples.jpa.service.StudentService;
import com.osmaga.examples.jpa.util.CostsUtil;
import com.osmaga.examples.jpa.util.TrackingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final TrackingUtils trackingUtils;

    @Override
    public Long getSemesterFee(Career career, LocalDate dueDate) {
        if (dueDate.isAfter(LocalDate.now()) || dueDate.isEqual(LocalDate.now())) {
            return CostsUtil.getSemesterFee(career);
        } else {
            return CostsUtil.getLateSemesterFee(career);
        }
    }

    @Override
    public Long getSemesterFee(Student student) {
        return getSemesterFee(student.getCareer(), student.getDueDate());
    }

    @Override
    public Iterable<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(Long id) {
        log.info("finding {}", trackingUtils.getCurrentTraceId());
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public Student addStudent(Student student) {
        long totalFee = getTotalFee(student);
        student.setSemesterFee(totalFee);
        return studentRepository.save(student);
    }

    private long getTotalFee(Student student) {
        long discountFee = Optional.ofNullable(student.getDiscountFee()).orElse(0L);
        if (discountFee > 0 && !StringUtils.hasText(student.getDiscountReason())) {
            throw new BadRequestException(ErrorCode.DISCOUNT_WITHOUT_REASON);
        }
        return getSemesterFee(student.getCareer(), student.getDueDate()) - discountFee;
    }

    @Override
    public void deleteById(Long id) {
        try {
            final Student student = getStudent(id);
            log.info("deleting {}", trackingUtils.getCurrentTraceId());
            studentRepository.delete(student);
        } catch (Exception e) {
            log.error("studentId {} not found", id);
        }
    }

    @Override
    public Student updateStudentCareer(Long id, Career career) {
        log.info("updating {}", trackingUtils.getCurrentTraceId());
        Student studentToUpdate = getStudent(id);

        if (career == Career.LAWYER) {
            studentToUpdate.setDiscountFee(Double.valueOf(studentToUpdate.getDiscountFee() * .5).longValue());
        } else if (career == Career.JOURNALIST) {
            studentToUpdate.setDiscountFee(0L);
        }

        studentToUpdate.setCareer(career);
        studentToUpdate.setSemesterFee(getTotalFee(studentToUpdate));

        studentToUpdate = studentRepository.save(studentToUpdate);

        log.info("updated {}", trackingUtils.getCurrentTraceId());
        return studentToUpdate;
    }
}
