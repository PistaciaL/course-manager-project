package org.lr.controller.home;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.lr.api.Result;
import org.lr.dto.SearchNoticeDto;
import org.lr.service.NoticeService;
import org.lr.utils.JWTUtil;
import org.lr.vo.NoticeVo;
import org.lr.vo.PageInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/search")
    public Result selectNotice(@RequestParam(required = false, defaultValue = "1") String page,
                               @RequestParam(required = false, defaultValue = "20") String pageSize,
                               HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(token);
        SearchNoticeDto dto = new SearchNoticeDto();
        dto.setReceiverId(userId);
        dto.setPage(Long.valueOf(page));
        dto.setPageSize(Long.valueOf(pageSize));
        PageInfoVo<NoticeVo> noticeVoPageInfoVo = noticeService.selectPageNotice(dto);
        return Result.success(noticeVoPageInfoVo);
    }

    @GetMapping("/count")
    public Result countUnreadNotice(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(token);
        Integer unreadNotice = noticeService.countUnreadNotice(userId);
        return Result.success(unreadNotice);
    }
}
