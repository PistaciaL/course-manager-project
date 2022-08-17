package org.lr.controller.home;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.dto.SearchSubjectDto;
import org.lr.entity.Subject;
import org.lr.service.SubjectService;
import org.lr.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

@RestController
@Slf4j
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/search")
    public Result searchSubject(@RequestParam(required = false, defaultValue = "") String name,
                                @RequestParam(required = false, defaultValue = "1") String page,
                                @RequestParam(required = false, defaultValue = "20") String pageSize,
                                @RequestParam(required = false) String collegeId) throws InterruptedException {
        SearchSubjectDto dto = new SearchSubjectDto();
        dto.setName(name);
        dto.setPage(Long.valueOf(page));
        dto.setPageSize(Long.valueOf(pageSize));
        if(!StringUtil.isNullOrEmpty(collegeId)){
            dto.setCollegeId(Integer.valueOf(collegeId));
        }
        Page<Subject> pages = subjectService.page(dto);
        PageInfoVo<Subject> vo = new PageInfoVo();
        vo.setData(pages.getRecords());
        vo.setCurrentPage(pages.getCurrent());
        vo.setTotalPage(pages.getPages());
        return Result.success(vo);
    }
}
