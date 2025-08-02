package com.sravani.onlinelearningsystem.controller;

import com.sravani.onlinelearningsystem.model.Course;
import com.sravani.onlinelearningsystem.model.User;
import com.sravani.onlinelearningsystem.repository.UserRepository;
import com.sravani.onlinelearningsystem.service.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final CourseService courseService;
    private final UserRepository userRepository;

    @GetMapping("/my-courses")
    public ResponseEntity<List<Course>> viewEnrolledCourses(@AuthenticationPrincipal UserDetails userDetails) {
        User student = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        List<Course> enrolledCourses = student.getEnrolledCourses();
        return ResponseEntity.ok(enrolledCourses);
    }

    @PostMapping("/enroll/{courseId}")
    @Transactional
    public ResponseEntity<String> enrollInCourse(@PathVariable Long courseId,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        courseService.enrollStudentInCourse(userDetails.getUsername(), courseId);
        return ResponseEntity.ok("Successfully enrolled in course ID: " + courseId);
    }
}
