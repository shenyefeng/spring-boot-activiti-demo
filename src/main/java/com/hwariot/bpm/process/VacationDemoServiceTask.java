package com.hwariot.bpm.process;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VacationDemoServiceTask implements JavaDelegate {
	private final Logger log = LoggerFactory.getLogger(VacationDemoServiceTask.class);

	@Override
	public void execute(DelegateExecution execution) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("variavles=" + execution.getVariables());
		execution.setVariable("项目经理", "请假天数大约3天，同意请假。");
		log.info("项目经理，请假天数大约3天，同意请假。");

	}

}
