package com.manage.project.system.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.common.constant.UserConstants;
import com.manage.common.support.Convert;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.project.system.base.domain.Menu;
import com.manage.project.system.base.domain.Role;
import com.manage.project.system.base.domain.RoleMenu;
import com.manage.project.system.base.mapper.MenuMapper;
import com.manage.project.system.base.mapper.RoleMapper;
import com.manage.project.system.base.mapper.RoleMenuMapper;
import com.manage.project.system.base.mapper.UserRoleMapper;

/**
 * 角色 业务层处理
 * 
 */
@Service
public class RoleServiceImpl implements IRoleService
{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<Role> selectRoleList(Role role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId)
    {
        List<Role> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms)
        {
            if (StringUtils.isNotNull(perms))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询角色
     * 
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<Role> selectRolesByUserId(Long userId)
    {
        List<Role> userRoles = roleMapper.selectRolesByUserId(userId);
        List<Role> roles = roleMapper.selectRolesAll();
        for (Role role : roles)
        {
            for (Role userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    @Override
    public List<Role> selectRoleAll()
    {
        return roleMapper.selectRolesAll();
    }

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public Role selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public boolean deleteRoleById(Long roleId)
    {
        return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
    }

    /**
     * 批量删除角色信息
     * 
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deleteRoleByIds(String ids) throws Exception
    {
        Long[] roleIds = Convert.toLongArray(ids);
        for (Long roleId : roleIds)
        {
            Role role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new Exception(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 新增保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int insertRole(Role role)
    {
        role.setCreateBy(ShiroUtils.getLoginName());
        
        // 将menuIds转成字符串存进remark,用于给前端保存勾选项
        Long[] menuIds = role.getMenuIds();
        if (menuIds != null && menuIds.length > 0) {
        	String str1=StringUtils.join(menuIds, ",");
            role.setVmenuids(str1);
        } else {
        	role.setVmenuids("");
        }
        
        // 新增角色信息
        int result = roleMapper.insertRole(role);
        ShiroUtils.clearCachedAuthorizationInfo();
        insertRoleMenu(role);
        return result;
    }

    /**
     * 修改保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRole(Role role)
    {
        role.setUpdateBy(ShiroUtils.getLoginName());
        
     // 将menuIds转成字符串存进remark,用于给前端保存勾选项
        Long[] menuIds = role.getMenuIds();
        if (menuIds != null && menuIds.length > 0) {
        	String str1=StringUtils.join(menuIds, ",");
            role.setVmenuids(str1);
        } else {
        	role.setVmenuids("");
        }
        
        // 修改角色信息
        int result = roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        ShiroUtils.clearCachedAuthorizationInfo();
        insertRoleMenu(role);
        return result;
    }

    /**
     * 新增角色菜单信息
     * 
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role)
    {
        int rows = 1;
        // 新增用户与角色管理
        if (role.getMenuIds() == null) {
        	return 0;
        }
        List<RoleMenu> list = new ArrayList<RoleMenu>();
        
        // 取得所有父级菜单
        List<Menu> menus = menuMapper.selectMenuAll();
        Map<Long, Long> map = new HashMap<Long, Long>();
        Map<Long, String> permsMap = new HashMap<Long, String>();
    	for (Menu menu : menus) {
    		map.put(menu.getMenuId(), menu.getParentId());
    		permsMap.put(menu.getMenuId(), menu.getPerms());
    	}
        List<Long> menuIds = this.filterMenu(role.getMenuIds(), map);
        
        for (Long menuId : menuIds)
        {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            rm.setPerms(permsMap.get(menuId));
            list.add(rm);
        }
        // 先删除全量角色菜单，再添加
        //roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }
    
    public static void main(String[] args) {
    	Map<Long, Long> map = new HashMap<Long, Long>();
    	map.put(1l, 0l);
    	map.put(2l, 0l);
    	map.put(3l, 1l);
    	map.put(4l, 1l);
    	map.put(5l, 2l);
    	map.put(6l, 2l);
    	map.put(7l, 3l);
    	map.put(8l, 6l);
    	
    	Long[] menuIds = {5l, 6l, 7l, 8l};
    	
    	RoleServiceImpl r = new RoleServiceImpl();
    	List<Long> result = r.filterMenu(menuIds, map);
    	System.out.println(result);
    }
    
    private List<Long> filterMenu(Long[] menuIds, Map<Long, Long> map) {
    	List<Long> result = new ArrayList<Long>();
    	for (Long menuId : menuIds) {
    		result.add(menuId);
    		this.getMenuParent(menuId, map, result);
    	}
    	List<Long> ms = this.pastLeep3(result);
    	return ms;
    }
    
    private List<Long> getMenuParent(Long menuId, Map<Long, Long> allMenu, List<Long> result) {
    	Long parentId = allMenu.get(menuId);
    	if (parentId == 0 || parentId == null) {
    		return result;
    	}
    	result.add(parentId);
    	this.getMenuParent(parentId, allMenu, result);
    	return result;
    }
    
    /**
     * list去重
     * @param list
     * @return
     */
    private List<Long> pastLeep3(List<Long> list){
        Set set = new HashSet();
        List<Long> listNew=new ArrayList<Long>();
        set.addAll(list);
        listNew.addAll(set);
        return listNew;
    }

    /**
     * 校验角色名称是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(Role role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.ROLE_NAME_NOT_UNIQUE;
        }
        return UserConstants.ROLE_NAME_UNIQUE;
    }
    
    /**
     * 校验角色权限是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(Role role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.ROLE_KEY_NOT_UNIQUE;
        }
        return UserConstants.ROLE_KEY_UNIQUE;
    }

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }
    /**
     * 通过角色名称查角色
     */
	@Override
	public Role selectRoleByRoleName(String roleName) {
		
		return roleMapper.selectRoleByRoleName(roleName);
	}
    /**
     * 通过权限字符查角色
     */
	@Override
	public Role selectRoleByRoleKey(String roleKey) {
		
		return roleMapper.selectRoleByRoleKey(roleKey);
	}

}
