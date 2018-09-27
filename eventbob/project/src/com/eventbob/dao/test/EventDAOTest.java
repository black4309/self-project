package com.eventbob.dao.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eventbob.dao.admin.AdminDAO;
import com.eventbob.dao.event.EventDAO;
import com.eventbob.dto.admin.AdminDTO;
import com.eventbob.dto.event.EventDTO;

class EventDAOTest {

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

		int testCase = 4;

		try
		{

			EventDAO eventDAO = new EventDAO();
			EventDTO eventDTO = new EventDTO();

			if(testCase == 1) // insert
			{

				eventDTO.setEventName("아이스크림 1+2");
				eventDTO.setStartDate("20180624");
				eventDTO.setStartHour(18);
				eventDTO.setQuantity(2);


				eventDAO.insert(eventDTO); 
			}

			if(testCase == 2) // update
			{
				eventDTO.setEventName("아이스크림 1+3");
				eventDTO.setStartDate("20180624");
				eventDTO.setStartHour(18);
				eventDTO.setQuantity(2);
				eventDTO.setUid(21);

				eventDAO.update(eventDTO);
			}

			if(testCase == 3) // delete
			{
				eventDAO.delete(25);
			}

			if(testCase == 4) // select
			{
				// 값
				ArrayList<EventDTO> dtoList = new ArrayList<EventDTO>();
				EventDAO eventDao = new EventDAO();
				dtoList = eventDao.select();
				
				for (int i = 0; i < dtoList.size(); i++){

					int uid = dtoList.get(i).getUid();
					String eventName = dtoList.get(i).getEventName();
					String startDate = dtoList.get(i).getStartDate();
					int startHour = dtoList.get(i).getStartHour();
					int quantity = dtoList.get(i).getQuantity();
					
					System.out.println(uid + "/" + eventName +"/" + startDate + "/" + startHour + "/" + quantity);
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
