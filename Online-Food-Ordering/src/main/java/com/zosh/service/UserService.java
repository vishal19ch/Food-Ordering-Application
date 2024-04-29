package com.zosh.service;

import com.zosh.model.User;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.stereotype.Service;


public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;




}
