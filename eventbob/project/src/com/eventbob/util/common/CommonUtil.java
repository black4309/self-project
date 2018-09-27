package com.eventbob.util.common;

import java.text.SimpleDateFormat;
import java.util.Random;

public class CommonUtil {

	public static String getDateTime(String format)
	{
		// ��¥ ��� API (���۸� ����)
		// format : yyyyMMddHHmmss
		SimpleDateFormat formatter=new SimpleDateFormat(format);	
		return formatter.format(new java.util.Date());
	}

	public static int getRandom()
	{
		Random random = new Random();
		return random.nextInt(2); // 0 , 1 �� �������� ����
	}

}
