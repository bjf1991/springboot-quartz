package com.binbinbin.controller;

import com.binbinbin.entity.TestEntity;
import com.binbinbin.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bin on 2018/4/8.
 */
@RestController
@RequestMapping("/test/")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("/add")
    public String add(TestEntity test){
        return testService.save(test);
    }
}
