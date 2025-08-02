//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.student.details.services;

import com.student.details.modals.StudentDetails;
import com.student.details.modals.TaskAssign;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Validated
public interface StudentInterfaceService {
    StudentDetails registerStudents(@Valid StudentDetails studentDetails);

    TaskAssign addTask(TaskAssign taskAssign);

    List<TaskAssign> getAllTask();

    void deleteTaskById(long taskId);

    TaskAssign updateTask(TaskAssign taskAssign);

    List<TaskAssign> getTasksByStudentName(String studentName);

    List<TaskAssign> getTasksByStudentNameAndProgress(String studentName, String progress);

    List<StudentDetails> getUserByStudentEmailId(String studentEmailId);

    TaskAssign getDetailByTaskId(Long taskId);

    List<StudentDetails> getStudentByRole();

    void updateTaskProgress(Long taskId, String progress);

    List<TaskAssign> getTasksAssignedBy(String email);

    TaskAssign getTaskById(long taskId);
}
