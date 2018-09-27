package com.eventbob.dao.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.eventbob.dao.admin.AdminDAO;
import com.eventbob.dto.admin.AdminDTO;


class AdminDAOTest {

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

		try
		{

			AdminDAO adminDAO = new AdminDAO();
			AdminDTO adminDTO = new AdminDTO();

			if(testCase == 1) // isExist
			{
				adminDTO.setId("admin");
				adminDTO.setPassword("1234");

				boolean isExist = adminDAO.isExist(adminDTO);
				System.out.println("°á°ú : " + isExist);
			}
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
