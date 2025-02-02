package com.ca.account.manager.common.repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TASKS")
public class EmployeeTask {
    @Id
    @Column(name = "TASKID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(name = "TASK_GROUP")
    private String taskGroup;
    @Column(name = "TASK_NAME")
    private String taskName;
    @Column(name = "TASK_CHECKLIST")
    private String taskCheckList;
    @Column(name = "TASK_DUEDATE")
    private String taskDueDate;
    @Column(name = "TASK_ADV_NOTIFICATION")
    private String advanceIntimation;
    @Column(name = "TASK_REMARKS")
    private String remarks;
    @Column(name = "TASK_TYPE")
    private String taskType;
    @Column(name = "TASK_ASSIGNEDTO")
    private String taskAssignedTo;
    @Column(name = "TASK_ASSIGNED_DATE")
    private String taskAssignedDate;
    @Column(name = "TASK_REVIEWED_DATE")
    private String taskReviewedDate;
    @Column(name = "TASK_REVIEWED_BY")
    private String taskReviewedBy;
    @Column(name = "TASK_APPROVED_BY")
    private String taskApprovedBy;
    @Column(name = "TASK_APPROVED_DATE")
    private String taskApprovedDate;
    @Column(name = "TASK_STATUS")
    private String taskStatus;
}
