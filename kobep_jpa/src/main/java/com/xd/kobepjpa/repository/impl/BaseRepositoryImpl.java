package com.xd.kobepjpa.repository.impl;

import com.xd.kobepjpa.repository.BaseRepository;
import com.xd.kobepjpa.util.NamingUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @date 2019/10/31
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    /**
     * 构造方法
     *
     * @param entityManager
     */
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;

    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }


    @Override
    public List<T> findBySql(String sql, List<Object> params, Class<T> clazz) throws Exception {
        Query query = null;

        if (sql != null && !"".equals(sql)) {
            query = entityManager.createNativeQuery(sql, clazz);
        }

        // 参数设置
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        return query.getResultList();
    }

    @Override
    public List<T> findBySql(String sql, List<Object> params, int page, int limit, Class<T> clazz) throws Exception {
        Query query = null;

        if (sql != null && !"".equals(sql)) {
            query = entityManager.createNativeQuery(sql, clazz);
        }

        // 参数设置
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        // 分页
        if (page > 0 && limit > 0) {
            int start = (page - 1) * limit;
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }

        return query.getResultList();
    }

    @Override
    public List<Map<String, Object>> findMapBySql(String sql, List<Object> params, int page, int limit) throws Exception {
        Query query = null;

        if (sql != null && !"".equals(sql)) {
            query = entityManager.createNativeQuery(sql);
        }

        // 参数设置
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        // 设置返回类型为Map
        query.unwrap(NativeQueryImpl.class).setResultTransformer(new ResultTransformer() {
            @Override
            public List transformList(List arg0) {
                // TODO Auto-generated method stub
                return arg0;
            }

            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                Map<String, Object> map = new LinkedHashMap<String, Object>(columns.length);
                int i = 0;
                for (String column : columns) {
                    //转化成驼峰格式。
                    column = NamingUtils.camelName(column);
                    if (values[i] instanceof Timestamp && values[i] != null) {
                        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(((Timestamp) values[i]).getTime() / 1000, 0, ZoneOffset.ofHours(8));
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        map.put(column, df.format(localDateTime));
                        i++;
                    } else {
                        map.put(column, values[i++]);
                    }
                }
                return map;
            }

        });

        // 分页
        if (page > 0 && limit > 0) {
            int start = (page - 1) * limit;
            query.setFirstResult(start);
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }

    @Override
    public List<Map<String, Object>> findMapBySql(String sql, List<Object> params) throws Exception {
        Query query = null;

        if (sql != null && !"".equals(sql)) {
            query = entityManager.createNativeQuery(sql);
        }

        // 参数设置
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }
        // 设置返回类型为Map
        query.unwrap(NativeQueryImpl.class).setResultTransformer(new ResultTransformer() {
            @Override
            public List transformList(List arg0) {
                // TODO Auto-generated method stub
                return arg0;
            }

            @Override
            public Object transformTuple(Object[] values, String[] columns) {
                Map<String, Object> map = new LinkedHashMap<String, Object>(columns.length);
                int i = 0;
                for (String column : columns) {
                    //转化成驼峰格式。
                    column = NamingUtils.camelName(column);
                    if (values[i] instanceof Timestamp && values[i] != null) {
                        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(((Timestamp) values[i]).getTime() / 1000, 0, ZoneOffset.ofHours(8));
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        map.put(column, df.format(localDateTime));
                        i++;
                    } else {
                        map.put(column, values[i++]);
                    }
                }
                return map;
            }

        });


        return query.getResultList();
    }

    @Override
    public long findTotalCountBySql(String sql, List<Object> params) throws Exception {
        Query query = null;
        long totalCount = 0;

        if (sql != null && !"".equals(sql)) {
            query = entityManager.createNativeQuery(sql);
        }

        // 参数设置
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }

        @SuppressWarnings("unchecked")
        List<T> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            if (resultList.get(0) instanceof Integer) {
                String listCount = resultList.get(0).toString();
                totalCount = Long.parseLong(listCount);
            } else {
                totalCount = (long) resultList.size();
            }
        }

        return totalCount;
    }

    @Override
    public int executeSql(String sql, List<Object> params) throws Exception {
        Query query = null;
        if (sql != null && !"".equals(sql)) {
            query = entityManager.createNativeQuery(sql);
        }

        // 参数设置
        if (params != null && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
        }
        return query.executeUpdate();
    }

}
