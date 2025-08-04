//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.student.details.controller;

import com.student.details.modals.StudentDetails;
import com.student.details.modals.TaskAssign;
import com.student.details.repository.StudentDAO;
import com.student.details.services.StudentInterfaceService;
import com.student.details.services.StudentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {
    @Autowired
    private StudentInterfaceService studentInterfaceService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentDAO studentDAO;

    @GetMapping({"/"})
    public String landing(Model model) {
        try {
            return "landingAndProfilePages/landingPage";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Unable to load the landing page.");
            return "error";
        }
    }

    @GetMapping({"/login"})
    public String login(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("studentDetails", new StudentDetails());
            String rememberedPassword = null;
            String rememberedEmail = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for(Cookie cookie : cookies) {
                    if ("rememberedEmail".equals(cookie.getName())) {
                        rememberedEmail = cookie.getValue();
                        System.out.println("rememberedEmail: " + rememberedEmail);
                    }

                    if ("rememberedPassword".equals(cookie.getName())) {
                        rememberedPassword = cookie.getValue();
                        System.out.println("rememberedPassword: " + rememberedPassword);
                    }
                }
            }

            model.addAttribute("rememberedEmail", rememberedEmail);
            model.addAttribute("rememberedPassword", rememberedPassword);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Something went wrong while loading the login page.");
            return "index";
        }
    }

    @PostMapping({"/loginForm"})
    public String processLogin(@RequestParam String studentEmailId, @RequestParam String studentPassword, @RequestParam(required = false) String rememberMe, HttpSession session, HttpServletResponse response, Model model) {
        try {
            StudentDetails studentDetails = this.studentDAO.findByStudentEmailIdAndStudentPasswordAndRole(studentEmailId, studentPassword);
            if (studentDetails != null && this.studentDAO.existsByStudentEmailId(studentDetails.getStudentEmailId())) {
                session.setAttribute("loggedInUser", studentDetails);
                if (rememberMe != null) {
                    Cookie emailCookie = new Cookie("rememberedEmail", studentEmailId);
                    Cookie passwordCookie = new Cookie("rememberedPassword", studentPassword);
                    emailCookie.setMaxAge(604800);
                    passwordCookie.setMaxAge(604800);
                    emailCookie.setPath("/");
                    passwordCookie.setPath("/");
                    response.addCookie(emailCookie);
                    response.addCookie(passwordCookie);
                    System.out.println("cookie: " + emailCookie.getValue());
                    System.out.println("cookie: " + passwordCookie.getValue());
                } else {
                    Cookie emailCookie = new Cookie("rememberedEmail", (String)null);
                    Cookie passwordCookie = new Cookie("rememberedPassword", (String)null);
                    emailCookie.setMaxAge(0);
                    passwordCookie.setMaxAge(0);
                    emailCookie.setPath("/");
                    passwordCookie.setPath("/");
                    response.addCookie(emailCookie);
                    response.addCookie(passwordCookie);
                }

                if ("Admin".equals(studentDetails.getRole())) {
                    return "redirect:/adminDashboard";
                } else {
                    String encodedName = URLEncoder.encode(studentDetails.getStudentName(), StandardCharsets.UTF_8);
                    return "redirect:/studentDashboard?studentName=" + encodedName;
                }
            } else {
                model.addAttribute("error", "Invalid credentials");
                return "index";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred during login. Please try again.");
            return "index";
        }
    }

    @GetMapping({"/signup"})
    public String showSignupPage(StudentDetails studentDetailsmodel, Model model) {
        try {
            model.addAttribute("studentDetails", new StudentDetails());
            return "signUp";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/signup"})
    public String processSignup(@ModelAttribute("studentDetails") @Valid StudentDetails studentDetails, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                return "signUp";
            } else if (this.studentDAO.existsByStudentEmailId(studentDetails.getStudentEmailId())) {
                model.addAttribute("errorMessage", "User already registered with this email.");
                model.addAttribute("studentDetails", new StudentDetails());
                return "signUp";
            } else {
                this.studentInterfaceService.registerStudents(studentDetails);
                redirectAttributes.addFlashAttribute("successMessage", "You have registered successfully!");
                return "redirect:/landing";
            }
        } catch (Exception e) {
            System.err.println("Error during signup: " + e.getMessage());
            return "signUp";
        }
    }

    @GetMapping({"/profile"})
    public String profile(HttpSession session, Model model) {
        try {
            StudentDetails student = (StudentDetails)session.getAttribute("loggedInUser");
            if (student == null) {
                return "redirect:/adminDashboard";
            } else {
                model.addAttribute("userList", student);
                model.addAttribute("role", student.getRole());
                model.addAttribute("studentName", student.getStudentName());
                return "landingAndProfilePages/profile";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping({"/profileIcon"})
    public String profileIcon() {
        try {
            return "landingAndProfilePages/profileIcon";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping({"/studentDashboard"})
    public String studentDashboard(@RequestParam("studentName") String studentName, Model model) {
        try {
            List<TaskAssign> taskAssign = this.studentService.getTasksByStudentName(studentName);
            List<TaskAssign> notStarted = this.studentService.getTasksByStudentNameAndProgress(studentName, "Not Started");
            List<TaskAssign> inProgress = this.studentService.getTasksByStudentNameAndProgress(studentName, "In Progress");
            List<TaskAssign> completed = this.studentService.getTasksByStudentNameAndProgress(studentName, "Completed");
            List<TaskAssign> delayed = this.studentService.getTasksByStudentNameAndProgress(studentName, "Delayed");
            model.addAttribute("taskList", taskAssign);
            model.addAttribute("notStartedTasks", notStarted);
            model.addAttribute("inProgressTasks", inProgress);
            model.addAttribute("completedTasks", completed);
            model.addAttribute("delayedTasks", delayed);
            model.addAttribute("studentName", studentName);
            return "studentDashboard";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while loading the student dashboard.");
            return "index";
        }
    }

    @GetMapping({"/taskDetailForStudent"})
    public String taskDetailForStudent() {
        try {
            return "student/taskDetailForStudent";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping({"/adminDashboard"})
    public String adminDashboard(String role, HttpSession session, Model model) {
        try {
            StudentDetails studentDetails = (StudentDetails)session.getAttribute("loggedInUser");
            if (studentDetails == null) {
                return "redirect:/login";
            } else if (!"Admin".equals(studentDetails.getRole())) {
                return "access-denied";
            } else {
                String adminEmail = studentDetails.getStudentEmailId();
                List<StudentDetails> student = this.studentService.getStudentByRole();
                List<TaskAssign> tasks = this.studentInterfaceService.getTasksAssignedBy(adminEmail);
                model.addAttribute("taskAssign", new TaskAssign());
                model.addAttribute("listTask", tasks);
                model.addAttribute("students", student);
                return "adminDashboard";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "An unexpected error occurred while loading the dashboard.");
            return "error";
        }
    }

    @GetMapping({"/taskDetailForAdmin/{taskId}"})
    public String taskDetailForAdmin(@PathVariable Long taskId, Model model) {
        try {
            TaskAssign taskAssign = this.studentService.getDetailByTaskId(taskId);
            model.addAttribute("taskAssign", taskAssign);
            return "taskDetailForAdmin";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping({"/access-denied"})
    public String showAccessDenied() {
        try {
            return "access-denied";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/assignTask"})
    public String assignTask(@ModelAttribute @Valid TaskAssign taskAssign, BindingResult result, Model model, HttpSession session) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("students", this.studentService.getStudentByRole());
                return "assignTask";
            } else {
                StudentDetails loggedInUser = (StudentDetails)session.getAttribute("loggedInUser");
                if (loggedInUser != null) {
                    taskAssign.setAssignBy(loggedInUser.getStudentEmailId());
                    System.out.println("Assigning task by: " + loggedInUser.getStudentEmailId());
                    this.studentInterfaceService.addTask(taskAssign);
                    return "redirect:/adminDashboard";
                } else {
                    return "redirect:/login";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/editTask"})
    public String updatedTask(@ModelAttribute TaskAssign taskAssign, HttpSession session) {
        try {
            if (taskAssign.getTaskId() != 0L) {
                TaskAssign existingTask = this.studentInterfaceService.getTaskById(taskAssign.getTaskId());
                if (existingTask != null) {
                    StudentDetails loggedInUser = (StudentDetails)session.getAttribute("loggedInUser");
                    if (loggedInUser != null && !existingTask.getAssignBy().equals(loggedInUser.getStudentEmailId())) {
                        return "error/403";
                    }

                    existingTask.setStudentName(taskAssign.getStudentName());
                    existingTask.setTaskName(taskAssign.getTaskName());
                    existingTask.setDeadline(taskAssign.getDeadline());
                    existingTask.setProgress(taskAssign.getProgress());
                    this.studentInterfaceService.updateTask(existingTask);
                }
            }

            return "redirect:/adminDashboard";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/deleteTask"})
    public String deleteTask(@RequestParam(name = "taskId",required = false) Long taskId) {
        try {
            if (taskId == null) {
                System.out.println("Error: taskId is null during deletion.");
                return "redirect:/adminDashboard";
            } else {
                System.out.println("Deleting Task with ID: " + taskId);
                this.studentService.deleteTaskById(taskId);
                return "redirect:/adminDashboard";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/logout"})
    public String logout(HttpSession session) {
        try {
            session.invalidate();
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/updateTaskStatus"})
    @ResponseBody
    public ResponseEntity<String> updateTaskStatus(@RequestBody Map<String, String> payload) {
        System.out.println("Received request to update task: " + String.valueOf(payload));

        try {
            Long taskId = Long.parseLong((String)payload.get("taskId"));
            String progress = (String)payload.get("progress");
            this.studentService.updateTaskProgress(taskId, progress);
            return ResponseEntity.ok("Progress updated successfully");
        } catch (Exception var4) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating progress");
        }
    }

    @GetMapping({"/forgot-password"})
    public String showForgotPasswordForm() {
        try {
            return "forgotPassword";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/forgot-password"})
    public String handleForgotPassword(@RequestParam String email, Model model) {
        try {
            String result = this.studentService.processForgotPassword(email);
            model.addAttribute("message", result);
            return "forgotPassword";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping({"/reset-password"})
    public String resetPassword(@RequestParam String email, @RequestParam String newPassword, Model model) {
        try {
            boolean updated = this.studentService.updatePassword(email, newPassword);
            model.addAttribute("message", updated ? "Password updated successfully." : "Failed to update password.");
            return "forgotPassword";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping({"/validate-email"})
    @ResponseBody
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
        try {
            boolean exists = this.studentDAO.existsByStudentEmailId(email);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @GetMapping({"/studentTaskSection"})
    public String getUpdatedSection(@RequestParam("section") String section, @RequestParam("studentName") String studentName, Model model) {
        try {
            switch (section) {
                case "completedTask":
                    List<TaskAssign> updatedTasks = this.studentService.getTasksByStudentNameAndProgress(studentName, "Completed");
                    model.addAttribute("completedTasks", updatedTasks);
                    return "fragments/completedTask :: completedTaskTable";
                case "notStartedTask":
                    updatedTasks = this.studentService.getTasksByStudentNameAndProgress(studentName, "Not Started");
                    model.addAttribute("notStartedTasks", updatedTasks);
                    return "fragments/notStartedTask :: notStartedTaskTable";
                case "inProgressTask":
                    updatedTasks = this.studentService.getTasksByStudentNameAndProgress(studentName, "In Progress");
                    model.addAttribute("inProgressTasks", updatedTasks);
                    return "fragments/inProgressTask :: inProgressTaskTable";
                case "delayedTask":
                    updatedTasks = this.studentService.getTasksByStudentNameAndProgress(studentName, "Delayed");
                    model.addAttribute("delayedTasks", updatedTasks);
                    return "fragments/delayedTask :: delayedTaskTable";
                default:
                    return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "fragments/errorFragment :: errorMessage";
        }
    }

    @PostMapping({"/changePassword"})
    public String changePassword(@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, HttpSession session, Model model) {
        try {
            StudentDetails student = (StudentDetails)session.getAttribute("loggedInUser");
            if (student == null) {
                return "redirect:/login";
            } else if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("passwordError", "Passwords do not match.");
                model.addAttribute("userList", student);
                model.addAttribute("role", student.getRole());
                model.addAttribute("studentName", student.getStudentName());
                return "landingAndProfilePages/profile";
            } else if (newPassword.length() < 8) {
                model.addAttribute("passwordError", "Password must be at least 8 characters.");
                model.addAttribute("userList", student);
                model.addAttribute("role", student.getRole());
                model.addAttribute("studentName", student.getStudentName());
                return "landingAndProfilePages/profile";
            } else {
                this.studentService.updatePassword(student.getStudentEmailId(), newPassword);
                model.addAttribute("passwordSuccess", true);
                model.addAttribute("userList", student);
                model.addAttribute("role", student.getRole());
                model.addAttribute("studentName", student.getStudentName());
                return "landingAndProfilePages/profile";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
