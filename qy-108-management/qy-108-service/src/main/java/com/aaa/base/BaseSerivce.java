package com.aaa.base;


import com.aaa.utils.Map2BeanUtils;
import com.aaa.utils.SpringContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2020/5/11 15:39
 * @Description 通用service，这个泛型T指的是实体类
 * 也就是说传递什么样的实体类进来，最终所注入的mapper就是什么类型
 **/
public abstract class BaseSerivce<T> {

    private Class<T> cache = null; // 本地缓存池

    @Autowired
    private Mapper<T> mapper;

    /**
     * @param []
     * @return tk.mybatis.mapper.common.Mapper
     * @throws
     * @author Seven Lee
     * @description 上面的这个Mapper为了保证安全性，必须要是private类型，那么他的子类(UserService)就调用不到
     * 所以需要提供方法给子类用
     * @date 2020/5/11
     **/
    protected Mapper getMapper() {
        return mapper;
    }

    /**
     * @param [t]
     * @return java.lang.Integer
     * @throws
     * @author Seven Lee
     * @description 新增功能
     * @date 2020/5/11
     **/
    public Integer add(T t) throws Exception {
        return mapper.insertSelective(t);
    }

    /**
     * @param [t]
     * @return java.lang.Integer
     * @throws
     * @author Seven Lee
     * @description 通过主键删除
     * @date 2020/5/11
     **/
    public Integer delete(T t) throws Exception {
        return mapper.deleteByPrimaryKey(t);

    }

    /**
     * @param [ids]
     * @return java.lang.Integer
     * @throws
     * @author Seven Lee
     * @description 通过主键批量删除
     * 能用java代码来搞定的东西，千万不要上子查询
     * 阿里巴巴代码规约手册:
     * 如果联查的时候超过两张表，那么你一定要把这个联查拆开，放在java代码中实现
     * @date 2020/5/11
     **/
    public Integer delete(List<Object> ids) throws Exception {
        Example example = Example.builder(getTypeArguement()).where(Sqls.custom().andIn("id", ids)).build();
        return mapper.deleteByExample(example);
    }

    /**
     * @param [t]
     * @return java.lang.Integer
     * @throws
     * @author Seven Lee
     * @description 更新功能
     * @date 2020/5/11
     **/
    public Integer update(T t) throws Exception {
        return mapper.updateByPrimaryKeySelective(t);
    }

    /**
     * @param [t, ids, properties]
     * @return java.lang.Integer
     * @throws
     * @author Seven Lee
     * @description 批量更新
     * t:所要更新的数据(t代表实体类，只能存放一个id)
     * ids:通用主键来进行更新
     * @date 2020/5/11
     **/
    public Integer batchUpdate(T t, Object[] ids) throws Exception {
        Example example = Example.builder(getTypeArguement()).where(Sqls.custom().andIn("id", Arrays.asList(ids))).build();
        return mapper.updateByExample(t, example);
    }

    /**
     * @param [t]
     * @return T
     * @throws
     * @author Seven Lee
     * @description 查询一条数据
     * @date 2020/5/11
     **/
    public T queryOne(T t) throws Exception {
        return mapper.selectOne(t);
    }

    /**
     * @param [where, orderByField, fields]
     * @return T
     * @throws
     * @author Seven Lee
     * @description 通过指定字段查询一条数据
     * (有些表中有唯一键(username, iphone_num...))
     * @date 2020/5/11
     **/
    public T queryByField(Sqls where, String orderByField, String... fields) throws Exception {
        return (T) queryByFieldsBase(null, null, where, orderByField, fields).get(0);
    }

    /**
     * @param [where, orderByField, fields]
     * @return java.util.List<T>
     * @throws
     * @author Seven Lee
     * @description 条件查询集合
     * @date 2020/5/11
     **/
    public List<T> queryListByFields(Sqls where, String orderByField, String... fields) throws Exception {
        return queryByFieldsBase(null, null, where, orderByField, fields);
    }

    /**
     * @param [pageNo, pageSize, where, orderByFileds, fields]
     * @return com.github.pagehelper.PageInfo<T>
     * @throws
     * @author Seven Lee
     * @description 条件查询分页
     * @date 2020/5/11
     **/
    public PageInfo<T> queryListByPageAndFields(Integer pageNo, Integer pageSize, Sqls where, String orderByFileds, String... fields) throws Exception {
        return new PageInfo<T>(queryByFieldsBase(pageNo, pageSize, where, orderByFileds, fields));
    }

    /**
     * @param [t]
     * @return java.util.List<T>
     * @throws
     * @author Seven Lee
     * @description 条件查询
     * @date 2020/5/11
     **/
    public List<T> queryList(T t) throws Exception {
        return mapper.select(t);
    }

    /**
     * @param [t, pageNo, pageSize]
     * @return com.github.pagehelper.PageInfo<T>
     * @throws
     * @author Seven Lee
     * @description 分页查询
     * @date 2020/5/11
     **/
    public PageInfo<T> queryListByPage(T t, Integer pageNo, Integer pageSize) throws Exception {
        PageHelper.startPage(pageNo, pageSize);
        List<T> select = mapper.select(t);
        PageInfo<T> pageInfo = new PageInfo<T>(select);
        return pageInfo;
    }

    /**
     * @param [map]
     * @return T
     * @throws
     * @author Seven Lee
     * @description 根据反射获取实例对象
     * @date 2020/5/12
     **/
    public T newInstance(Map map) {
        return (T) Map2BeanUtils.map2Bean(map, getTypeArguement());
    }

    /**
     * @param [pageNo, pageSize, where, orderByField, field]
     * @return java.util.List<T>
     * @throws
     * @author Seven Lee
     * @description 封装条件查询，分页查询以及排序查询的通用方法(多条件查询)
     * @date 2020/5/11
     **/
    private List<T> queryByFieldsBase(Integer pageNo, Integer pageSize, Sqls where, String orderByField, String... field) {
        Example.Builder builder = null;
        if (null == field || field.length == 0) {
            // 没有条件查询，说明查询的是所有数据
            builder = Example.builder(getTypeArguement());
        } else {
            // 指定某些/某个字段进行查询
            builder = Example.builder(getTypeArguement())
                    .select(field);
        }
        if (null != where) {
            builder = builder.where(where);
        }

        if (null != orderByField) {
            builder = builder.orderByDesc(orderByField);
        }

        Example example = builder.build();

        if (null != pageNo && null != pageSize) { // pageHelper通用分页插件
            PageHelper.startPage(pageNo, pageSize);
        }

        List list = getMapper().selectByExample(example);
        return list;
    }

    /**
     * @param []
     * @return java.lang.Class<T>
     * @throws
     * @author Seven Lee
     * @description 获取子类泛型类型
     * @date 2020/5/11
     **/
    private Class<T> getTypeArguement() {
        if (null == cache) {
            cache = (Class<T>) ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
        }
        return cache;
    }

    /**
     * @param []
     * @return org.springframework.context.ApplicationContext
     * @throws
     * @author Seven Lee
     * @description 获取String容器
     * TODO 咱们用不到，有待补充
     * @date 2020/5/12
     **/
    public ApplicationContext getApplicationContext() {
        return SpringContextUtils.getApplicationContext();
    }

}
