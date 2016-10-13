package compiler.core;

import compiler.util.*;

public class Expression {
	private Type type;
	private String value;
	private String context;
	private Register register;
	
	public Expression(Type t) {
		this.type = t;
	}
	
	public Expression(String name) {
		type = new Type("UNKNOWN");
	}
	
	public Expression(Type t, String value) {
		this.type = t;
		this.value = value;
		this.context = "";
	}
	
	public Type getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getContext(){
		return this.context;
	}
	
	public void setContext(String context){
		this.context = context;
	}
	
	public void setRegister(Register register) {
		this. register = register;
	}
	
	public Register getRegister() {
		return register;
	}
	
	public boolean isNumeric() {
		return  getType().getName().equals("int")
				||getType().getName().equals("float")
				||getType().getName().equals("long")
				||getType().getName().equals("double");
	}
	
	public String toString(){
		return "Expression of type; " + getType();
	}

	public void setAssemblyValue(String value) {
		this.value = value;
	}

	public String getAssemblyValue() {
		return this.value;
	}
}
