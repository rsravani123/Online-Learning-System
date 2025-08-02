package com.sravani.onlinelearningsystem.controller;

import com.sravani.onlinelearningsystem.model.Course;
import com.sravani.onlinelearningsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // ✅ Get all courses (available to everyone logged in)
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    // ✅ INSTRUCTOR can create a course
    @PostMapping("/create")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Course> createCourse(@RequestBody Course course, Authentication authentication) {
        String instructorUsername = authentication.getName();
        Course created = courseService.createCourse(course, instructorUsername);
        return ResponseEntity.ok(created);
    }

    // ✅ INSTRUCTOR can update their own course
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestBody Course updatedCourse,
            Authentication authentication
    ) {
        String instructorUsername = authentication.getName();
        Course course = courseService.updateCourse(id, updatedCourse, instructorUsername);
        return ResponseEntity.ok(course);
    }

    // ✅ INSTRUCTOR can delete their own course
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id, Authentication authentication) {
        String instructorUsername = authentication.getName();
        courseService.deleteCourse(id, instructorUsername);
        return ResponseEntity.ok("Course deleted successfully");
    }

    // ✅ STUDENT can view their enrolled courses
    @GetMapping("/enrolled")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<Course>> getEnrolledCourses(Authentication authentication) {
        String studentUsername = authentication.getName();
        return ResponseEntity.ok(courseService.getEnrolledCourses(studentUsername));
    }

    // ✅ STUDENT can enroll in a course
    @PostMapping("/enroll/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> enrollInCourse(@PathVariable Long courseId, Authentication authentication) {
        String studentUsername = authentication.getName();
        courseService.enrollStudentInCourse(studentUsername, courseId);
        return ResponseEntity.ok("Enrolled successfully");
    }
}
