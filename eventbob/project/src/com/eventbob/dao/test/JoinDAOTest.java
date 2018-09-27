package com.eventbob.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eventbob.dao.admin.AdminDAO;
import com.eventbob.dao.join.JoinDAO;
import com.eventbob.dto.admin.AdminDTO;
import com.eventbob.dto.join.JoinDTO;

class JoinDAOTest {

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
		 
		int testCase = 1;
		
		try {

			JoinDAO joinDAO = new JoinDAO();
			
			
			if(testCase == 1) // select 
			{
				ArrayList<JoinDTO> dtoList = joinDAO.select();
				for(int i=0; i<dtoList.size(); i++)
				{
					System.out.println(dtoList.get(i).getUid());
					System.out.println(dtoList.get(i).getJointime());
					System.out.println(dtoList.get(i).getIsWin());
					System.out.println(dtoList.get(i).getEventUID());
					System.out.println(dtoList.get(i).getApplicantUID());
				}
			}
			
			if(testCase == 2) // insert 
			{
				joinDAO.insert(50, 13, 1);
			}
			
			if(testCase == 3) // update
			{
				joinDAO.update(50, 13, 0);
			}
				
			
			
			
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		

		
		
	}

}
