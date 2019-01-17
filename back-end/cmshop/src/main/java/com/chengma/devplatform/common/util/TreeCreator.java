package com.chengma.devplatform.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TreeCreator {

    public static <T extends TreeNode> List<T> constructTree(List<T> list, String root) {
        return constructTree(list, root, null);
    }

    public static <T extends TreeNode> List<T> constructTree(List<T> list, String root, String attributes) {
        if (StringUtils.isNotBlank(attributes)) {
            String[] attrs = attributes.split(",");
            Class<?> cla = null;
            Map<String, Method> methodMap = new HashMap<String, Method>();
            for (T tn : list) {
                if (cla == null) {
                    cla = tn.getClass();
                }
                for (String attr : attrs) {
                    attr = attr.trim();
                    String getAttr = StringUtil.fieldToGetMethod(attr);
                    Method getMet = methodMap.get(attr);
                    if (getMet == null) {
                        try {
                            getMet = cla.getMethod(getAttr);
                            methodMap.put(attr, getMet);
                        } catch (Exception e) {
                            System.out.println("No such method!——" + getAttr);
                        }
                    }
                    try {
                        Object obj = getMet.invoke(tn);
                        tn.getAttributes().put(attr, obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        List<T> tree = new ArrayList<T>();
        for (T tn : list) {
            if (root == null) {
                if (tn.getParentId() != null) {
                    tree.add(tn);
                }
            } else {
                if (root.equals(tn.getParentId())) {
                    tree.add(tn);
                }
            }
        }
        setChildren(tree, list);
        return tree;
    }

    private static <T extends TreeNode> void setChildren(List<T> tree, List<T> list) {
        for (T tn : tree) {
            //国际化
            List<T> children = new ArrayList<T>();
            for (T child : list) {
                if (tn.getId().equals(child.getParentId())) {
                    children.add(child);
                }
            }
            if (children.size() > 0) {
                tn.setChildren((List<TreeNode>) children);
                setChildren(children, list);
            }
        }
    }

}
