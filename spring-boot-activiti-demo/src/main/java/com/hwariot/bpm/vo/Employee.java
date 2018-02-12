/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.hwariot.bpm.vo;

import java.util.ArrayList;
import java.util.List;

import com.hwariot.bpm.vo.TaskRepresentation;

public class Employee {

	public Employee(Long id, String name, String role) {
		this.id = id;
		this.name = name;
		this.role = new Role(role);
	}

	private Long id;

	private String name;

	private Role role;
	private List<TaskRepresentation> requests = new ArrayList<>();

	private List<TaskRepresentation> dtos = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TaskRepresentation> getDtos() {
		return dtos;
	}

	public void setDtos(List<TaskRepresentation> dtos) {
		this.dtos = dtos;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<TaskRepresentation> getRequests() {
		return requests;
	}

	public void setRequests(List<TaskRepresentation> requests) {
		this.requests = requests;
	}

}
