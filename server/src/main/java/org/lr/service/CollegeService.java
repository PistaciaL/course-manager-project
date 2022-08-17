package org.lr.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lr.entity.College;
import org.lr.mapper.CollegeMapper;
import org.springframework.stereotype.Service;

@Service
public class CollegeService extends ServiceImpl<CollegeMapper, College> {
}
