package com.snowm.cat.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class TestPasswordEncoder {

    private String rawPassword = "test123";

    private String secret = "cat2.1";

    private StandardPasswordEncoder standardPasswordEncoder1 = new StandardPasswordEncoder(secret);

    private StandardPasswordEncoder standardPasswordEncoder2 = new StandardPasswordEncoder(secret);

    @Test
    public void testStandard() {
        String encodedPassword = standardPasswordEncoder1.encode(this.rawPassword);
        assertTrue(standardPasswordEncoder1.matches(this.rawPassword, encodedPassword));
        assertTrue(standardPasswordEncoder2.matches(this.rawPassword, encodedPassword));
        
        encodedPassword = this.standardPasswordEncoder1.encode(this.rawPassword);
        assertTrue(standardPasswordEncoder1.matches(this.rawPassword, encodedPassword));
        assertTrue(standardPasswordEncoder2.matches(this.rawPassword, encodedPassword));
        
        encodedPassword = this.standardPasswordEncoder1.encode(this.rawPassword);
        assertTrue(standardPasswordEncoder1.matches(this.rawPassword, encodedPassword));
        assertTrue(standardPasswordEncoder2.matches(this.rawPassword, encodedPassword));
    }

    @Test
    public void testMatchPassword() {
        String encodedPassword = "f844e6325364a6d1beb32e5a630271089adec6feeded8a4aedd0fdf66bc3a141b2b62e8d1140579e";
        assertTrue(standardPasswordEncoder1.matches(this.rawPassword, encodedPassword));
        encodedPassword = "f844e6325364a6d1beb32e5a630271089adec6feeded8a4aedd0fdf66bc3a141b2b62e8d1140579e";
        assertTrue(standardPasswordEncoder1.matches(rawPassword, encodedPassword));
        encodedPassword = "f844e6325364a6d1beb32e5a630271089adec6feeded8a4aedd0fdf66bc3a141b2b62e8d1140579e";
        assertTrue(standardPasswordEncoder1.matches(rawPassword, encodedPassword));
        encodedPassword = "f844e6325364a6d1beb32e5a630271089adec6feeded8a4aedd0fdf66bc3a141b2b62e8d1140579e";
        assertTrue(standardPasswordEncoder1.matches(rawPassword, encodedPassword));
    }

    @Test
    public void testPassword() {
        String encodedPassword = standardPasswordEncoder1.encode(this.rawPassword);
        System.out.println("raw:" + this.rawPassword + "|encoded:" + encodedPassword);
    }
}
