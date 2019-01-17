package com.chengma.devplatform.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 文件名：BaseEntity.java
 * 创建日期： 2012-6-12
 * Copyright (c) 2011-2011 广电运通
 * All rights reserved.
 * <p>
 * 修改记录：
 * 1.修改时间, 修改人：
 * 修改内容：
 */

/**
 * 统一定义id的entity基类.
 * <p>
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 子类可重载getId()函数重定义id的列名映射和生成策略.
 *
 * @author yrliang
 */
// JPA 基类的标识
@MappedSuperclass
@SuppressWarnings({"unchecked", "serial"})
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "C_ID")
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
