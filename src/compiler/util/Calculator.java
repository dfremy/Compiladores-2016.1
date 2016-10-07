package compiler.util;

import compiler.core.Expression;
import compiler.core.Type;
import compiler.exceptions.InvalidOperationException;

public class Calculator {

	public String getSumNumericValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("string")) && md.equals("+")) {
			return le.getValue() + re.getValue();
		}

		else if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return ""
					+ (Double.valueOf(le.getValue()) + Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return ""
					+ (Float.valueOf(le.getValue()) + Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return ""
					+ (Long.valueOf(le.getValue()) + Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return ""
					+ (Integer.valueOf(le.getValue()) + Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}

	}

	public String getSubNumericValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return ""
					+ (Double.valueOf(le.getValue()) - Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return ""
					+ (Float.valueOf(le.getValue()) - Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return ""
					+ (Long.valueOf(le.getValue()) - Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return ""
					+ (Integer.valueOf(le.getValue()) - Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}

	public String getMultNumericValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return ""
					+ (Double.valueOf(le.getValue()) * Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return ""
					+ (Float.valueOf(le.getValue()) * Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return ""
					+ (Long.valueOf(le.getValue()) * Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return ""
					+ (Integer.valueOf(le.getValue()) * Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
	
	public String getDivNumericValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return ""
					+ (Double.valueOf(le.getValue()) / Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return ""
					+ (Float.valueOf(le.getValue()) / Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return ""
					+ (Long.valueOf(le.getValue()) / Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return ""
					+ (Integer.valueOf(le.getValue()) / Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
	
	public boolean getEqualBooleanValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return (Double.valueOf(le.getValue()) == Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return (Float.valueOf(le.getValue()) == Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return  (Long.valueOf(le.getValue()) == Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return (Integer.valueOf(le.getValue()) == Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
	
	public boolean getNotEqualBooleanValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return (Double.valueOf(le.getValue()) != Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return (Float.valueOf(le.getValue()) != Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return  (Long.valueOf(le.getValue()) != Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return (Integer.valueOf(le.getValue()) != Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
	
	public boolean getGreaterThanOrEqualBooleanValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return (Double.valueOf(le.getValue()) >= Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return (Float.valueOf(le.getValue()) >= Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return  (Long.valueOf(le.getValue()) >= Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return (Integer.valueOf(le.getValue()) >= Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
	
	public boolean getLessThanEqualBooleanValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return (Double.valueOf(le.getValue()) <= Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return (Float.valueOf(le.getValue()) <= Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return  (Long.valueOf(le.getValue()) <= Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return (Integer.valueOf(le.getValue()) <= Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
	
	public boolean getGreaterThanBooleanValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return (Double.valueOf(le.getValue()) > Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return (Float.valueOf(le.getValue()) > Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return  (Long.valueOf(le.getValue()) > Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return (Integer.valueOf(le.getValue()) > Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
	
	public boolean getLessThanBooleanValue(Expression le, Expression re, String md)
			throws InvalidOperationException {
		if (le.getType().equals(new Type("double"))
				|| re.getType().equals(new Type("double"))) {
			return (Double.valueOf(le.getValue()) < Double.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("float"))
				|| re.getType().equals(new Type("float"))) {
			return (Float.valueOf(le.getValue()) < Float.valueOf(re
							.getValue()));
		} else if (le.getType().equals(new Type("long"))) {
			return  (Long.valueOf(le.getValue()) < Long
							.valueOf(re.getValue()));
		} else if (le.getType().equals(new Type("int"))) {
			return (Integer.valueOf(le.getValue()) < Integer.valueOf(re
							.getValue()));
		} else {
			throw new InvalidOperationException(
					"Types are not avaible to this kind of operation");
		}
	}
}
