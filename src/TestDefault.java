
import java.io.Serializable;


public class TestDefault extends Main implements Serializable {
	/**
	 * teste
	 */
	int variable;
	int[] array;
	char[] charArray;
	
	public TestDefault(){
		this.variable = 10;
	}

	protected void begginTest(int b, float c, boolean de){
		boolean a = true;
		while(a){
			a=false;
		}
	}

	public void logicalOP() {
		boolean t = true;
		boolean f = false;
		boolean a = t && f || t != true || t == f && !f;
		boolean b = t ^ f;

	}

	public void integers() {
		int a = 10;
		int b = 20;
	}
	
	public synchronized int decrement(int c){
		return --c;
	}
	
	public void switch_case(){
		int a = 10;
		switch (a) {
		case 1:
			System.out.println(1);
			break;
		case 10:
			System.out.println(10);
			break;
		default:
			break;
		}
	}

	public void chars() {
		char a = 'A';
		char b = 'B';
	}

	public void integersOp() {
		int a = 10;
		int b = 20;
		float c = a + b - a * b / a % b;
		int d = a >> 2;
		int e = b >>> 3;
		int f = e << 2;
		float j = a /= 2;
		int g = a++;
		int p = b--;
	}

	public void floats() {
		float a =  1.5f;
		double b = 1.5986;
		float c =  0.5986f;
	}
	
	public void testThreadSafe(){
		
	}

	public void strings(){
		String a = "";
		String b = "test";
		String c = "AaDa";
		String d = "Aa";
		String e = "A12";
		String f = "09";
		String g = "Mauhsuwswijsiks wuhedywghdwujsoqwks dhywgdywqgsuqjsiqjs uwhsdywgduhsiqjsqs**";
		String h = "Mauhsuwswijsiks wuhedywghdwujsoqwks "
				+ "dhywgdywqgsuqjsiqjs uwhsMauhsuwswijsiks wuhedywghdwujsoqwks "
				+ "dhywgdywqgsuqjsiqjs uwhs "
				+ "Mauhsuwswijsiks wuhedywghdwujsoqwks dhywgdywqgsuqjsiqjs uwhs";
		g = "!:_)(*&&*)oiiis";		
	}

}