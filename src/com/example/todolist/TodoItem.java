package com.example.todolist;

import java.io.Serializable;

public class TodoItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7571026170589588634L;
	private String todobody;
	private boolean checked=false;
	private int archived;
	//private static int total
	
	TodoItem(String name){
		this.todobody=name;
	}
	public String getBody(){
		return this.todobody;
	}
	public boolean getChecked(){
		return this.checked;
	}
	public void setCheck(boolean check){
		this.checked=check;
	}

	public void archive(){
		this.archived=1;
	}
	public void unarchive(){
		this.archived=0;
	}
	public int getArchived(){
		return archived;
	}
	
}
