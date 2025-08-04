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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Generated;

@Entity
@Table(
        name = "TaskAssign"
)
public class TaskAssign {
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    @Id
    private long taskId;
    @Column(
            name = "studentName"
    )
    private String studentName;
    @Column(
            name = "taskName"
    )
    private String taskName;
    @Column(
            name = "deadline"
    )
    private @NotNull(
            message = "Deadline is required"
    ) @FutureOrPresent(
            message = "Deadline must be today or in the future"
    ) LocalDate deadline;
    @Column(
            name = "progress"
    )
    private String progress;
    @Column(
            name = "assignBy"
    )
    private String assignBy;

    public void getTaskId(String taskAssign, TaskAssign taskAssign1) {
    }

    public TaskAssign getTaskAssign() {
        return null;
    }

    @Generated
    public long getTaskId() {
        return this.taskId;
    }

    @Generated
    public String getStudentName() {
        return this.studentName;
    }

    @Generated
    public String getTaskName() {
        return this.taskName;
    }

    @Generated
    public LocalDate getDeadline() {
        return this.deadline;
    }

    @Generated
    public String getProgress() {
        return this.progress;
    }

    @Generated
    public String getAssignBy() {
        return this.assignBy;
    }

    @Generated
    public void setTaskId(final long taskId) {
        this.taskId = taskId;
    }

    @Generated
    public void setStudentName(final String studentName) {
        this.studentName = studentName;
    }

    @Generated
    public void setTaskName(final String taskName) {
        this.taskName = taskName;
    }

    @Generated
    public void setDeadline(final LocalDate deadline) {
        this.deadline = deadline;
    }

    @Generated
    public void setProgress(final String progress) {
        this.progress = progress;
    }

    @Generated
    public void setAssignBy(final String assignBy) {
        this.assignBy = assignBy;
    }

    @Generated
    public TaskAssign() {
    }

    @Generated
    public TaskAssign(final long taskId, final String studentName, final String taskName, final LocalDate deadline, final String progress, final String assignBy) {
        this.taskId = taskId;
        this.studentName = studentName;
        this.taskName = taskName;
        this.deadline = deadline;
        this.progress = progress;
        this.assignBy = assignBy;
    }
}
