/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hwariot.bpm.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.hwariot.bpm.service.MyService;
import com.hwariot.bpm.vo.Employee;
import com.hwariot.bpm.vo.TaskRepresentation;
import com.hwariot.bpm.web.ui.MessageRepository;

/**
 * @author Rob Winch
 * @author Doo-Hwan Kwak
 */
@Controller
public class ActivitiDemoController {

	private final MessageRepository messageRepository;
	
	@Autowired
	private MyService myService;

	public ActivitiDemoController(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@GetMapping("/employees/")
	public ModelAndView list() {
		Iterable<Employee> employees = this.messageRepository.findAllEmployees();
		return new ModelAndView("activiti/list", "employees", employees);
	}

	@GetMapping("/employees/{id}")
	public ModelAndView view(@PathVariable("id") Employee employee) {
		getDtos(employee);
		return new ModelAndView("activiti/view", "employee", employee);
	}

	@GetMapping(value = "/vacation/request/{id}")
	public ModelAndView startVacationRequest(@PathVariable("id") Employee employee) {
		myService.startVacationRequest(employee);
		return new ModelAndView("redirect:/employees/{id}", "id", employee.getId());
	}
	
	@GetMapping(value = "/vacation/request/agree/{id}/{employeeId}")
	public ModelAndView agree(@PathVariable("id") String taskId, @PathVariable("employeeId") Employee employee) {
		myService.agree(taskId, employee);
		return new ModelAndView("redirect:/employees/{id}", "id", employee.getId());
	}
	
	@GetMapping(value = "/vacation/request/deny/{id}/{employeeId}")
	public ModelAndView deny(@PathVariable("id") String taskId, @PathVariable("employeeId") Employee employee) {
		myService.deny(taskId, employee);
		return new ModelAndView("redirect:/employees/{id}", "id", employee.getId());
	}
	
	private void getDtos(Employee employee) {
		List<Task> tasks = myService.getTasksByRole(employee);
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName(), task.getAssignee(), task.getDescription(), task.getCreateTime()));
		}
		employee.setDtos(dtos);
		
		tasks = myService.getMyRequestedTasks(employee);
		dtos = new ArrayList<TaskRepresentation>();
		for (Task task : tasks) {
			dtos.add(new TaskRepresentation(task.getId(), task.getName(), task.getAssignee(), task.getDescription(), task.getCreateTime()));
		}
		employee.setRequests(dtos);
	}
}
