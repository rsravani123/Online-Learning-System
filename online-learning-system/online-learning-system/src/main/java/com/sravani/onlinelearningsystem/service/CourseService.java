package com.sravani.onlinelearningsystem.service;

import com.sravani.onlinelearningsystem.model.Course;
import com.sravani.onlinelearningsystem.model.User;
import com.sravani.onlinelearningsystem.repository.CourseRepository;
import com.sravani.onlinelearningsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course, String instructorUsername) {
        User instructor = userRepository.findByUsername(instructorUsername)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse, String instructorUsername) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor().getUsername().equals(instructorUsername)) {
            throw new RuntimeException("Not authorized to update this course");
        }

        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id, String instructorUsername) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getInstructor().getUsername().equals(instructorUsername)) {
            throw new RuntimeException("Not authorized to delete this course");
        }

        courseRepository.delete(course);
    }

    public void enrollStudentInCourse(String username, Long courseId) {
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!student.getEnrolledCourses().contains(course)) {
            student.getEnrolledCourses().add(course);
            userRepository.save(student);
        }
    }

    // ✅ NEW METHOD: Used by CourseController
    public List<Course> getEnrolledCourses(String username) {
        User student = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return student.getEnrolledCourses();
    }
}
