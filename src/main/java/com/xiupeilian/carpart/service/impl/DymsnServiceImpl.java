package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.DymsnMapper;
import com.xiupeilian.carpart.mapper.NoticeMapper;
import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.Notice;
import com.xiupeilian.carpart.service.DymsnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DymsnServiceImpl implements DymsnService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private DymsnMapper dymsnMapper;
    @Override
    public List<Dymsn> findDymsns() {
        return dymsnMapper.findDymsns();
    }

    @Override
    public List<Notice> findNotice() {
        return noticeMapper.findNotice();
    }
}
