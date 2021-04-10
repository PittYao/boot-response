package com.fanyao.boot.response;

import com.fanyao.boot.response.enums.ResultEnum;
import com.fanyao.boot.response.exception.BusinessException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class BootResponseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootResponseApplication.class, args);
    }

    @GetMapping("/")
    public CommonResult<String> test() {
        return CommonResult.error(1000, "服务器异常", "");
    }

    @GetMapping("/list")
    public CommonResult<List<String>> testList() {
        List<String> strings = new ArrayList<>();
        strings.add("未统一响应体1");
        strings.add("未统一响应体2");
        return CommonResult.success("查询成功", strings);
    }

    @GetMapping("/error")
    public String testString() {
        throw new BusinessException(ResultEnum.ERROR);
    }

    @GetMapping("/errorCustomize")
    public String testErrorCustomize() {
        throw new BusinessException(ResultEnum.ERROR_Customize);
    }

}
