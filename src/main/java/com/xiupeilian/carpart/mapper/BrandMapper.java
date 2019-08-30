package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Prime;

import javax.servlet.http.Part;
import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {
    List<Brand> findBrandAll();



}