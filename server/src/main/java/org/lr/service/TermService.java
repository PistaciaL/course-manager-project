package org.lr.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lr.dto.SearchPageSimpleDto;
import org.lr.entity.Term;
import org.lr.mapper.TermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class TermService extends ServiceImpl<TermMapper, Term> {

    private final String TERM_KEY_PREFIX = "CM:Term:";

    @Autowired
    private TermMapper mapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Term> pageTerm(SearchPageSimpleDto dto) {
        JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get(TERM_KEY_PREFIX + dto.toString());
        if(jsonObject!=null){
            return jsonObject.toJavaObject(Page.class);
        }
        Page<Term> termPage = mapper.selectPage(new Page<>(dto.getPage(), dto.getPageSize()),
                new QueryWrapper<Term>().like("name", dto.getName()).orderByDesc("start_date"));
        redisTemplate.opsForValue().set(TERM_KEY_PREFIX+ dto,termPage,3, TimeUnit.MINUTES);
        return termPage;
    }
}
