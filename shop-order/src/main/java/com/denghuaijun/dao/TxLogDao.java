package com.denghuaijun.dao;

import com.denghuaijun.entity.TxLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderDao
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/5/17 11:21
 * @Description 事务日志dao
 */
public interface TxLogDao extends JpaRepository<TxLog,String> {
}
