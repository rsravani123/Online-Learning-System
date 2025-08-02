package com.sravani.onlinelearningsystem.service;

import com.sravani.onlinelearningsystem.model.Course;
import com.sravani.onlinelearningsystem.model.User;
import com.sravani.onlinelearningsystem.model.Role;
import com.sravani.onlinelearningsystem.repository.CourseRepository;
import com.sravani.onlinelearningsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    /**
     * Get all users from DB
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get all courses from DB
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Delete a user by ID
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("❌ User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Promote or demote a user by changing their role
     */
    public void updateUserRole(Long id, Role newRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ User not found with ID: " + id));
        user.setRole(newRole);
        userRepository.save(user);
    }

    /**
     * (Optional) If you want to keep promoteUser for legacy code
     */
    public void promoteUser(Long id, Role role) {
        updateUserRole(id, role); // Delegate to shared method
    }
}
