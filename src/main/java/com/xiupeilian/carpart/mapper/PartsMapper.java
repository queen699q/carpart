package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.Parts;

import javax.servlet.http.Part;
import java.util.List;

public interface PartsMapper extends BaseMapper<Parts> {
    List<Parts> findPartAll();

}