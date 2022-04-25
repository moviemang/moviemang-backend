package com.moviemang.member.util;

import java.util.Random;

public class CreateCertificationUtil {

	// 난수를 생성하는 메소드
	public static String create(boolean lowerCheck, int size) {
		Random ran = new Random();
	    StringBuffer sb = new StringBuffer();
	    int num = 0;

	    do {
	       num = ran.nextInt(75) + 48;
	       if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
	          sb.append((char) num);
	       } else {
	          continue;
	       }

	    } while (sb.length() < size);
	    if (lowerCheck) {
	    	return sb.toString().toLowerCase();
	    }
	    
	    return sb.toString();
	}
	
}
