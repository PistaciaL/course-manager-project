package org.lr.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lr.entity.Course;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoVo<T> {
    private List<T> data;
    private Long currentPage;
    private Long totalPage;

    public PageInfoVo(Page<T> page){
        this.data=page.getRecords();
        this.currentPage=page.getCurrent();
        this.totalPage=page.getPages();
    }
}
