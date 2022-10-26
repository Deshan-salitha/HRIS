package com.ccms.hris.services;

import com.ccms.hris.enums.LeaveRequestStatus;
import com.ccms.hris.models.dto.LeaveApplicationDto;
import com.ccms.hris.models.dto.UserCreationDto;
import com.ccms.hris.models.entities.LeaveAllocation;
import com.ccms.hris.models.entities.LeaveApplication;
import com.ccms.hris.models.entities.User;
import com.ccms.hris.repositories.LeaveAllocationRepository;
import com.ccms.hris.repositories.LeaveApplicationRepository;
import com.ccms.hris.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveAppicationService {

    @Autowired
    LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    LeaveAllocationRepository leaveAllocationRepository;

    @Autowired
    UserService userService;



    public void createLeave(LeaveApplication leaveApplication){

        leaveApplicationRepository.save(leaveApplication);
    }
    public List<LeaveApplication> getAllLeaveAplications(){
        List<LeaveApplication> leaveApplications = leaveApplicationRepository.findAll();
        return leaveApplications;
    }
    public void approveApplication(LeaveApplication leaveApplication) throws Exception{
        Optional<LeaveApplication> leaveApplicationExist = leaveApplicationRepository.findById(leaveApplication.getLeaveApplicationId());

        if(leaveApplicationExist.isEmpty()) throw new Exception("User does not exist");
        if(leaveApplication.getLeaveRequestStatus() != null)  leaveApplicationExist.get().setLeaveRequestStatus(LeaveRequestStatus.APPROVED);
        User user = userService.getUserById(leaveApplication.getUser().getUserId());
//        user.getLeaveAllocation().get()
        leaveApplicationRepository.save(leaveApplicationExist.get());
    }
    public void rejectApplication(LeaveApplication leaveApplication)throws Exception{
        Optional<LeaveApplication> leaveApplicationExist = leaveApplicationRepository.findById(leaveApplication.getLeaveApplicationId());

        if(leaveApplicationExist.isEmpty()) throw new Exception("User does not exist");
        if(leaveApplication.getLeaveRequestStatus() != null)  leaveApplicationExist.get().setLeaveRequestStatus(LeaveRequestStatus.REJECTED);
        leaveApplicationRepository.save(leaveApplicationExist.get());
    }
    public void deleteLeaveApplication(int id) {
        leaveApplicationRepository.deleteById(id);
    }
//    public void updateLeaveApplication(LeaveApplication leaveApplication) throws Exception {
//
//        Optional<LeaveApplication> leaveApplicationExist = leaveApplicationRepository.findById(leaveApplication.getLeaveApplicationId());
//
//        if(leaveApplicationExist.isEmpty()) throw new Exception("User does not exist");
//        if(leaveApplication.getLeaveType() != null)  leaveApplicationExist.get().setLeaveType(leaveApplication.getLeaveType());
//        userRepo.save(user);
//
//    }

}
