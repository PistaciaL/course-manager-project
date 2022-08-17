package org.lr.controller.home;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.entity.College;
import org.lr.entity.Term;
import org.lr.service.CollegeService;
import org.lr.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    @GetMapping("/search")
    public Result searchCollege(@RequestParam(required = false, defaultValue = "") String name,
                                @RequestParam(required = false, defaultValue = "1") String page,
                                @RequestParam(required = false, defaultValue = "20") String pageSize){
        Page<College> p = new Page<>(Long.parseLong(page), Long.parseLong(pageSize));
        Page<College> pages = collegeService.page(p, new QueryWrapper<College>().like("name", name));
        PageInfoVo<College> vo = new PageInfoVo();
        vo.setData(pages.getRecords());
        vo.setCurrentPage(pages.getCurrent());
        vo.setTotalPage(pages.getPages());
        return Result.success(vo);
    }
}
