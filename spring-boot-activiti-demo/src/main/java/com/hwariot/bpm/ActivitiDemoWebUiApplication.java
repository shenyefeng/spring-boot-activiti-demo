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

package com.hwariot.bpm;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import com.hwariot.bpm.vo.Employee;
import com.hwariot.bpm.web.ui.InMemoryMessageRepository;
import com.hwariot.bpm.web.ui.Message;
import com.hwariot.bpm.web.ui.MessageRepository;

@SpringBootApplication
public class ActivitiDemoWebUiApplication {

	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRepository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}

	@Bean
	public Converter<String, Employee> employeeConverter() {
		return new Converter<String, Employee>() {
			@Override
			public Employee convert(String id) {
				return messageRepository().findEmployee(Long.valueOf(id));
			}
		};
	}
	
	@Bean
	public DataSource database() {
	    return DataSourceBuilder.create()
	        .url("jdbc:mysql://127.0.0.1:3306/activiti_demo?characterEncoding=UTF-8&useSSL=true")
	        .username("root")
	        .password("rootpass")
	        .driverClassName("com.mysql.jdbc.Driver")
	        .build();
	}
	
	/*@Bean
	public CommandLineRunner init(final RepositoryService repositoryService, final RuntimeService runtimeService,
			final TaskService taskService) {

		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				System.out.println("Number of process definitions : " + repositoryService.createProcessDefinitionQuery().count());
				System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
				runtimeService.startProcessInstanceByKey("oneTaskProcess");
				System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
			}
		};
	}*/

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ActivitiDemoWebUiApplication.class, args);
	}

}
