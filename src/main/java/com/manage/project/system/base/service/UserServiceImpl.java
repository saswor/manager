package com.manage.project.system.base.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.common.constant.UserConstants;
import com.manage.common.support.Convert;
import com.manage.common.utils.StringUtils;
import com.manage.common.utils.security.ShiroUtils;
import com.manage.framework.shiro.service.PasswordService;
import com.manage.project.system.base.domain.Post;
import com.manage.project.system.base.mapper.PostMapper;
import com.manage.project.system.base.domain.Role;
import com.manage.project.system.base.mapper.RoleMapper;
import com.manage.project.system.base.domain.User;
import com.manage.project.system.base.domain.UserPost;
import com.manage.project.system.base.domain.UserRole;
import com.manage.project.system.base.mapper.UserMapper;
import com.manage.project.system.base.mapper.UserPostMapper;
import com.manage.project.system.base.mapper.UserRoleMapper;
import com.manage.project.system.util.BussinessCacheService;
import com.manage.project.system.util.SystemUtil;

/**
 * 用户 业务层处理
 * 
 */
@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private PasswordService passwordService;
    
    @Autowired
    private BussinessCacheService bussinessCacheService;

    /**
     * 根据条件分页查询用户对象
     * 
     * @param user 用户信息
     * 
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 通过用户名查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public User selectUserByLoginName(String userName)
    {
        return userMapper.selectUserByLoginName(userName);
    }

    /**
     * 通过手机号码查询用户
     * 
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public List<User> selectUserByPhoneNumber(String phoneNumber)
    {
        return userMapper.selectUserByPhoneNumber(phoneNumber);
    }

    /**
     * 通过邮箱查询用户
     * 
     * @param email 邮箱
     * @return 用户对象信息
     */
    @Override
    public User selectUserByEmail(String email)
    {
        return userMapper.selectUserByEmail(email);
    }

    /**
     * 通过用户ID查询用户
     * 
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public User selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户ID删除用户
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        int result = userMapper.deleteUserById(userId);
        bussinessCacheService.initUser();
        return result;
    }

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws Exception
    {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds)
        {
            if (User.isAdmin(userId))
            {
                throw new Exception("不允许删除超级管理员用户");
            }
        }
        int result = userMapper.deleteUserByIds(userIds);
        bussinessCacheService.initUser();
        return result;
    }

    /**
     * 新增保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        String corpId=user.getCorpId();
        if(StringUtils.isEmpty(corpId)) {
        	corpId=ShiroUtils.getCorpId();
        }
        user.setCorpId(corpId);
        user.setCorpName(SystemUtil.getCorpNameById(corpId));
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        bussinessCacheService.initUser();
        return rows;
    }

    /**
     * 修改保存用户信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        Long userId = user.getUserId();
        user.setUpdateBy(ShiroUtils.getLoginName());
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        int result = userMapper.updateUser(user);
        bussinessCacheService.initUser();
        return result;
    }

    /**
     * 修改用户个人详细信息
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(User user)
    {
        int result = userMapper.updateUser(user);
//        bussinessCacheService.initUser();
        SystemUtil.deleteUserCacheByUserId(user.getUserId());
        SystemUtil.deleteUserCacheByLoginNameAndCorpId(user.getLoginName(), user.getCorpId());
        SystemUtil.deleteUserCacheByLoginName(user.getLoginName());
        return result;
    }

    /**
     * 修改用户密码
     * 
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(User user)
    {
        user.randomSalt();
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        int result = updateUserInfo(user);
        bussinessCacheService.initUser();
        return result;
    }

    /**
     * 新增用户角色信息
     * 
     * @param user 用户对象
     */
    public void insertUserRole(User user)
    {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<UserRole>();
        if (user.getRoleIds() == null) {
        	return;
        }
//        for (Long roleId : user.getRoleIds())
//        {
//            UserRole ur = new UserRole();
//            ur.setUserId(user.getUserId());
//            ur.setRoleId(roleId);
//            list.add(ur);
//        }
        Long roleId = user.getRoleIds();
        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(roleId);
        list.add(ur);
        if (list.size() > 0)
        {
            userRoleMapper.batchUserRole(list);
        }
    }

    /**
     * 新增用户岗位信息
     * 
     * @param user 用户对象
     */
    public void insertUserPost(User user)
    {
        // 新增用户与岗位管理
        List<UserPost> list = new ArrayList<UserPost>();
        if (user.getPostIds() == null) {
        	return;
        }        
        for (Long postId : user.getPostIds())
        {
            UserPost up = new UserPost();
            up.setUserId(user.getUserId());
            up.setPostId(postId);
            list.add(up);
        }
        if (list.size() > 0)
        {
            userPostMapper.batchUserPost(list);
        }
        
    }

    /**
     * 校验用户名称是否唯一
     * 
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName)
    {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0)
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param phonenumber 用户名
     * @return
     */
    @Override
    public String checkPhoneUnique(User user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param email 用户名
     * @return
     */
    @Override
    public String checkEmailUnique(User user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        User info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 查询用户所属角色组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(Long userId)
    {
        List<Role> list = roleMapper.selectRolesByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Role role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(Long userId)
    {
        List<Post> list = postMapper.selectPostsByUserId(userId);
        StringBuffer idsStr = new StringBuffer();
        for (Post post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }
    
    /**
	 * 根据姓名进行模糊查询
	 * @param userName
	 * @return
	 */
    @Override
	public List<User> selectUserByUserName(String userName, String corpId,String roleId) {
    	return userMapper.selectUserByUserName(userName, corpId,roleId);
	}
    
    /**
     * 根据用户id获取所拥有的权限编码
     */
    @Override
    public List<String> selectPermsByUserId(Long userId) {
    	return userMapper.selectPermsByUserId(userId);
    }
    
    /**
     * 查询用户有哪些角色id
     * @param userId
     * @return
     */
    @Override
    public List<Long> selectRolesByUserId(Long userId) {
    	return userMapper.selectRolesByUserId(userId);
    }
    
    /**
	 * 根据用户id查用户信息
	 * @param userId
	 * @return
	 */
	public User selectByUserId(Long userId) {
		return userMapper.selectByUserId(userId);
	}

	@Override
	public List<String> selectUserNameByLoginName(String loginName) {
		return userMapper.selectUserNameByLoginName(loginName);
	}
	
	/**
     * 通过角色编号查询权限码
     * 
     * @param roleIds
     * @return
     */
	@Override
	public List<String> selectPermsByRoleId(Long roleIds) {
		return userMapper.selectPermsByRoleId(roleIds);
	}
}
