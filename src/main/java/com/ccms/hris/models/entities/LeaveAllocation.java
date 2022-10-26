package com.ccms.hris.models.entities;

import com.ccms.hris.enums.LeaveType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class LeaveAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int leaveAllocationId;

    @Enumerated(value = EnumType.ORDINAL)
    private LeaveType leaveType;

    private int totalAllocation;

    public LeaveAllocation(int leaveAllocationId, LeaveType leaveType, int totalAllocation) {
        this.leaveAllocationId = leaveAllocationId;
        this.leaveType = leaveType;
        this.totalAllocation = totalAllocation;
    }
    public LeaveAllocation(LeaveType leaveType, int totalAllocation) {

        this.leaveType = leaveType;
        this.totalAllocation = totalAllocation;
    }


    @Override
    public String toString() {
        return "LeaveAllocation{" +
                "leaveAllocationId=" + leaveAllocationId +
                ", leaveType=" + leaveType +
                ", totalAllocation=" + totalAllocation +
                '}';
    }
}
