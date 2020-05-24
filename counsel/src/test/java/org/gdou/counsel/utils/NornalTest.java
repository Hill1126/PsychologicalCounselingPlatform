package org.gdou.counsel.utils;

import org.gdou.common.constant.ProjectConstant;
import org.gdou.common.utils.CookieUtils;
import org.junit.Test;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/5/17
 **/
public class NornalTest {

    @Test
    public void fun(){
        Double start = 0D;
        Double end = 62.0;
        System.out.println(start.compareTo(end));
    }

    @Test
    public void cookieTest(){
        var str = "JSESSIONID=DA11B96D5753043C15F3D6F4A920782B; USER_TOKEN=360011c5-9ba1-4f0d-872e-4ac835aea383";
        String cookie = CookieUtils.getCookieInHeaderString(ProjectConstant.TOKEN_NAME, str.trim());
        System.out.println(cookie);

    }
}
