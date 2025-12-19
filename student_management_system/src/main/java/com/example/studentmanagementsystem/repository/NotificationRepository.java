package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop5ByUserIdOrderByCreatedAtDesc(Long userId);
}
