package com.cheng.framework.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author: cheng
 * @time: 2022/6/26 12:37
 * @desc:
 */
public class BaseEntity {

    @Schema(name = "createTime",description = "创建时间")
    @TableField(value = "CREATETIME")
    private LocalDateTime createTime;

    @Schema(name = "createUser",description = "创建人")
    @TableField(value = "CREATEUSER")
    private Integer createUser;

    @Schema(name = "updateTime",description = "修改时间")
    @TableField(value = "UPDATETIME")
    private LocalDateTime updateTime;

    @Schema(name = "updateUser",description = "修改人")
    @TableField(value = "UPDATEUSER")
    private Integer updateUser;

    @Schema(name = "deleteTime",description = "删除时间")
    @TableField(value = "DELETETIME")
    private LocalDateTime deleteTime;

    @Schema(name = "deleteFlag",description = "删除标识1删除 0未删除")
    @TableField(value = "删除标识")
    private Integer deleteFlag;

    @Schema(name = "deleteUser",description = "删除人")
    @TableField(value = "DELETEUSER")
    private Integer deleteUser;


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public LocalDateTime getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(LocalDateTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
    }
}
