package com.eventbob.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eventbob.dao.applicant.ApplicantDAO;
import com.eventbob.dto.applicant.ApplicantDTO;

class ApplicantDAOTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {

		int testCase = 3;

		try
		{

			ApplicantDAO applicantDAO = new ApplicantDAO();
			ApplicantDTO applicantDTO = new ApplicantDTO();

			if(testCase == 1) // isExist
			{
				applicantDTO.setName("홍길동");
				applicantDTO.setTel("010-1111-2222");

				boolean isExist = applicantDAO.isExist(applicantDTO);
				System.out.println("결과 : " + isExist);
			}
			if(testCase == 2) // insert
			{
				
				applicantDTO.setName("트럼프");
				applicantDTO.setTel("010-2020-3030");
				
				applicantDAO.insert(applicantDTO); 

			}
			
			
			if(testCase == 3) // select
			{
				
				ArrayList<ApplicantDTO> dtoList = applicantDAO.select();
				
				for (int i = 0; i < dtoList.size(); i++){

					String name = dtoList.get(i).getName();
					String tel = dtoList.get(i).getTel();
					
					System.out.println(name + "/" + tel);
				}
				
				

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
