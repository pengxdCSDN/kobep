package com.xd.kobepcommon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeUtils {

    private static Integer sortNumber = 0;

    /**
     * 将数据Map的集合组成树
     *
     * @param datas      map数据
     * @param parent     parent的默认数据,不可为空   一般是0或者root,也可自定义
     * @param isExpanded 是否展开  true为展开，false：为不展开
     * @param idName     形成树的节点id的key
     * @param parentName 形成树的节点parent的key
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getTree(List<Map<String, Object>> datas, Object parent, boolean isExpanded,
                                                    String idName, String parentName) throws Exception {
        List<Map<String, Object>> model_conext = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> model_childContext = new ArrayList<Map<String, Object>>();
        for (Map object : datas) {
            Object id = object.get(idName);
            Object pid = object.get(parentName);
            if (parent.equals(pid) || parent.equals(pid.toString())) {
                model_conext.add(object);
                model_childContext = getTree(datas, id, isExpanded, idName, parentName);
                if (model_childContext.size() > 0) {
                    object.put("data", model_childContext);
                    object.put("expanded", isExpanded);
                } else {
                    object.put("leaf", true);
                }

            }
        }
        return model_conext;
    }

    /**
     * 将数据Map的集合组成树 树带目录编号
     *
     * @param datas
     * @param parent
     * @param isExpanded
     * @param idName
     * @param parentName
     * @return
     * @throws Exception
     */

    public static List<Map<String, Object>> getSortTree(List<Map<String, Object>> datas, Object parent, boolean isExpanded,
                                                        String idName, String parentName) throws Exception {
        List<Map<String, Object>> model_conext = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> model_childContext = new ArrayList<Map<String, Object>>();
        Long num = 0L;
        for (Map object : datas) {
            Object id = object.get(idName);
            Object pid = object.get(parentName);
            if (parent.equals(pid) || parent.equals(pid.toString())) {
                num++;
                if (object.containsKey("sortNumber")) {
                    sortNumber = Integer.parseInt(object.get("sortNumber").toString());
                }
                if (!pid.equals(0)) {
                    object.put("sortNumber", sortNumber + "." + num.toString());
                }
                model_conext.add(object);
                model_childContext = getSortTree(datas, id, isExpanded, idName, parentName);
                if (model_childContext.size() > 0) {
                    object.put("data", model_childContext);
                    object.put("expanded", isExpanded);
                } else {
                    object.put("leaf", true);
                }
            }
        }
        return model_conext;
    }

}
