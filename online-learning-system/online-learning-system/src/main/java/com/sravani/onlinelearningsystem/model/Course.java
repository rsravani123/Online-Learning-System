package com.sravani.onlinelearningsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    // ✅ Avoid recursive instructor -> course -> instructor loop
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private User instructor;

    // ✅ Prevent infinite loop when returning courses with students
    @ManyToMany(mappedBy = "enrolledCourses")
    @JsonIgnore
    private List<User> enrolledStudents = new ArrayList<>();
}
