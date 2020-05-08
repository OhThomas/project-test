package application;
/**
 * Model in MVC. Currently used for calculator model.
 * 
 * @author Thomas Rader (vil203)
 */
public class Model {
		/**
		 * Calculates numbers from the Calculator.fxml file.
		 * @param number1		first number inputed
		 * @param number2		second number receieved
		 * @param operator		operator chosen
		 * @return				returns the result
		 */
		public float calculate(long number1, long number2, String operator){
			switch(operator){
			case "+":
				return (float)number1 + (float)number2;
			case "-":
				return (float)number1 - (float)number2;
			case "/":
				if(number2 == 0)
					return 0;
				else
					return (float)number1 / (float)number2;
			case "*":
				return (float)number1 * (float)number2;
			default:
				break;
			}
			return 0f;
		}
		
}
