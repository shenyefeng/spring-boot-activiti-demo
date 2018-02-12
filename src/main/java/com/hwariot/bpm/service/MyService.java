package com.hwariot.bpm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hwariot.bpm.vo.Employee;

@Service
public class MyService {
	private final Logger logger = LoggerFactory.getLogger(MyService.class);
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	IdentityService identityService;
	
	@Autowired
	HistoryService historyService;
	
	@Transactional
	public void startProcess() {
		runtimeService.startProcessInstanceByKey("oneTaskProcess");
	}

	@Transactional
	public void startVacationRequest(Employee employee) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeId", employee.getId());
		variables.put("employeeName", employee.getName());
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "实在太累了！");
		runtimeService.startProcessInstanceByKey("vacationRequest", variables);
	}

	@Transactional
	public List<Task> getTasks(String assignee) {
		return taskService.createTaskQuery().taskAssignee(assignee).list();
	}

	@Transactional
	public List<Task> getAllTasks() {
		return taskService.createTaskQuery().list();
	}

	public List<Task> getTasksByRole(Employee employee) {
		return taskService.createTaskQuery().taskCandidateGroup(employee.getRole().getName()).list();
	}
	
	public List<Task> getMyRequestedTasks(Employee employee) {
		return taskService.createTaskQuery().taskAssignee(employee.getId().toString()).list();
	}

	public void agree(String taskId, Employee employee) {
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "true");
		taskVariables.put("approver", employee.getName());
		taskVariables.put("managerMotivation", "Have a good vacation!");
		taskService.complete(taskId, taskVariables);
	}

	public void deny(String taskId, Employee employee) {
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("vacationApproved", "false");
		taskVariables.put("approver", employee.getName());
		taskVariables.put("managerMotivation", "时间紧张，项目结束再休假吧。");
		taskService.complete(taskId, taskVariables);
	}

	public List<HistoricProcessInstance> getHistoryTasks() {
		return historyService.createHistoricProcessInstanceQuery().list();
	}

}