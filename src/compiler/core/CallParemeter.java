package compiler.core;

public class CallParemeter implements Parameter{

	Type type;
	Object value;
	
	public CallParemeter(Type type, Object value) {
		this.value = value;
		this.type = type;
	}
	
	@Override
	public Type getType() {
		return this.type;
	}
	
	public Object getValue(){
		return this.value;
	}

	@Override
	public String getIdentifier() {
		return null;
	}

}
