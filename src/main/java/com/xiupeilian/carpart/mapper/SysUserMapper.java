package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.model.Role;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.UserListVo;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser finUserByLoginNameAndPassword(LoginVo vo);

    SysUser selectByPrimaryKey(int id);
    SysUser findUserByLoginNameAndEmail(LoginVo vo);

    void updateUser(SysUser user);

    int findUserPgeCount(int cid);

    List<SysUser> findUserByNameAndPage(UserListVo vo);

    SysUser   findUserByLoginName( String loginName);

    SysUser findUserByPhone(String phone);

    SysUser findUserByEmail(String email);


}