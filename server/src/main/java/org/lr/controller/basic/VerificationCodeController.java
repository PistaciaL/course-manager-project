package org.lr.controller.basic;

import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.api.ResultCode;
import org.lr.dto.PicCodeDto;
import org.lr.vo.PicCodeVo;
import org.lr.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/api/verifyCode")
@Slf4j
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("/getPhoneCode")
    public Result getPhoneCode(@RequestParam String phoneNumb){
        if(verificationCodeService.sendPhoneCode(phoneNumb)){
            return new Result(ResultCode.SUCCESS);
        }
        return Result.fail();
    }

    @RequestMapping(value = "/getPicCode")
    public Result getPicCode() throws IOException {
        PicCodeDto dto = verificationCodeService.sendPicCode();
        PicCodeVo vo = new PicCodeVo(dto.getPic(), dto.getCodeId());
        return Result.success(vo);
    }
}
