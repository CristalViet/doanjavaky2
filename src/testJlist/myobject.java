package testJlist;

import javax.swing.Icon;

public class myobject {
	String name;
	String add;
	Icon icon;
	public myobject(String name, String add, Icon icon) {
		super();
		this.name = name;
		this.add = add;
		this.icon = icon;
	}
	@Override
	public String toString() {
		return "myobject [name=" + name + ", add=" + add + ", icon=" + icon + "]";
	}
	
	


	
	

}
