package com.ishmamruhan.application.backend.DAO;

import com.ishmamruhan.application.backend.DTO.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskDao extends JpaRepository<Task, UUID> {
}
