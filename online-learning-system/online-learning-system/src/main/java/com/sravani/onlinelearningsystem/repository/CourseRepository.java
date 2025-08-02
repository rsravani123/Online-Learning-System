package com.sravani.onlinelearningsystem.repository;

import com.sravani.onlinelearningsystem.model.Course;
import com.sravani.onlinelearningsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByInstructor(User instructor);
    List<Course> findByEnrolledStudentsContaining(User student);
}
