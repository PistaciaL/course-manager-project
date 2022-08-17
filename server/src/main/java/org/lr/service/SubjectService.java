package org.lr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lr.dto.SearchSubjectDto;
import org.lr.entity.Subject;
import org.lr.mapper.SubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectService extends ServiceImpl<SubjectMapper, Subject> {

    @Autowired
    private SubjectMapper subjectMapper;

    public <E extends IPage<Subject>> E page(SearchSubjectDto dto){
        Page<Subject> page = new Page<>(dto.getPage(),dto.getPageSize());
        return subjectMapper.selectPage(page, dto);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Subject selectById(Integer id){
        return subjectMapper.selectById(id);
    }
}
