package com.eventbob.main;

import com.eventbob.ui.admin.AdminLogin;
import com.eventbob.ui.applicant.ApplicantLogin;
import com.eventbob.util.common.CommonUtil;

public class EventBob {

	public static void main(String[] args) {
		
		int admin = 1;
		
		if(admin == 1) // 관리자
		{
			AdminLogin adminLogin = new AdminLogin();
			adminLogin.open();
		}
		else // 응모자
		{
			ApplicantLogin applicantLogin = new ApplicantLogin();
			applicantLogin.open();
		}
	}
}
