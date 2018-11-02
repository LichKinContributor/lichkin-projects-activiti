package com.lichkin.application.controllers.pages.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lichkin.application.controllers.pages.in.impl.ProcessDetailPageIn;
import com.lichkin.application.controllers.pages.in.impl.SubmitFormPageIn;
import com.lichkin.framework.defines.enums.impl.LKErrorCodesEnum;
import com.lichkin.framework.defines.exceptions.LKRuntimeException;
import com.lichkin.springframework.controllers.LKPagesController;
import com.lichkin.springframework.web.LKSession;
import com.lichkin.springframework.web.beans.LKPage;

@Controller
@RequestMapping("/employee")
public class EmployeeActivitiPagesController extends LKPagesController {

	@GetMapping("/activiti/{module}/index" + MAPPING)
	public LKPage linkTo(@PathVariable String module) {
		if (!LKSession.checkMenuAuth(request, "/index")) {
			throw new LKRuntimeException(LKErrorCodesEnum.NOT_FOUND);
		}
		return null;
	}


	@PostMapping("/activiti/{module}/index" + MAPPING)
	public LKPage jumpTo(@PathVariable String module) {
		return linkTo(module);
	}


	@GetMapping(value = "/activiti/index" + MAPPING)
	public LKPage linkTo() {
		LKPage mv = new LKPage();
		mv.putServerData("deptId", LKSession.getString(session, "deptId", ""));
		mv.putServerData("userId", LKSession.getLoginId(session) + "_" + LKSession.getCompId(session));
		mv.putServerData("tabName", request.getParameter("tabName"));
		return mv;
	}


	@GetMapping("/activiti/submitForm/index" + MAPPING)
	public LKPage submitForm(SubmitFormPageIn in) {
		LKPage mv = new LKPage();

		mv.putServerData("processConfigId", in.getProcessConfigId());
		mv.putServerData("processCode", in.getProcessCode());

		mv.putServerData("userName", LKSession.getUser(session).getUserName());
		mv.putServerData("deptName", LKSession.getString(session, "deptName", ""));
		mv.putServerData("userId", LKSession.getLoginId(session) + "_" + LKSession.getCompId(session));

		return mv;
	}


	@GetMapping(value = "/activiti/processDetail/index" + MAPPING)
	public LKPage processDetail(ProcessDetailPageIn in) {
		LKPage mv = new LKPage();

		mv.putServerData("userId", LKSession.getLoginId(session) + "_" + LKSession.getCompId(session));

		mv.putServerData("processType", in.getProcessType());
		mv.putServerData("processInstanceId", in.getProcessInstanceId());
		mv.putServerData("tabName", in.getTabName());

		return mv;
	}

}
