package org.lr.controller.home;

import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.dto.SearchPageSimpleDto;
import org.lr.service.UserService;
import org.lr.vo.PageInfoVo;
import org.lr.vo.UserSimpleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/teacher/search")
    public Result searchTeacher(@RequestParam(required = false, defaultValue = "") String name,
                                @RequestParam(required = false, defaultValue = "1") String page,
                                @RequestParam(required = false, defaultValue = "20") String pageSize){
        SearchPageSimpleDto dto = new SearchPageSimpleDto();
        dto.setName(name);
        dto.setPage(Long.valueOf(page));
        dto.setPageSize(Long.valueOf(pageSize));
        PageInfoVo<UserSimpleVo> vo = userService.selectUserSimple(dto);
        return Result.success(vo);
    }
}
