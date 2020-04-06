package com.xd.kobepjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Repository基类
 *  @author pxd
 *  @date 2019/10/31
 *
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 根据sql语句查询,返回T 不分页
     *
     * @param sql
     * @param params
     * @param clazz
     * @return
     * @throws Exception
     */
    List<T> findBySql(String sql, List<Object> params, Class<T> clazz) throws Exception;

    /**
     * 根据sql语句查询,返回T 分页
     *
     * @param sql
     * @param params
     * @param page
     * @param limit
     * @param clazz
     * @return
     * @throws Exception
     */
    List<T> findBySql(String sql, List<Object> params, int page, int limit, Class<T> clazz) throws Exception;

    /**
     * 根据sql语句查询,返回Map
     *
     * @param sql
     * @param params
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findMapBySql(String sql, List<Object> params, int page, int limit) throws Exception;

    /**
     * 根据sql语句查询,返回Map 不分页
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findMapBySql(String sql, List<Object> params) throws Exception;

    /**
     * 根据sql语句查询数量
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    long findTotalCountBySql(String sql, List<Object> params) throws Exception;

    /**
     * sql执行update
     *
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    int executeSql(final String sql, List<Object> params) throws Exception;
}
