package org.lr.controller.home;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.dto.SearchPageSimpleDto;
import org.lr.entity.Term;
import org.lr.service.TermService;
import org.lr.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/term")
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping("/search")
    public Result searchTerms(@RequestParam(required = false, defaultValue = "") String name,
                              @RequestParam(required = false, defaultValue = "1") String page,
                              @RequestParam(required = false, defaultValue = "20") String pageSize){
        SearchPageSimpleDto dto = new SearchPageSimpleDto();
        dto.setName(name);
        dto.setPage(Long.valueOf(page));
        dto.setPageSize(Long.valueOf(pageSize));
        Page<Term> pages = termService.pageTerm(dto);
        PageInfoVo<Term> vo = new PageInfoVo();
        vo.setData(pages.getRecords());
        vo.setCurrentPage(pages.getCurrent());
        vo.setTotalPage(pages.getPages());
        return Result.success(vo);
    }
}
