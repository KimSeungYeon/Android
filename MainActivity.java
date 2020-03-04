package com.example.myapplication020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.NoCopySpan;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButtonClick(View v){
       TextView result_text=(TextView)findViewById(R.id.result_text);
       EditText num=(EditText)findViewById(R.id.num1);
       String str=num.getText().toString();
       switch (v.getId()) {
           case R.id.add:
               str=str+"+";
               break;
           case R.id.sub:
               str=str+"-";
               break;
           case R.id.mul:
               str=str+"*";
               break;
           case R.id.div:
               str=str+"/";
               break;
           case R.id.result:
               result_text.setText(String.valueOf(calculation(ChangPostFix(str))));
               break;
           default:

       }
        num.setText(str);
        num.setSelection(num.length());
    }
    public int calculation(ArrayList<String> list){
        Stack<Integer> stack=new Stack<Integer>();
        for(int i=0;i<list.size();i++)
        {
            if(TextUtils.isDigitsOnly(list.get(i)))
                stack.push(Integer.valueOf(list.get(i)));
            else{
                int value1,value2;
                value2=stack.pop();
                value1=stack.pop();
                stack.push(useOperator(value1,value2,list.get(i)));
            }
        }
        return stack.pop();
    }
    public int useOperator(int a,int b,String op){
        switch (op){
            case "+":return a+b;
            case "-":return a-b;
            case "*":return a*b;
            case "/":return a/b;
        }
        return 1;
    }
    public ArrayList<String> ChangPostFix(String str){
        Stack<String> stack=new Stack<String>();
        ArrayList<String> list=new ArrayList<String>();
        ArrayList<String> result=new ArrayList<String>();
        StringTokenizer tokens=new StringTokenizer(str,"+-*/()",true);
        while(tokens.hasMoreTokens()){
            String token=tokens.nextToken();
            list.add(token);
        }

        for(int i=0;i<list.size();i++)
        {
            String temp=list.get(i);
            if(temp.equals("("))
                stack.push(temp);
            else if(temp.equals(")"))
            {
                while(true) {
                    temp = stack.pop();
                    if(temp.equals("("))
                        break;
                    result.add(temp);
                }

            }
            else if(temp.equals("+") || temp.equals("-") || temp.equals("*") || temp.equals("/")){
                while(!stack.isEmpty()&&StackPrecede(stack.peek(),temp))
                    result.add(stack.pop());
                stack.push(temp);

            }else{
                result.add(temp);
            }
        }
        while(!stack.isEmpty())
            result.add(stack.pop());
        return result;
    }
    public boolean StackPrecede(String in,String out){
        if(Rank(in)>=Rank(out))
            return true;
        else
            return false;
    }
    public int Rank(String str){
        switch (str) {
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            case "(":
                return 1;
        }
        return 1;
    }
}
