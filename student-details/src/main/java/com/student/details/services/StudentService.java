//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.student.details.services;

import com.student.details.modals.StudentDetails;
import com.student.details.modals.TaskAssign;
import com.student.details.repository.AdminDAO;
import com.student.details.repository.StudentDAO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class StudentService implements StudentInterfaceService {
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private AdminDAO adminDAO;

    public StudentDetails registerStudents(@Valid StudentDetails studentDetails) {
        try {
            return (StudentDetails)this.studentDAO.save(studentDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TaskAssign addTask(TaskAssign taskAssign) {
        try {
            return (TaskAssign)this.adminDAO.save(taskAssign);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TaskAssign> getAllTask() {
        try {
            return this.adminDAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Transactional
    public void deleteTaskById(long taskId) {
        try {
            System.out.println("Inside deleteTask Service: ID = " + taskId);
            this.adminDAO.deleteById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public TaskAssign updateTask(TaskAssign taskAssign) {
        try {
            return (TaskAssign)this.adminDAO.save(taskAssign);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TaskAssign> getTasksByStudentName(String studentName) {
        try {
            return this.adminDAO.findByStudentName(studentName);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<TaskAssign> getTasksByStudentNameAndProgress(String studentName, String progress) {
        try {
            return this.adminDAO.findByStudentNameAndProgress(studentName, progress);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<StudentDetails> getUserByStudentEmailId(String studentEmailId) {
        try {
            return this.studentDAO.findByStudentEmailId(studentEmailId);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public TaskAssign getDetailByTaskId(Long taskId) {
        try {
            return this.adminDAO.findByTaskId(taskId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<StudentDetails> getStudentByRole() {
        try {
            return this.studentDAO.findByRole("Student");
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Transactional
    public void updateTaskProgress(Long taskId, String progress) {
        try {
            this.adminDAO.updateProgressById(taskId, progress);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String processForgotPassword(String email) {
        try {
            StudentDetails studentDetails = this.studentDAO.findTop1ByStudentEmailId(email);
            return studentDetails == null ? "No account found with this email." : "Your password is: " + studentDetails.getStudentPassword();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while processing the request.";
        }
    }

    @Transactional
    public boolean updatePassword(String email, String newPassword) {
        try {
            StudentDetails student = this.studentDAO.findTop1ByStudentEmailId(email);
            if (student == null) {
                return false;
            } else {
                this.studentDAO.updatePasswordByEmail(email, newPassword);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TaskAssign> getTasksAssignedBy(String email) {
        try {
            return this.adminDAO.findByAssignBy(email);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public TaskAssign getTaskById(long taskId) {
        try {
            return (TaskAssign)this.adminDAO.findById(taskId).orElse((TaskAssign) null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
