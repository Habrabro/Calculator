package org.bonestudio.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    TextView textView;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvResult);
        editText = findViewById(R.id.etInput);
        button = findViewById(R.id.btnClear);
    }

    public void enterChar(View view)
    {
        Button button = (Button)view;
        int cursorPosition = editText.getSelectionEnd();
        editText.getText().insert(cursorPosition, button.getText());
        editText.setSelection(++cursorPosition);
    }

    public void calculate(View view)
    {
        String expression = editText.getText().toString().replaceAll("[^\\d.+-\\/*]", "");
        editText.setText(expression);
        int operatorIndex = indexOf(Pattern.compile("[+\\-\\/*]"), expression);
        double result = 0;
        try
        {
            char operator = expression.charAt(operatorIndex);
            double operand1 = Float.parseFloat(expression.substring(0, operatorIndex));
            double operand2 = Float.parseFloat(expression.substring(operatorIndex + 1));

            switch (operator)
            {
                case '+':
                    result = operand1 + operand2;
                    break;
                case '-':
                    result = operand1 - operand2;
                    break;
                case '*':
                    result = operand1 * operand2;
                    break;
                case '/':
                    result = operand1 / operand2;
                    break;
            }

            textView.setText(formatDouble(result));
        }
        catch (StringIndexOutOfBoundsException ex)
        {
            showToast(getResources().getString(R.string.toast_stringIndexOutOfBoundsEx));
        }
        catch (NumberFormatException ex)
        {
            showToast(getResources().getString(R.string.toast_stringIndexOutOfBoundsEx));
        }
    }

    public void delete(View view)
    {
        int cursorPosition = editText.getSelectionEnd();
        String expression = editText.getText().toString();
        try
        {
            editText.setText(expression.substring(0, cursorPosition - 1) + expression.substring(cursorPosition));
            editText.setSelection(--cursorPosition);
        }
        catch (StringIndexOutOfBoundsException ex)
        {
            System.out.println(ex);
        }
    }

    int indexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.start() : -1;
    }

    void showToast(String message)
    {
        Toast toast = Toast.makeText(this, message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0,160);
        toast.show();
    }

    public static String formatDouble(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%.2f", d);
    }
}
