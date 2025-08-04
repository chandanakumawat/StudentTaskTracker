//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.student.details.repository;

import com.student.details.modals.StudentDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDAO extends JpaRepository<StudentDetails, Long> {
    @Query("SELECT s FROM StudentDetails s WHERE s.studentEmailId = ?1 AND s.studentPassword = ?2")
    StudentDetails findByStudentEmailIdAndStudentPasswordAndRole(String studentEmailId, String studentPassword);

    @Query("SELECT COUNT(s) > 0 FROM StudentDetails s WHERE s.studentEmailId = :studentEmailId")
    boolean existsByStudentEmailId(@Param("studentEmailId") String studentEmailId);

    List<StudentDetails> findByStudentEmailId(String studentEmailId);

    List<StudentDetails> findByRole(String role);

    StudentDetails findTop1ByStudentEmailId(String studentEmailId);

    @Modifying
    @Query("UPDATE StudentDetails s SET s.studentPassword = :newPassword WHERE s.studentEmailId = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword);
}
