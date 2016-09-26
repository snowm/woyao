package com.woyao.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class TestPasswordEncoder {

    private String rawPwd = "test123";

    private String secret = "woyao$-1.0";
    
    private String encodedPwd = "44ea86d26563cd7ae457366c63a93567685c4272c608367aaa026a4f6f3313c4ec472fe37f85d884";

    private StandardPasswordEncoder standardPasswordEncoder1 = new StandardPasswordEncoder(secret);

    private StandardPasswordEncoder standardPasswordEncoder2 = new StandardPasswordEncoder(secret);

    @Test
    public void testStandard() {
        String encodedPassword = standardPasswordEncoder1.encode(this.rawPwd);
        assertTrue(standardPasswordEncoder1.matches(this.rawPwd, encodedPassword));
        assertTrue(standardPasswordEncoder2.matches(this.rawPwd, encodedPassword));
        
        encodedPassword = this.standardPasswordEncoder1.encode(this.rawPwd);
        assertTrue(standardPasswordEncoder1.matches(this.rawPwd, encodedPassword));
        assertTrue(standardPasswordEncoder2.matches(this.rawPwd, encodedPassword));
        
        encodedPassword = this.standardPasswordEncoder1.encode(this.rawPwd);
        assertTrue(standardPasswordEncoder1.matches(this.rawPwd, encodedPassword));
        assertTrue(standardPasswordEncoder2.matches(this.rawPwd, encodedPassword));
    }

    @Test
    public void testMatchPassword() {
        String encodedPassword = this.encodedPwd;
        assertTrue(standardPasswordEncoder1.matches(this.rawPwd, encodedPassword));
        assertTrue(standardPasswordEncoder1.matches(rawPwd, encodedPassword));
        assertTrue(standardPasswordEncoder1.matches(rawPwd, encodedPassword));
        assertTrue(standardPasswordEncoder1.matches(rawPwd, encodedPassword));
    }

    @Test
    public void testPassword() {
        String encodedPassword = standardPasswordEncoder1.encode(this.rawPwd);
        System.out.println("raw:" + this.rawPwd + "|encoded:" + encodedPassword);
    }
}
