package com.example.be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "marketer")
public class Marketer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer experienceYears;
    private String specialization;

    @JsonBackReference("userMarketerReference")
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonBackReference("roleMarketerReference")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public Marketer() {
    }

    // Getters và Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Marketer{" +
                "id=" + id +
                ", experienceYears=" + experienceYears +
                ", specialization='" + specialization + '\'' +
                ", user=" + user +
                ", role=" + role +
                '}';
    }
}
