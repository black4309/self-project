package com.eventbob.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eventbob.util.common.CommonUtil;

class CommonUtilTest {

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
		
		int testCase = 2;
		
		if(testCase == 1) // �ð� �׽�Ʈ
		{
			System.out.println(CommonUtil.getDateTime("yyyyMMdd"));
			System.out.println(CommonUtil.getDateTime("HH"));
		}
		
		if(testCase == 2) // Ȯ�� �׽�Ʈ
		{
			System.out.println(CommonUtil.getRandom());
		}
			
	}

}
