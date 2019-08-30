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
     * @Description: 指定该方法的返回值，会被spring-cache cachemanager进行缓存,需要指定使用哪一个缓存
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
     * @Description: 需要定义规则，手动维护缓存(删除或清空)
     * @Author:      Administrator
     * @Param:       [id]
     * @Return       void
     **/
    @CacheEvict(value="cityimpl",allEntries = true)
    public void deleteCityById(int id) {
  cityMapper.deleteByPrimaryKey(id);
    }
}
