package com.denghuaijun.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * TxLog
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/18 16:28
 * @Description 事务日志
 */
@Entity(name = "shop_txlog")
@Data
public class TxLog {

    @Id
    private String txLogId;

    private String content;

    private Date date;
}
