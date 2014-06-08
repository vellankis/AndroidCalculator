package com.dev.calculator;

import java.util.regex.Pattern;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class CalculatorActivity extends ActionBarActivity implements OnClickListener {
	
	Button add, subtract, multiply, divide, calculate, clear;
	EditText inputValues;
	
	// This boolean is set to true when user selects any operator so that we dont end up in adding multiple operators.	
	static boolean isOperatorUsed = false;
	
	// To Store the operator stored by the user.
	private static String mOperator;
	
	// To store the operands and results.
	private Float mNumberValue1, mNumberValue2, mResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_calculator);
		
		inputValues = (EditText) findViewById(R.id.action_input);
		
		add = (Button) findViewById(R.id.action_add);
		subtract = (Button) findViewById(R.id.action_subtract);
		multiply = (Button) findViewById(R.id.action_multiply);
		divide = (Button) findViewById(R.id.action_divide);
		calculate = (Button) findViewById(R.id.action_calculate);
		clear = (Button) findViewById(R.id.action_clear);		
		
		// Assign onclickListeners to buttons.
		add.setOnClickListener(this);
		subtract.setOnClickListener(this);
		multiply.setOnClickListener(this);
		divide.setOnClickListener(this);
		calculate.setOnClickListener(this);
		clear.setOnClickListener(this);
		inputValues.addTextChangedListener(customTextWatcher);
		
		// This is to show keyboard to user when the application is launched.
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		
	}
	

	/** Check for the text change so that if user delete any operator then clear the operator and boolean
	 *  so user can input new operator again.
	*/
    private TextWatcher customTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        	
        	if( (isOperatorUsed) && (count == 0) ) {
        		// check if the operator is removed 
        		// if removed set isOperator = false;
        		String validate = inputValues.getText().toString();
        		
        		if (! ( validate.contains("+") || validate.contains("-") || 
        				validate.contains("*") || validate.contains("/") ) )
        		{
        			// No operator found so reset operator
        			isOperatorUsed = false;
        			mOperator = null;
        		}
        	}
        }
        
        @Override
        public void afterTextChanged(Editable s) {
        }
        
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {        	
        }
    };
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.	
		return super.onOptionsItemSelected(item);
	}

	private void updateInputEditor(String operator) {
				
		if(isOperatorUsed) {
			// return if the operator is already used by user.
			return;
		}
		
		isOperatorUsed = true;
		mOperator = operator;
				
		StringBuffer userData = new StringBuffer();
		userData.append(inputValues.getText().toString());
		
		// Append the operator to the input.
		inputValues.setText(userData.append(operator) );
		
		// To place the cursor at the end of the input values
		inputValues.setSelection(inputValues.getText().length());
	}
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		if(inputValues.getText().length() == 0) {
			return;
		}
					
		switch(arg0.getId()) {
		
			case R.id.action_add:
				updateInputEditor(getResources().getString(R.string.action_add));				
			break;

			case R.id.action_subtract:
				updateInputEditor(getResources().getString(R.string.action_subtract));				
			break;
			
			case R.id.action_multiply:
				updateInputEditor(getResources().getString(R.string.action_multiply));				
			break;
			
			case R.id.action_divide:
				updateInputEditor(getResources().getString(R.string.action_divide));				
			break;
			
			case R.id.action_calculate:
				performCalculation();
			break;
			
			case R.id.action_clear:
				clearInputField();
			break;
 	
		}
	}
	
	
	// This API will perform the calculation of the given string
	private void performCalculation() {
				
		if(mOperator == null) {
			return;
		}
				
		String result = inputValues.getText().toString();
		
		String[] temp;
		// Get the operands from the input string.
		temp = result.split(Pattern.quote(mOperator));
		
		if(temp.length <= 1) {
			return;
		}
		
		for (int i = 0; i < temp.length; i++) {
			mNumberValue1 = Float.parseFloat(temp[0]); 
			mNumberValue2 = Float.parseFloat(temp[1]); 
		}
		
		if(mOperator.equals(getResources().getString(R.string.action_add))) {
			mResult = mNumberValue1 + mNumberValue2;
		} else if(mOperator.equals(getResources().getString(R.string.action_subtract))) {
			mResult = mNumberValue1 - mNumberValue2;
		} else if(mOperator.equals(getResources().getString(R.string.action_multiply))) {
			mResult = mNumberValue1 * mNumberValue2;
		} else if(mOperator.equals(getResources().getString(R.string.action_divide))) {
			mResult = mNumberValue1 / mNumberValue2;
		}
		
		inputValues.setText(String.valueOf(mResult));
		
		// To place the cursor at the end of the input values
		inputValues.setSelection(inputValues.getText().length());
		
		// reset the operator 
		isOperatorUsed = false;
	}
	
	
	private void clearInputField() {		
		// Append the operator to the input.
		inputValues.setText("");
	}
	
}
