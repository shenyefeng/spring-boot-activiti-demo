/*
 * Copyright 2012-2015 the original author or authors.
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

package com.hwariot.bpm.web.ui;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.hwariot.bpm.vo.Employee;

/**
 * @author Dave Syer
 */
public class InMemoryMessageRepository implements MessageRepository {

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<Long, Message> messages = new ConcurrentHashMap<Long, Message>();
	
	private final ConcurrentMap<Long, Employee> employees = new ConcurrentHashMap<Long, Employee>();

	public InMemoryMessageRepository() {
		super();
		saveEmployee(new Employee(1L, "张三", "employee"));
		saveEmployee(new Employee(2L, "李四", "management"));
		saveEmployee(new Employee(3L, "王五", "seniorManagement"));
		saveEmployee(new Employee(4L, "赵六", "seniorManagement2"));
	}

	@Override
	public Iterable<Employee> findAllEmployees() {
		return this.employees.values();
	}
	
	@Override
	public Iterable<Message> findAll() {
		return this.messages.values();
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		Long id = employee.getId();
		if (id == null) {
			id = counter.incrementAndGet();
			employee.setId(id);
		}
		this.employees.put(id, employee);
		return employee;
	}
	
	@Override
	public Message save(Message message) {
		Long id = message.getId();
		if (id == null) {
			id = counter.incrementAndGet();
			message.setId(id);
		}
		this.messages.put(id, message);
		return message;
	}

	@Override
	public Message findMessage(Long id) {
		return this.messages.get(id);
	}
	
	@Override
	public void deleteMessage(Long id) {
		this.messages.remove(id);
	}

	@Override
	public Employee findEmployee(Long id) {
		return this.employees.get(id);
	}

}
