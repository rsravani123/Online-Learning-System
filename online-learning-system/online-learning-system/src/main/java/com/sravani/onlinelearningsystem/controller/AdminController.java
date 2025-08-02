package com.sravani.onlinelearningsystem.controller;

import com.sravani.onlinelearningsystem.model.Course;
import com.sravani.onlinelearningsystem.model.User;
import com.sravani.onlinelearningsystem.model.Role;
import com.sravani.onlinelearningsystem.service.AdminService;
import com.sravani.onlinelearningsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CourseService courseService;

    /**
     * Get all users in the system
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    /**
     * Get all courses available in the system
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(adminService.getAllCourses());
    }

    /**
     * Delete a user by ID
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok("✅ User with ID " + id + " deleted successfully.");
    }

    /**
     * Promote a user to a higher role (INSTRUCTOR or ADMIN)
     */
    @PutMapping("/users/{id}/promote")
    public ResponseEntity<String> promoteUser(@PathVariable Long id, @RequestParam String role) {
        try {
            Role newRole = Role.valueOf(role.toUpperCase());
            adminService.updateUserRole(id, newRole);
            return ResponseEntity.ok("✅ User with ID " + id + " promoted to " + newRole.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Invalid role: " + role + ". Valid roles: STUDENT, INSTRUCTOR, ADMIN.");
        }
    }

    /**
     * Demote a user to a lower role (e.g., back to STUDENT)
     */
    @PutMapping("/users/{id}/demote")
    public ResponseEntity<String> demoteUser(@PathVariable Long id, @RequestParam String role) {
        try {
            Role newRole = Role.valueOf(role.toUpperCase());
            adminService.updateUserRole(id, newRole);
            return ResponseEntity.ok("✅ User with ID " + id + " demoted to " + newRole.name());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Invalid role: " + role + ". Valid roles: STUDENT, INSTRUCTOR, ADMIN.");
        }
    }

    /**
     * Create a new course as an admin (assign to an instructor)
     */
    @PostMapping("/courses/create")
    public ResponseEntity<Course> createCourseAsAdmin(
            @RequestBody Course course,
            @RequestParam String instructorUsername
    ) {
        Course created = courseService.createCourse(course, instructorUsername);
        return ResponseEntity.ok(created);
    }

    /**
     * Test endpoint to verify admin access
     */
    @GetMapping("/test")
    public ResponseEntity<String> testAdminAccess() {
        return ResponseEntity.ok("✅ Admin access is working.");
    }
}
