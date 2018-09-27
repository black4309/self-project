package com.eventbob.util.common;

import java.text.SimpleDateFormat;
import java.util.Random;

public class CommonUtil {

	public static String getDateTime(String format)
	{
		// 날짜 출력 API (구글링 참조)
		// format : yyyyMMddHHmmss
		SimpleDateFormat formatter=new SimpleDateFormat(format);	
		return formatter.format(new java.util.Date());
	}

	public static int getRandom()
	{
		Random random = new Random();
		return random.nextInt(2); // 0 , 1 중 랜덤으로 생성
	}

}
