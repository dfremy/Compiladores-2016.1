public class SemanticTest{
	
	int a = 10;
	int b;
	
	public int testOverload(){
		int a = 5;
		return 5;
	}
	
	public int testOverload(int a){
		return 5;
	}
	
	public void testTypeCreated(){
		SemanticTest t;
	}
	
	public void testVariable(){
		int a = 10;
		int b = 10;
	}
	
	public void testDeclaration(){
		a = 10;
	}
	
	public void testNumericExpression() {
		int a = 5 + 5;
		int e = (3*(1 + true));
		int b = 5 * 10;
		int c = 10 / 2;
		int d = 3 - 3;
	}
	
	public void testLogical(){
		boolean a = true && false;
		boolean b = true || false;
		boolean c = true ^ false;
		boolean d = !a;
		boolean e = !(b && c);
		boolean f = e;
	}
	
	public void testSwitch(){
		switch("ola Franklin") {
			case "ola":
				int b = 10;
				break;
			case "Franklin":
				int a = 20;
				break;
		}
	}
}
