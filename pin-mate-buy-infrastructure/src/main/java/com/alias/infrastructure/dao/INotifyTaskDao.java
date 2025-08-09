package com.alias.infrastructure.dao;

import com.alias.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 回调任务
 */
@Mapper
public interface INotifyTaskDao {

    void insert(NotifyTask notifyTask);

}

