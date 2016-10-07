package compiler.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import compiler.core.Expression;
import compiler.core.Function;
import compiler.core.Operation;
import compiler.core.Parameter;
import compiler.core.Program;
import compiler.core.ScopedEntity;
import compiler.core.Type;
import compiler.core.Variable;
import compiler.exception.InvalidFunctionCallException;
import compiler.exceptions.InvalidFunctionException;
import compiler.exceptions.InvalidOperationException;
import compiler.exceptions.InvalidOperatorException;
import compiler.exceptions.InvalidParameterException;
import compiler.exceptions.InvalidTypeAssignmentException;
import compiler.exceptions.InvalidTypeException;
import compiler.exceptions.InvalidVariableException;
import compiler.generator.CodeGenerator;
import compiler.util.Calculator;
import compiler.util.Register;

public class SemanticImpl {

	private HashMap<String, Variable> variables = new HashMap<String, Variable>();
	private List<Type> secondaryTypes = new ArrayList<Type>();
	private ArrayList<Function> functions = new ArrayList<Function>();
	private List<Variable> tempVariables = new ArrayList<Variable>();
	private Stack<ScopedEntity> scopeStack = new Stack<ScopedEntity>();
	private Expression switchExpression;
	Program jProgram = new Program();
	private static Map<String, List<String>> tiposCompativeis = new HashMap<String, List<String>>();
	private static List<String> testingOperators = new ArrayList<String>();
	private static List<Type> BASIC_TYPES;
	private static int blockSize = 0;

	private static SemanticImpl singleton;
	private Program javaProgram;
	static CodeGenerator codeGenerator;
	private static Calculator calculator;
	private static String currentOperator;

	public static SemanticImpl getInstance() {
		if (singleton == null) {
			singleton = new SemanticImpl();
			codeGenerator = new CodeGenerator();
			calculator = new Calculator();
			initCollections();
		}
		return singleton;
	}

	private static void initCollections() {
		initBasicTypes();
		initTypeCompatibility();
		iniTestingOperators();
	}

	public int getBlockSize() {
		return this.blockSize;
	}

	public void incBlockSize() {
		this.blockSize++;
	}

	public void resetBlockSize() {
		this.blockSize = 0;
	}

	protected SemanticImpl() {
		javaProgram = new Program();
	}

	private static void initBasicTypes() {
		BASIC_TYPES = new ArrayList<Type>() {
			{
				add(new Type("int"));
				add(new Type("float"));
				add(new Type("double"));
				add(new Type("long"));
				add(new Type("char"));
				add(new Type("void"));
				add(new Type("String"));
				add(new Type("boolean"));
				add(new Type("Object"));
				add(new Type("Integer"));
			}
		};
	}

	private static void initTypeCompatibility() {
		List<String> doubleCompTypes = new ArrayList<String>();
		doubleCompTypes.add("int");
		doubleCompTypes.add("float");
		doubleCompTypes.add("double");
		doubleCompTypes.add("long");

		List<String> floatCompTypes = new ArrayList<String>();
		floatCompTypes.add("int");
		floatCompTypes.add("float");
		floatCompTypes.add("long");

		List<String> longCompTypes = new ArrayList<String>();
		longCompTypes.add("long");
		longCompTypes.add("int");

		List<String> intCompTypes = new ArrayList<String>();
		intCompTypes.add("int");

		List<String> booleanCompTypes = new ArrayList<String>();
		booleanCompTypes.add("boolean");

		tiposCompativeis.put("double", doubleCompTypes);
		tiposCompativeis.put("float", floatCompTypes);
		tiposCompativeis.put("long", longCompTypes);
		tiposCompativeis.put("int", intCompTypes);
		tiposCompativeis.put("boolean", booleanCompTypes);
	}

	private static void iniTestingOperators() {
		testingOperators.add("<");
		testingOperators.add(">");
		testingOperators.add("<=");
		testingOperators.add(">=");
		testingOperators.add("==");
		testingOperators.add("!=");
	}

	private void createNewScope(ScopedEntity scope) {
		scopeStack.push(scope);
	}

	public void exitCurrentScope() throws InvalidFunctionException {
		ScopedEntity scoped = scopeStack.pop();
		checkDeclaredAndReturnedType(scoped.getName(),
				((Function) scoped).getDeclaredReturnType(), null);
	}

	public void checkVariableAttribution(String id, String function)
			throws InvalidVariableException, InvalidTypeException,
			InvalidFunctionException {
		if (!checkVariableExistence(id)) {
			throw new InvalidVariableException("Variable doesn't exist");
		}
		Type identifierType = findVariableByIdentifier(id).getType();

		for (Function f : functions) {
			if (f.getName().equals(function)) {
				if (!checkTypeCompatibility(identifierType,
						f.getDeclaredReturnType())) {
					String exceptionMessage = String.format(
							"Incompatible types! %s doesn't match %s",
							identifierType, f.getDeclaredReturnType());
					throw new InvalidFunctionException(exceptionMessage);
				}
			}
		}

	}

	public void exitCurrentScope(Expression exp)
			throws InvalidFunctionException {
		ScopedEntity scoped = scopeStack.pop();
		if (scoped instanceof Function) {
			if (exp != null) {
				checkDeclaredAndReturnedType(scoped.getName(),
						((Function) scoped).getDeclaredReturnType(), exp);
			} else {
				// System.out.println("O declared eh: "+((Function)
				// scoped).getDeclaredReturnType());
				if (!((Function) scoped).getDeclaredReturnType().equals(
						new Type("void"))) {
					throw new InvalidFunctionException("The function "
							+ scoped.getName()
							+ " is missing a return statement in the end of it");
				}
			}
		}
	}

	public ScopedEntity getCurrentScope() {
		return scopeStack.peek();
	}

	public void addFunctionAndNewScope(Function f) {
		functions.add(f);
		createNewScope(f);
	}
	
	public void generateSwitchCode(Expression e) {
		codeGenerator.generateSUBCode(switchExpression.getRegister(), e.getRegister());
		codeGenerator.generateBNEQZCodeCase();
	}
	
	public void beginSwitch(Expression e) throws InvalidTypeException {
		if (e.getType() == null) {
			throw new InvalidTypeException("ERROR: The type of switch expression cannot be null!");
		}
		switchExpression = e;
	}
	
	public void validateCase(Expression e) throws InvalidTypeException {
		if (e.getType() == null) {
			throw new InvalidTypeException("ERROR: The type of case expression cannot be null!");
		}
		if (!e.getType().equals(switchExpression.getType())) {
			throw new InvalidTypeException("ERROR: The type of case is not the same as the type of switch!");
		}
	}

	public boolean checkVariableExistence(String variableName) {
		if (!scopeStack.isEmpty()
				&& getCurrentScope().getVariable().get(variableName) != null) {
			return true;
		} else {
			return variables.get(variableName) != null ? true : false;
		}
	}

	public boolean checkVariableExistenceLocal(String variableName) {
		if (!scopeStack.isEmpty() && getCurrentScope().getVariable().get(variableName) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkVariableExistenceGlobal(String variableName) {
		return variables.get(variableName) != null ? true : false;
	}

	public void checkFunctionExistence(Function temp) throws InvalidFunctionException {
		if (functions.contains(temp)) {
			throw new InvalidFunctionException("ERROR: The function "
					+ temp.getName() + " has already been declared!");
		}
	}

	public boolean checkValidExistingType(Type type) {
		return BASIC_TYPES.contains(type) || secondaryTypes.contains(type);
	}
	
	public void checkIsBoolean(Type type) throws InvalidTypeException {
		if (!checkTypeCompatibility(new Type("boolean"), type)) {
			throw new InvalidTypeException("Logical expression can only have boolean.");
		}
	}

	public boolean checkTypeCompatibility(Type leftType, Type rightType) {
		if (leftType.equals(rightType)) {
			return true;
		} else {
			List<String> tipos = tiposCompativeis.get(leftType.getName());
			if (tipos == null)
				return false;
			return tipos.contains(rightType.getName());
		}
	}

	public void addType(Type type) {
		if (!secondaryTypes.contains(type)) {
			secondaryTypes.add(type);
			List<String> tipos = new ArrayList<String>();
			tipos.add(type.getName());
			tiposCompativeis.put(type.getName(), tipos);
		}
	}

	public boolean checkTypeOfAssignment(Variable variable, Expression exp)
			throws InvalidTypeAssignmentException {
		if (!variable.getType().equals(exp.getType())) {
			throw new InvalidTypeAssignmentException("Alguma msg aqui");
		}
		return true;
	}

	public boolean isNumericExpression(Expression le, Expression re)
			throws InvalidOperationException {
		if (!le.isNumeric() || !re.isNumeric()) {
			throw new InvalidOperationException("Not a numeric expression");
		}
		return true;
	}

	public boolean isNumericExpression(Expression le)
			throws InvalidOperationException {
		if (!le.isNumeric()) {
			throw new InvalidOperationException("Not a numeric expression");
		}
		return true;
	}

	/**
	 * Valida uma variavel: - se o tipo dela existe - se o nome ja esta em uso
	 * 
	 * @param variable
	 *            variable a ser validade
	 * 
	 * @throws Exception
	 */
	private void validateVariable(Variable variable) throws Exception {
		if (checkVariableExistenceLocal(variable.getIdentifier())) {
			throw new InvalidVariableException("Name already exists");
		}
		if (!checkValidExistingType(variable.getType())) {
			throw new InvalidTypeException("Type non existing");
		}
	}

	private void validateVariableGlobal(Variable variable) throws Exception {
		if (checkVariableExistenceGlobal(variable.getIdentifier())) {
			throw new InvalidVariableException("Name already exists");
		}
		if (!checkValidExistingType(variable.getType())) {
			throw new InvalidTypeException("Type non existing");
		}
	}

	/**
	 * Valida uma variavel. Caso seja valida, adiciona a um mapa de variaveis
	 * sendo usadas.
	 * 
	 * @param variable
	 *            variable a ser validade e posteriormente adicionada.
	 * 
	 * @throws Exception
	 */
	private void addVariable(Variable variable) throws Exception {
		for (String v : variables.keySet()) {
			// System.out.println(v);
		}
		if (scopeStack.isEmpty()) {
			validateVariableGlobal(variable);

			variables.put(variable.getIdentifier(), variable);
		} else {
			validateVariable(variable);
			getCurrentScope().addVariable(variable);
		}

		if (variable.getValue() != null) {
			checkVariableAttribution(variable.getIdentifier(),
					variable.getValue());
		}
	}

	public void addVariablesFromTempList(Type type) throws Exception {
		for (Variable variable : tempVariables) {
			variable.setType(type);
			addVariable(variable);
		}

		tempVariables = new ArrayList<Variable>();
	}

	public void validateFunction(String functionName, ArrayList<Parameter> params, Type declaredType) throws InvalidFunctionException, InvalidParameterException {
		
		
		if (declaredType == null) {
			throw new InvalidFunctionException(
					"The function "
							+ functionName
							+ " is missing either a declared return type or a return statement in the end of it");
		}
		Function temp = new Function(functionName, params);
		checkFunctionExistence(temp);

		if (params != null) {
			for (Parameter p : params) {
				variables.put(p.getIdentifier(), (Variable) p);
			}
			checkExistingParameter(params);
		}
		temp.setDeclaredReturnedType(declaredType);
		addFunctionAndNewScope(temp);
	}

	private boolean hasReturn(Expression exp) throws InvalidFunctionException {
		if(exp.getContext() == null) return false;
		return exp.getContext().equalsIgnoreCase("return");
	}

	private void checkDeclaredAndReturnedType(String functionName,
			Type declaredType, Expression exp) throws InvalidFunctionException {
		if(exp == null && declaredType.equals(new Type("void"))){return;}
		if(exp == null && !declaredType.equals(new Type("void"))){throw new InvalidFunctionException("Missing a return statement");}
		if(!declaredType.equals(new Type("void"))){
			if(!hasReturn(exp)){throw new InvalidFunctionException("Missing a return statement");}
			if (!declaredType.equals(exp.getType())) {
				throw new InvalidFunctionException("The function " + functionName
						+ " didn't return the expected type: " + declaredType
						+ ". It returns " + exp.getType() + " instead");
			}
		}else{
			if(hasReturn(exp)){
				if(exp.getType() != null){throw new InvalidFunctionException("A void function should not return a: "+exp.getType());} 
			}
			
		}
		
	}

	private void checkExistingParameter(ArrayList<Parameter> params)
			throws InvalidParameterException {
		for (int i = 0; i < params.size(); i++) {
			for (int k = i + 1; k < params.size(); k++) {
				if (params.get(i).getIdentifier()
						.equals(params.get(k).getIdentifier())) {
					throw new InvalidParameterException("The parameter: "
							+ params.get(k).getIdentifier()
							+ " has been previously defined.");
				}
			}
		}
	}

	public Expression getExpression(Expression le, String md, Expression re)
			throws InvalidTypeException, InvalidOperationException {
		// System.out.println("No getexpression " + "tipo 1: "+
		// le.getType().getName() + "   " + "tipo 2: "+ re.getType().getName());
		if (checkTypeCompatibility(le.getType(), re.getType())
				|| checkTypeCompatibility(re.getType(), le.getType())) {
			Type newType = getMajorType(le.getType(), re.getType());
			String result;
			switch (md) {

			case "+":
				result = calculator.getSumNumericValue(le, re, md);
				return new Expression(newType, result);
			case "-":
				result = calculator.getSubNumericValue(le, re, md);
				return new Expression(newType, result);
			case "*":
				result = calculator.getMultNumericValue(le, re, md);
				return new Expression(newType, result);
			case "/":
				result = calculator.getDivNumericValue(le, re, md);
				return new Expression(newType, result);
				// case "+=":
				// result = calculator.getSumEqNumericValue(le,re,md);
				// return new Expression(newType,result);
				// break;
				// case "-=":
				// result = calculator.getSubEqNumericValue(le,re,md);
				// return new Expression(newType,result);
				// break;
				// case "%":
				// result = calculator.getModNumericValue(le,re,md);
				// return new Expression(newType,result);
				// break;
				// case "%=":
				// result = calculator.getModEqNumericValue(le,re,md);
				// return new Expression(newType,result);
				// break;
				//
			default:
				break;
			}
			return new Expression(newType);
		}
		throw new InvalidTypeException("Not allowed!");
	}

	private Type getMajorType(Type type1, Type type2) {
		return tiposCompativeis.get(type1.getName()).contains(type2.getName()) ? type1
				: type2;
	}

	public void checkVariableAttribution(String id, Expression expression)
			throws InvalidVariableException, InvalidTypeException,
			InvalidFunctionException {
		if (!checkVariableExistence(id)) {
			throw new InvalidVariableException("Variable doesn't exist");
		}
		if (!expression.getType().equals(new Type("null"))
				&& !checkValidExistingType(expression.getType())) {
			throw new InvalidTypeException("Type non existing");
		}
		Type identifierType = findVariableByIdentifier(id).getType();
		if (expression.getType().equals(new Type("null"))) {
			return;
		}
		if (!checkTypeCompatibility(identifierType, expression.getType())) {
			String exceptionMessage = String.format(
					"Incompatible types! %s doesn't match %s", identifierType,
					expression.getType());
			throw new InvalidFunctionException(exceptionMessage);
		}
	}

	public Variable findVariableByIdentifier(String variableName) {
		if (!scopeStack.isEmpty()
				&& getCurrentScope().getVariable().get(variableName) != null) {
			return getCurrentScope().getVariable().get(variableName);
		} else {
			return variables.get(variableName);
		}

	}

	public void validateVariableName(String variableName)
			throws InvalidVariableException {
		if (!checkVariableExistence(variableName)) {
			throw new InvalidVariableException("Variable doesn't exist");
		}
	}

	public void addSupertype(String className, String superClassName)
			throws InvalidTypeException {
		if (superClassName != null) {
			if (tiposCompativeis.containsKey(superClassName)) {
				tiposCompativeis.get(superClassName).add(className);
				return;
			}

			throw new InvalidTypeException("Superclass doesn't exist");
		}
	}

	private void checkTestingExpression(Expression le, String operator,
			Expression re) throws InvalidOperatorException,
			InvalidOperationException {
		// operador eh valido
		if (!testingOperators.contains(operator)) {
			String message = String.format(
					"This operator: %s is not allowed for testing operations.",
					operator);
			throw new InvalidOperatorException(message);
		}
		// exp sao de tipos equivalentes
		if (!checkTypeCompatibility(le.getType(), re.getType())
				&& !checkTypeCompatibility(re.getType(), le.getType())) {
			String message = String
					.format("This testing operation ( %s ) is not avaiable for the types %s and %s",
							operator, le.getType(), re.getType());
			throw new InvalidOperationException(message);
		}
	}

	public boolean verifyCall(String funcName, ArrayList<Expression> args)
			throws InvalidFunctionCallException {

		for (Function f : functions) {
			if (f.getName().equals(funcName)) {
				ArrayList<Parameter> p = (ArrayList<Parameter>) f.getParams();
				if (p.size() != args.size()) {
					throw new InvalidFunctionCallException(
							"The method call of " + funcName
									+ " has incorrect number of arguments");
				}
				for (int i = 0; i < p.size(); i++) {
					if (!p.get(i).getType().getName()
							.equals(args.get(i).getType().getName())) {
						throw new InvalidFunctionCallException(
								"The method call of " + funcName
										+ " expects a "
										+ p.get(i).getType().getName()
										+ " but got the type "
										+ args.get(i).getType().getName());
					}
				}
				return true;
			}
		}
		throw new InvalidFunctionCallException("The function " + funcName
				+ " may have not been declared");
	}

	/* Auxiliary functions */

	public void addVariableToTempList(Variable var) {
		tempVariables.add(var);
	}

	public CodeGenerator getCodeGenerator() {
		return codeGenerator;
	}
	
	public void setCurrentOperator(boolean newCurrentOperator){
		currentOperator = newCurrentOperator+"";
	}

	public void generateBaseOpCode(String op, Expression e1, Expression e2) {
		Operation operator = getOperator(op);
		switch (operator) {
		case AND_OP:
			codeGenerator.generateANDCode(e1.getRegister(), e2.getRegister());
			break;
		case OR_OP:
			codeGenerator.generateORCode(e1.getRegister(), e2.getRegister());
			break;
		case XOR_OP:
			codeGenerator.generateXORCode(e1.getRegister(), e2.getRegister());
			break;
		case ANDEQ_OP:
			codeGenerator.generateANDEQCode(e1.getRegister(), e2);
			break;
		case OREQ_OP:
			codeGenerator.generateOREQCode(e1.getRegister(), e2);
			break;
		case XOREQ_OP:
			codeGenerator.generateXOREQCode(e1, e2);
			break;
		case MINUS:
			codeGenerator.generateSUBCode(e1.getRegister(), e2.getRegister());
			break;
		case MULT:
			codeGenerator.generateMULCode(e1.getRegister(), e2.getRegister());
			break;
		case PLUS:
			codeGenerator.generateADDCode(e1.getRegister(), e2.getRegister());
			break;
		case DIV:
			codeGenerator.generateDIVCode(e1.getRegister(), e2.getRegister());
			break;
		case MOD:
			codeGenerator.generateMODCode(e1.getRegister(), e2.getRegister());
			break;
		case MINUSEQ:
			codeGenerator.generateSUBCode(e1.getRegister(), e2.getRegister());
			break;
		case MULTEQ:
			codeGenerator.generateMULCode(e1.getRegister(), e2.getRegister());
			break;
		case PLUSEQ:
			codeGenerator.generateADDCode(e1.getRegister(), e2.getRegister());
			break;
		case DIVEQ:
			codeGenerator.generateDIVEQCode(e1.getRegister(), e2);
			break;
		case MODEQ:
			codeGenerator.generateMODEQCode(e1.getRegister(), e2);
			break;
		default:
			break;
		}

	}

	private Operation getOperator(String op) {
		switch (op) {
		case "+":
			return Operation.PLUS;
		case "-":
			return Operation.MINUS;
		case "*":
			return Operation.MULT;
		case "/":
			return Operation.DIV;
		case "+=":
			return Operation.PLUSEQ;
		case "-=":
			return Operation.MINUSEQ;
		case "*=":
			return Operation.MULTEQ;
		case "/=":
			return Operation.DIVEQ;
		case "==":
			return Operation.EQ_OP;
		case "!=":
			return Operation.NE_OP;
		case "&&":
			return Operation.AND_OP;
		case "||":
			return Operation.OR_OP;
		case "^":
			return Operation.XOR_OP;
		case "^=":
			return Operation.XOREQ_OP;
		case "|=":
			return Operation.OREQ_OP;
		case "&=":
			return Operation.ANDEQ_OP;
		default:
			return Operation.PLUS;
		}
	}
}