package com.ccms.hris.repositories;

import com.ccms.hris.models.entities.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer> {
}
