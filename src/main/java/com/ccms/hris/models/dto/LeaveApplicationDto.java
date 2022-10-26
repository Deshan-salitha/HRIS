package com.ccms.hris.models.dto;

import com.ccms.hris.enums.LeaveRequestStatus;
import com.ccms.hris.enums.LeaveType;
import com.ccms.hris.models.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplicationDto {
    private int leaveApplicationId;
    private LeaveType leaveType;
    private String reason;
    private LeaveRequestStatus leaveRequestStatus;
    private Long userId;
}
