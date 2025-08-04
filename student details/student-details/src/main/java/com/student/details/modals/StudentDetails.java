//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.student.details.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Generated;

@Entity
@Table(
        name = "student_detail"
)
public class StudentDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private long studentId;
    private @NotBlank(
            message = "Name is required"
    ) @Pattern(
            regexp = "^[A-Za-z\\s]{2,50}$",
            message = "Name must contain only letters and spaces (2-50 chars)"
    ) String studentName;
    @Column(
            unique = true
    )
    private @Email(
            message = "Enter a valid email"
    ) @NotBlank(
            message = "Email is required"
    ) String studentEmailId;
    private @NotBlank(
            message = "DOB is required"
    ) @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "DOB must be in YYYY-MM-DD format"
    ) String studentDOB;
    private @NotBlank(
            message = "Contact number is required"
    ) @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Enter valid 10-digit contact no"
    ) String studentContactNo;
    private @NotBlank(
            message = "Password is required"
    ) @Size(
            min = 8,
            max = 50,
            message = "Password must be 8-50 characters"
    ) String studentPassword;
    private @NotBlank(
            message = "Select a role"
    ) @Pattern(
            regexp = "Admin|Student",
            message = "Invalid role selected"
    ) String role;

    public String toString() {
        return "StudentDetails{studentName=" + this.studentName + "}";
    }

    public void getStudentId(String studentDetails, StudentDetails studentDetails1) {
    }

    @Generated
    public long getStudentId() {
        return this.studentId;
    }

    @Generated
    public String getStudentName() {
        return this.studentName;
    }

    @Generated
    public String getStudentEmailId() {
        return this.studentEmailId;
    }

    @Generated
    public String getStudentDOB() {
        return this.studentDOB;
    }

    @Generated
    public String getStudentContactNo() {
        return this.studentContactNo;
    }

    @Generated
    public String getStudentPassword() {
        return this.studentPassword;
    }

    @Generated
    public String getRole() {
        return this.role;
    }

    @Generated
    public void setStudentId(final long studentId) {
        this.studentId = studentId;
    }

    @Generated
    public void setStudentName(final String studentName) {
        this.studentName = studentName;
    }

    @Generated
    public void setStudentEmailId(final String studentEmailId) {
        this.studentEmailId = studentEmailId;
    }

    @Generated
    public void setStudentDOB(final String studentDOB) {
        this.studentDOB = studentDOB;
    }

    @Generated
    public void setStudentContactNo(final String studentContactNo) {
        this.studentContactNo = studentContactNo;
    }

    @Generated
    public void setStudentPassword(final String studentPassword) {
        this.studentPassword = studentPassword;
    }

    @Generated
    public void setRole(final String role) {
        this.role = role;
    }

    @Generated
    public StudentDetails() {
    }

    @Generated
    public StudentDetails(final long studentId, final String studentName, final String studentEmailId, final String studentDOB, final String studentContactNo, final String studentPassword, final String role) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmailId = studentEmailId;
        this.studentDOB = studentDOB;
        this.studentContactNo = studentContactNo;
        this.studentPassword = studentPassword;
        this.role = role;
    }
}
