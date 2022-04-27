package com.moviemang.member.encrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommonEncoder implements PasswordEncoder {

    private BCryptPasswordEncoder bCrypt;
    @Override
    public String encode(CharSequence rawPassword) {

        return bCrypt.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCrypt.matches(rawPassword,encodedPassword);
    }

    public CommonEncoder(){
        this.bCrypt = new BCryptPasswordEncoder();
    }
}
