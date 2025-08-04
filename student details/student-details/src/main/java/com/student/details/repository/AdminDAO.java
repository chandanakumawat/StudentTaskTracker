//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.student.details.repository;

import com.student.details.modals.TaskAssign;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDAO extends JpaRepository<TaskAssign, Long> {
    List<TaskAssign> findByStudentName(String studentName);

    List<TaskAssign> findByStudentNameAndProgress(String studentName, String progress);

    TaskAssign findByTaskId(Long taskId);

    @Modifying
    @Query("UPDATE TaskAssign t SET t.progress = :progress WHERE t.taskId = :taskId")
    void updateProgressById(@Param("taskId") Long taskId, @Param("progress") String progress);

    List<TaskAssign> findByAssignBy(String assignBy);
}
