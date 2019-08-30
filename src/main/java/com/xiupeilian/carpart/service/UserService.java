package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Role;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;
import com.xiupeilian.carpart.vo.UserListVo;

import java.util.List;

public interface UserService {
    SysUser findUserByloginNameAndPassword(LoginVo vo);
    List<Menu> findMenusById(int id);

    SysUser findUserByLoginNameAndEmail(LoginVo vo);

    void updateUser(SysUser user);

    int findUserPgeCount(int cid);

    List<SysUser> findUserByNameAndPage(UserListVo vo);

    SysUser findUserByLoginName(String loginName);

    SysUser findUserByPhone(String phone);

    SysUser findUserByEmail(String email);

    Company findCompanyByName(String companyname);

    void addRegsiter(RegisterVo vo);

    Role findRoleByRoleId(Integer roleId);
}
