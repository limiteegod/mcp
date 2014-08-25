/**
 * 
 */
package com.mcp.core.util.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ming.li
 *
 */
public class Tree<T> {
	
	/**
	 * 快速访问的map
	 */
	private Map<String, TreeNode<T>> quickAccessMap = new HashMap<String, TreeNode<T>>();
	
	public Tree(String id, T data)
	{
		this.root = new TreeNode<T>(id, data);
		this.quickAccessMap.put(id, root);
	}
	
	private TreeNode<T> root;

	public TreeNode<T> getRoot() {
		return root;
	}

	public void setRoot(TreeNode<T> root) {
		this.root = root;
	}
	
	/**
	 * 新增一个节点
	 * @param parent
	 * @param data
	 */
	public void add(TreeNode<T> parent, String id, T data)
	{
		TreeNode<T> node = new TreeNode<T>(id, data);
		parent.getChildren().add(node);
		node.setParent(parent);
		this.quickAccessMap.put(id, node);
	}
	
	/**
	 * 快速访问树形的结点
	 * @param id
	 * @return
	 */
	public TreeNode<T> quickAccess(String id)
	{
		return this.quickAccessMap.get(id);
	}
	
	/**
	 * 快速访问树形的结点的数据
	 * @param id
	 * @return
	 */
	public T quickAccessData(String id)
	{
		TreeNode<T> obj = this.quickAccessMap.get(id);
		if(obj == null)
		{
			return null;
		}
		return this.quickAccessMap.get(id).getData();
	}
	
	/**
	 * 遍历树形
	 * @param node
	 */
	public void traverse(TreeNode<T> node)
	{
		String info = "";
		info += node.getId();
		TreeNode<T> parent = node.getParent();
		while(parent != null)
		{
			info += "*" + parent.getId();
			parent = parent.getParent();
		}
		System.out.println(info);
		List<TreeNode<T>> children = node.getChildren();
		for(int i = 0; i < children.size(); i++)
		{
			traverse(children.get(i));
		}
	}

}
