package com.example.data.controller;

import com.example.common.base.result.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dataConfig")
@RestController
public class DataConfigController {

    @GetMapping("/getData")
    public BaseResult<Boolean> getData() {
        //收集各软件的房屋出租的各种信息
        //id url strategy retry times timeout




        return BaseResult.ok();
    }

}
