package com.wfsc.actions.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.wfsc.common.bo.product.ProductCat;

public class ProductUtil {

	/**
	 * 将分类信息按层次构建成Map树形结构
	 * 
	 * @param pcList
	 * @return
	 */
	public static LinkedHashMap<Long, ProductCat> buildProductCatMap(List<ProductCat> pcList) {
		LinkedHashMap<Long, ProductCat> pcMap = new LinkedHashMap<Long, ProductCat>();
		// 转换成Map
		for (ProductCat pc : pcList) {
			pcMap.put(pc.getId(), pc);
		}
		// 将子节点挂在父节点上
		for (Map.Entry<Long, ProductCat> map : pcMap.entrySet()) {
			ProductCat cat = map.getValue();
//			System.out.println("now cat：" + cat.getName() + '-' + cat.getCode());
			// 跳过一级分类
			if (cat.getParentId() == 0) {
				continue;
			}
			long pid = cat.getParentId();
			// 添加节点到父节点
			List<ProductCat> childList = pcMap.get(pid).getChildList();
			if (childList == null) {
				childList = new ArrayList<ProductCat>();
			}
			childList.add(cat);
			pcMap.get(pid).setChildList(childList);
		}
		return pcMap;
	}

	/**
	 * 树形构建完毕后删除非根节点
	 * 
	 * @param pcMap
	 */
	@SuppressWarnings("unchecked")
	public static void clearChildCat(LinkedHashMap<Long, ProductCat> pcMap) {
		Iterator iter = pcMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			ProductCat pc = (ProductCat) entry.getValue();
			if (pc.getParentId() != 0) {
				iter.remove();
			}
		}
	}

	/**
	 * 利用序列化深度复制集合
	 * 
	 * @param src
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static List deepCopy(List src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);

		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		List dest = (List) in.readObject();
		return dest;
	}
}
