package compiler.core;

import compiler.util.Register;

public class Variable implements Parameter{
	
	Type type;
	String identifier;
	Expression value;
	Register register;
	
	public Variable(String identifier, Type type){
		this.type = type;
		this.identifier = identifier;
	}
	
	public Variable(String identifier, Type type, Expression value){
		this.type = type;
		this.identifier = identifier;
		this.value = value;
	}

	public Type getType() {
		return this.type;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Expression getValue() {
		return value;
	}

	public void setValue(Expression value) {
		this.value = value;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setRegister(Register register) {
		this.register = register;
		getValue().setRegister(register);
	}
	
	public Register getRegister() {
		return register;
	}
	
	
	
	
	@Override
	public String toString(){
		return this.identifier + " of type: " + getType().getName();
	}

}
