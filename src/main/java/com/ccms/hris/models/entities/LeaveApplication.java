package com.ccms.hris.models.entities;

import com.ccms.hris.enums.LeaveRequestStatus;
import com.ccms.hris.enums.LeaveType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class LeaveApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveApplicationId;
    @Enumerated(value = EnumType.ORDINAL)
    private LeaveType leaveType;
    private String reason;
    @Enumerated(value = EnumType.ORDINAL)
    private LeaveRequestStatus leaveRequestStatus;

    @ManyToOne
    @JoinColumn(name = "user_user_id")
    private User user;

    public LeaveApplication(int leaveApplicationId, LeaveType leaveType, String reason, LeaveRequestStatus leaveRequestStatus) {
        this.leaveApplicationId = leaveApplicationId;
        this.leaveType = leaveType;
        this.reason = reason;
        this.leaveRequestStatus = leaveRequestStatus;
    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
                "leaveApplicationId=" + leaveApplicationId +
                ", leaveType=" + leaveType +
                ", reason='" + reason + '\'' +
                ", leaveRequestStatus=" + leaveRequestStatus +
                '}';
    }
}
