package com.xd.kobepmq.controller;


import com.xd.kobepcommon.annotation.LogMsg;
import com.xd.kobepcommon.core.Result;
import com.xd.kobepcommon.core.ResultGenerator;
import com.xd.kobepmq.entity.query.SysLogQuery;
import com.xd.kobepmq.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Api(value = "日志controller", tags = {"系统日志操作接口"})
@Slf4j
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @LogMsg(actionName = "log")
    @ApiOperation(value = "日志信息分页查询", notes = "日志信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "sysLogQuery", value = "日志信息查询类", required = false, dataType = "SysLogQuery")
    @GetMapping("/page")
    public Result<SysLogQuery> pageByQuery(SysLogQuery sysLogQuery){
        return ResultGenerator.genSuccessResult(sysLogService.pageByQuery(sysLogQuery));
    }

    @LogMsg(actionName = "test")
    @GetMapping(value = "/test")
    public String aa(){
        log.info("test");
        return "test";
    }

}