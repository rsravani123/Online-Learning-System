package com.sravani.onlinelearningsystem.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // ✅ This must exist

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore

    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> enrolledCourses = new ArrayList<>();

    // 👇 Add this if you're using email for login or course enrollment
    private String email; // ✅ Optional but required if findByEmail is used
}
