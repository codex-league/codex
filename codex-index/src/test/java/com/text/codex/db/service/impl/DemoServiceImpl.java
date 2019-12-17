package com.text.codex.db.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import com.text.codex.db.mapper.DemoMapper;
import com.text.codex.db.entity.DemoEntity;
import com.text.codex.service.DemoService;


@Service("demoService")
public class DemoServiceImpl extends ServiceImpl<DemoMapper, DemoEntity> implements DemoService {


}
