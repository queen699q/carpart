package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.CityMapper;
import com.xiupeilian.carpart.model.City;
import com.xiupeilian.carpart.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;
    @Override
    /**
     * @Description: ָ���÷����ķ���ֵ���ᱻspring-cache cachemanager���л���,��Ҫָ��ʹ����һ������
     * @Author:      Administrator
     * @Param:       [parentId]
     * @Return       java.util.List<com.xiupeilian.carpart.model.City>
     **/
  @Cacheable(value = "cityimpl")
    public List<City> findCitiesByParentId(Integer parentId) {
        return cityMapper.findCitiesByParentId(parentId);
    }

    @Override
    /**
     * @Description: ��Ҫ��������ֶ�ά������(ɾ�������)
     * @Author:      Administrator
     * @Param:       [id]
     * @Return       void
     **/
    @CacheEvict(value="cityimpl",allEntries = true)
    public void deleteCityById(int id) {
  cityMapper.deleteByPrimaryKey(id);
    }
}
