package com.ccms.hris.controllers;

import com.ccms.hris.enums.LeaveRequestStatus;
import com.ccms.hris.models.ResponseWrapper;
import com.ccms.hris.models.dto.LeaveApplicationDto;
import com.ccms.hris.models.dto.UserCreationDto;
import com.ccms.hris.models.entities.LeaveApplication;
import com.ccms.hris.models.entities.User;
import com.ccms.hris.services.LeaveAppicationService;
import com.ccms.hris.services.UserService;
import com.ccms.hris.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("leaveApplication")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LeaveApplicationController {

    @Autowired
    LeaveAppicationService leaveAppicationService;

    @Autowired
    UserService userService;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/")
    public ResponseEntity getAllLeaveApplications(){
        try {
            List<LeaveApplication> leaveApplications = leaveAppicationService.getAllLeaveAplications();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(leaveApplications, "success", "fetched"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper(null, "not found", "No Applications found"));
        }
    }

    @PostMapping("/")
    public ResponseEntity createLeaveApplication(@RequestBody LeaveApplicationDto leaveApplicationDto) {
        try {
            System.out.println(userService.getUserById(leaveApplicationDto.getUserId()).getLeaveAllocation().get(0).getTotalAllocation());
            System.out.println(userService.getUserById(leaveApplicationDto.getUserId()).getLeaveAllocation().get(1).getTotalAllocation());
            System.out.println(userService.getUserById(leaveApplicationDto.getUserId()).getLeaveAllocation().get(2).getTotalAllocation());
            if(userService.getUserById(leaveApplicationDto.getUserId()).getLeaveAllocation().get(0).getTotalAllocation() != 0 || userService.getUserById(leaveApplicationDto.getUserId()).getLeaveAllocation().get(1).getTotalAllocation() != 0 || userService.getUserById(leaveApplicationDto.getUserId()).getLeaveAllocation().get(2).getTotalAllocation() != 0){
                LeaveApplication leaveApplication = new LeaveApplication();
                leaveApplication.setLeaveType(leaveApplicationDto.getLeaveType());
                leaveApplication.setLeaveRequestStatus(LeaveRequestStatus.PENDING);
                leaveApplication.setUser(userService.getUserById(leaveApplicationDto.getUserId()));
                System.out.println(leaveApplication);
//                leaveAppicationService.createLeave(leaveApplication);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(leaveApplicationDto, "success", "created"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(leaveApplicationDto, "failed", "you don't have leaves"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper(leaveApplicationDto, "failed", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLeaveApplication (@PathVariable int id) {
        try {
            leaveAppicationService.deleteLeaveApplication(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper("", "success", "deleted"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper("", "failed", e.getMessage()));
        }
    }

    @PostMapping("/approveapplication")
    public ResponseEntity getApproveLeaveApplication(@RequestHeader(name="Authorization") String token,@RequestBody LeaveApplicationDto leaveApplicationDto){
        try {
            LeaveApplication leaveApplication = new LeaveApplication();
            leaveApplication.setLeaveRequestStatus(leaveApplicationDto.getLeaveRequestStatus());
            System.out.println(token.split(" ")[1]);
            String username = jwtUtil.extractUsername(token.split(" ")[1]);
            User user = userService.findUserByEmail(username);
            System.out.println(user.getRoles().get(0).getName());
            if (Objects.equals(user.getRoles().get(0).getName(), "ADMIN")){
                User requestedUser  = userService.getUserById(leaveApplicationDto.getUserId());
//                leaveAppicationService.approveApplication(leaveApplication);

                System.out.println("Approved: "+requestedUser.getLeaveAllocation());
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(leaveApplicationDto, "Faild", "You can't access this"));
            }
////            List<LeaveApplication> leaveApplications = leaveAppicationService.getAllLeaveAplications();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(leaveApplication, "success", "fetched"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper(null, "not found", "No Applications found"));
        }
    }

    @PostMapping("/rejectapplication")
    public ResponseEntity getRejectLeaveApplication(@RequestHeader(name="Authorization") String token,@RequestBody LeaveApplicationDto leaveApplicationDto){
        try {
            LeaveApplication leaveApplication = new LeaveApplication();
            leaveApplication.setLeaveRequestStatus(leaveApplicationDto.getLeaveRequestStatus());
            System.out.println(token.split(" ")[1]);
            String username = jwtUtil.extractUsername(token.split(" ")[1]);
            User user = userService.findUserByEmail(username);
            System.out.println(user.getRoles().get(0).getName());
            if (user.getRoles().get(0).getName() == "ADMIN"){
                leaveAppicationService.approveApplication(leaveApplication);
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(leaveApplicationDto, "Faild", "You can't access this"));
            }
//            List<LeaveApplication> leaveApplications = leaveAppicationService.getAllLeaveAplications();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseWrapper(leaveApplicationDto, "success", "fetched"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseWrapper(null, "not found", "No Applications found"));
        }
    }
}
