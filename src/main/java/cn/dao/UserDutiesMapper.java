package cn.dao;

import cn.entity.UserDuties;

import java.util.List;

public interface UserDutiesMapper {
    /**
     *
     * @mbggenerated 2017-05-18
     */
    int insert(UserDuties record);

    /**
     *
     * @mbggenerated 2017-05-18
     */
    int insertSelective(UserDuties record);

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
    UserDuties selectByName(String userName);

    /**
     * 通过组织名查询组织人员
     * @param organizationName
     * @return
     */
    List<UserDuties> selectByOrganizationName(String organizationName);

    /**
     * 查找全部
     * @return
     */
    List<UserDuties> selectAll();

    UserDuties selectByUserId(Long userId);
    /**
     * 根据组织id查找组织里所有的人员名字
     * @param organizationId
     * @return
     */
    List<String> selectNameByOrganizationid(String organizationId);
    /**
     * 返回所有的人员名称
     * @return
     */
    List<String> selectNameAll();
}