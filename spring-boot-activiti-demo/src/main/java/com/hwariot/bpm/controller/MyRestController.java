package com.hwariot.bpm.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwariot.bpm.service.MyService;
import com.hwariot.bpm.vo.TaskRepresentation;

@RestController
public class MyRestController {
	private final Logger logger = LoggerFactory.getLogger(MyRestController.class);
	
	@Autowired
	private MyService myService;
	
	@Autowired
	private RuntimeService runtimeService;

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public void startProcessInstance() {
		myService.startProcess();
	}

	@RequestMapping(value = "/tasks/{assignee}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskRepresentation> getTasks(@PathVariable String assignee) {
		List<Task> tasks = myService.getTasks(assignee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName(), task.getAssignee(), task.getDescription()));
		}
		return dtos;
	}

	@RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskRepresentation> getTasks() {
		List<Task> tasks = myService.getAllTasks();
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName(), task.getAssignee(), task.getDescription()));
		}

		tasks.stream().forEach(t -> {
			logger.debug(t.getId());
			logger.debug(t.getName());
			logger.debug(t.getAssignee());
			logger.debug(t.getCategory());
			logger.debug(t.getClaimTime() + "");
			logger.debug(t.getCreateTime() + "");
			logger.debug(t.getDelegationState() + "");
			logger.debug(t.getDescription());
			logger.debug(t.getOwner());
			logger.debug(t.getTaskDefinitionKey());
			logger.debug("variavles=" + runtimeService.getVariables(t.getExecutionId()));
		});
		return dtos;
	}
	
	@RequestMapping(value = "/tasks/history", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TaskRepresentation> getHisTasks() {
		List<HistoricProcessInstance> tasks = myService.getHistoryTasks();
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (HistoricProcessInstance task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName(), task.getDescription()));
		}
		return dtos;
	}
}