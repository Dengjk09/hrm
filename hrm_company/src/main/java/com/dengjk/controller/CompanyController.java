package com.dengjk.controller;

import com.dengjk.common.utils.Result;
import com.dengjk.common.utils.ResultUtil;
import com.dengjk.dao.CompanyRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dengjk
 * @create 2019-04-14 11:09
 * @desc
 **/
@RestController
@RequestMapping("/company")
@Api(value = "公司模块控制")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;


    @GetMapping("/findAllCompany")
    @ApiOperation("查询所有的公司")
    public Result findAllCompany() {
        return ResultUtil.success(companyRepository.findAll());
    }
}
