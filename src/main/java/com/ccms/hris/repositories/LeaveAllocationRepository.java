package com.ccms.hris.repositories;

import com.ccms.hris.models.entities.LeaveAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveAllocationRepository extends JpaRepository<LeaveAllocation,Integer> {
}
