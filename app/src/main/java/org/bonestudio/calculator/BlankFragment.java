package org.bonestudio.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements View.OnClickListener
{
    private OnFragmentInteractionListener mListener;

    private TextView tvResult, tvDateTime;
    private EditText etInput;

    public BlankFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance()
    {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        long dateTime = bundle.getLong("dateTime");
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd MMM yyyy, HH:mm:ss");

        tvDateTime = view.findViewById(R.id.tvDateTime);
        tvResult = view.findViewById(R.id.tvResult);
        etInput = view.findViewById(R.id.etInput);

        tvDateTime.setText(simpleDate.format(dateTime));

        view.findViewById(R.id.btn0).setOnClickListener(this);
        view.findViewById(R.id.btn1).setOnClickListener(this);
        view.findViewById(R.id.btn2).setOnClickListener(this);
        view.findViewById(R.id.btn3).setOnClickListener(this);
        view.findViewById(R.id.btn4).setOnClickListener(this);
        view.findViewById(R.id.btn5).setOnClickListener(this);
        view.findViewById(R.id.btn6).setOnClickListener(this);
        view.findViewById(R.id.btn7).setOnClickListener(this);
        view.findViewById(R.id.btn8).setOnClickListener(this);
        view.findViewById(R.id.btn9).setOnClickListener(this);
        view.findViewById(R.id.btnPoint).setOnClickListener(this);
        view.findViewById(R.id.btnEquals).setOnClickListener(this);
        view.findViewById(R.id.btnPlus).setOnClickListener(this);
        view.findViewById(R.id.btnMinus).setOnClickListener(this);
        view.findViewById(R.id.btnDivide).setOnClickListener(this);
        view.findViewById(R.id.btnMultiply).setOnClickListener(this);
        view.findViewById(R.id.btnClear).setOnClickListener(this);
        view.findViewById(R.id.btnClose).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btn0:
            case R.id.btn1:
            case R.id.btn2:
            case R.id.btn3:
            case R.id.btn4:
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
            case R.id.btn8:
            case R.id.btn9:
            case R.id.btnPoint:
            case R.id.btnPlus:
            case R.id.btnMinus:
            case R.id.btnDivide:
            case R.id.btnMultiply:
                enterChar(view);
                break;
            case R.id.btnClear:
                deleteChar();
                break;
            case R.id.btnEquals:
                calculate();
                break;
            case R.id.btnClose:
                if (mListener != null)
                {
                    mListener.onFragmentInteraction();
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction();
    }

    public void enterChar(View view)
    {
        Button button = (Button)view;
        int cursorPosition = etInput.getSelectionEnd();
        etInput.getText().insert(cursorPosition, button.getText());
        etInput.setSelection(++cursorPosition);
    }

    public void calculate()
    {
        String expression = etInput.getText().toString().replaceAll("[^\\d.+-\\/*]", "");
        etInput.setText(expression);
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

            tvResult.setText(formatDouble(result));
        }
        catch (StringIndexOutOfBoundsException ex)
        {
            showToast(getResources().getString(R.string.toast_stringIndexOutOfBoundsEx));
        }
        catch (NumberFormatException ex)
        {
            showToast(getResources().getString(R.string.toast_NumberFormatException));
        }
    }

    public void deleteChar()
    {
        int cursorPosition = etInput.getSelectionEnd();
        String expression = etInput.getText().toString();
        try
        {
            etInput.setText(expression.substring(0, cursorPosition - 1) + expression.substring(cursorPosition));
            etInput.setSelection(--cursorPosition);
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
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message,Toast.LENGTH_LONG);
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
