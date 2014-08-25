/**
 * 树形的结点
 */
package com.mcp.core.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class TreeNode<T> {
	
	public TreeNode(String id, T data)
	{
		this(id, data, null, null);
	}
	
	public TreeNode(String id, T data, TreeNode<T> parent,  List<TreeNode<T>> children)
	{
		this.id = id;
		this.data = data;
		this.parent = parent;
		this.children = children;
		if(this.children == null)
		{
			this.children = new ArrayList<TreeNode<T>>();
		}
	}
	
	private TreeNode<T> parent;
	
	private T data;
	
	private List<TreeNode<T>> children;
	
	private String id;
	
	/**
	 * 是否允许快捷访问，如果允许快捷访问，将被放在一个hashmap中，键为id
	 */
	private boolean allowQuickAccess;

	public boolean isAllowQuickAccess() {
		return allowQuickAccess;
	}

	public void setAllowQuickAccess(boolean allowQuickAccess) {
		this.allowQuickAccess = allowQuickAccess;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode<T>> children) {
		this.children = children;
	}
}
