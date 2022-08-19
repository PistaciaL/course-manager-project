package org.lr.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lr.dto.RegisterVerifyDto;
import org.lr.entity.WorkNumber;
import org.lr.mapper.WorkNumberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkNumberService extends ServiceImpl<WorkNumberMapper, WorkNumber> {

    @Autowired
    private WorkNumberMapper workNumberMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public WorkNumber getNotRegisterWorkNumber(RegisterVerifyDto dto){
        return workNumberMapper.getNotRegisterWorkNumber(dto);
    }
}
