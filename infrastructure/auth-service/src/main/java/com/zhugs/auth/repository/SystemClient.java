package com.zhugs.auth.repository;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange(url = "inner/system/user", accept = "application/json", contentType = "application/json")
public interface SystemClient {

    @GetExchange
    Map<String, Object> getUserPasswordByUsername(@RequestParam("username") String username);

}
