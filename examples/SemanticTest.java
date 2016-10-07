public class SemanticTest{
	
	int globalA = 10;
	int globalB;
	
	public int testOverload(){
		int a = 5;
		int b;
		return a;
	}
	
	public int testOverload(int a){
		return 10;
	}
	
	public void testTypeCreated(){
		SemanticTest t;
	}
	
	public void testVariable(){
		int a = 10;
		int b = 10;
	}
	
	public void testDeclaration(){
		globalA = 10;
	}
	
	public void testNumericExpression() {
		int a = 5 + 5;
		int b = 5 * 10;
		int c = 10 / 2;
		int d = 3 - 3;
		int e = 3 * (1 + 2);
		float f = 2.0;
	}
	
	public void testLogical(){
		boolean a = true && false;
		boolean b = true || false;
		boolean c = true ^ false;
		boolean d = !a;
		boolean e = !(b && c);
	}
	
	public void testSwitch(){
		int opcao = 10;
		switch(opcao) {
			case 10:
				int b = 10;
				break;
			case 11:
				int a = 20;
				break;
		}
	}
}
