package org.gdou.counsel.utils;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author HILL
 * @version V1.0
 * @date 2020/5/2
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class EncryptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void fun(){
        String str = "d15f962503344d368ea68e010ee76f6c";
        String encrypt = stringEncryptor.encrypt(str);
        System.out.println(encrypt);
        String decrypt = stringEncryptor.decrypt(encrypt);
        System.out.println(decrypt);

    }
}
