package shapirju.Calc3320;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.*;

public class CalcActivity extends AppCompatActivity {

    private TextView number_screen;
    private Button row1_col1, row1_col2, row1_col3, row1_col4,
                   row2_col1, row2_col2, row2_col3, row2_col4,
                   row3_col1, row3_col2, row3_col3, row3_col4,
                   row4_col1, row4_col2, row4_col3, row4_col4,
                   row5_col1, row5_col3, row5_col4;

    private float A = 0, B = 0, C = 0;

    private String operator_type, modifier_type, decimal_store;
    private boolean A_occupied = false, dot_set = false, last_store = true,
                    last_operator = false, cleared_before = false, modify_at_start = false;
    // last_store == true if last num stored was A
    // last_operator == true if last button pressed was operator

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_layout);

        number_screen = (TextView)findViewById(R.id.screen);
        number_screen.setText(isInteger(C));
        row1_col1 = (Button) findViewById(R.id.AC);
        row1_col2 = (Button) findViewById(R.id.plusmin);
        row1_col3 = (Button) findViewById(R.id.percent);
        row1_col4 = (Button) findViewById(R.id.divide);
        row2_col1 = (Button) findViewById(R.id.seven);
        row2_col2 = (Button) findViewById(R.id.eight);
        row2_col3 = (Button) findViewById(R.id.nine);
        row2_col4 = (Button) findViewById(R.id.times);
        row3_col1 = (Button) findViewById(R.id.four);
        row3_col2 = (Button) findViewById(R.id.five);
        row3_col3 = (Button) findViewById(R.id.six);
        row3_col4 = (Button) findViewById(R.id.minus);
        row4_col1 = (Button) findViewById(R.id.one);
        row4_col2 = (Button) findViewById(R.id.two);
        row4_col3 = (Button) findViewById(R.id.three);
        row4_col4 = (Button) findViewById(R.id.plus);
        row5_col1 = (Button) findViewById(R.id.zero);
        row5_col3 = (Button) findViewById(R.id.dot);
        row5_col4 = (Button) findViewById(R.id.equals);

        calculate();

        Button exit_button = (Button) findViewById(R.id.exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View click) {
                finish();
                System.exit(0);
            }
        });
    }

    private void calculate() {
        View.OnClickListener detect_button = new View.OnClickListener() {
            @Override
            public void onClick(View click) {
                Button this_button = (Button) click;

                if (isDigit(this_button)) {
                    updateClear("C");
                    if (dot_set) {
                        store(this_button);
                        print(decimal_store);
                    }
                    else {
                        print(Integer.toString(numLookup(this_button)));
                        store(this_button);
                    }
                }

                else if (isOperator(this_button)) {
                    dot_set = false;

                    if (!last_operator) {
                        last_operator = true;

                        if (!A_occupied)
                            A_occupied = true;
                        else {
                            compute();
                            print(isInteger(C));
                        }
                    }
                }

                else if (isModifier(this_button)) modify();

                else if (isDecimal(this_button)) {
                    updateClear("C");
                    if (!dot_set) {
                        dot_set = true;

                        if (last_operator)
                            decimal_store = "0.";
                        else
                            decimal_store = number_screen.getText().toString() + ".";

                        print(decimal_store);
                    }
                }

                else if (isEqual(this_button)) {
                    last_operator = true;
                    compute();
                    print(isInteger(C));
                }

                else if (isClear(this_button)) {
                    // clear critical variables
                    A = 0; B = 0; C = 0;

                    // clear status flags
                    A_occupied = false;
                    last_store = true;
                    last_operator = false;
                    dot_set = false;
                    cleared_before = true;

                    // clear storage containers
                    operator_type = "";
                    modifier_type = "";
                    decimal_store = "";

                    // set UI back to default
                    updateClear("AC");
                    number_screen.setText("0");
                }
            }
        };

        makeButtonsDetectable(detect_button);
    }

    private void print(String val) {
        if (dot_set)
            number_screen.setText(val);
        else {
            if (number_screen.getText().toString().equals("0"))
                number_screen.setText(val);
            else if (cleared_before)
                number_screen.setText(val);
            else if (last_operator)
                number_screen.setText(val);
            else if (modify_at_start)
                number_screen.setText(val);
            else
                number_screen.append(val);
        }
    }

    private void store(Button this_button) {
        if (dot_set) {
            decimal_store += Integer.toString(numLookup(this_button));
            if (!A_occupied) {
                A = Float.parseFloat(decimal_store);
                last_store = true;
            }
            else {
                B = Float.parseFloat(decimal_store);
                last_store = false;
            }
        }
        else if (!A_occupied) {
            if (A != 0) {
                String num_str = Integer.toString((int)A);
                String temp = Integer.toString(numLookup(this_button));
                num_str += temp;
                A = Float.parseFloat(num_str);
            }
            else
                A = numLookup(this_button);

            last_store = true;
        }

        else {
            if (B != 0) {
                String num_str = Integer.toString((int)B);
                String temp = Integer.toString(numLookup(this_button));
                num_str += temp;
                B = Float.parseFloat(num_str);
            }
            else
                B = numLookup(this_button);

            last_store = false;
        }

        last_operator = false;
    }

    private boolean isEqual(Button check) { return (check == row5_col4); }
    private boolean isClear(Button check) { return (check == row1_col1); }
    private boolean isDecimal(Button check) { return (check == row5_col3); }

    private boolean isDigit(Button check) {
        if (check == row1_col1) return false;
        else if (check == row1_col2) return false;
        else if (check == row1_col3) return false;
        else if (check == row1_col4) return false;
        else if (check == row2_col4) return false;
        else if (check == row3_col4) return false;
        else if (check == row4_col4) return false;
        else if (check == row5_col3) return false;
        else if (check == row5_col4) return false;
        else
            return true;
    }

    private boolean isOperator(Button check) {
        if (check == row1_col4) {
            operator_type = "divide";
            return true;
        }
        else if (check == row2_col4) {
            operator_type = "times";
            return true;
        }
        else if (check == row3_col4) {
            operator_type = "minus";
            return true;
        }
        else if (check == row4_col4) {
            operator_type = "plus";
            return true;
        }
        else return false;
    }

    private boolean isModifier(Button check) {
        if (check == row1_col2) {
            modifier_type = "negate";
            return true;
        }
        else if (check == row1_col3) {
            modifier_type = "percent";
            return true;
        }
        else return false;
    }

    private void compute() {
        if (dot_set) {
            dot_set = false;
            B = Float.parseFloat(decimal_store);
        }

        if (!A_occupied)
            C = Float.parseFloat(number_screen.getText().toString());
        else {
            switch (operator_type) {
                case ("divide"):
                    try {
                        C = A / B;
                    } catch (ArithmeticException e) { /* prints Infinity */}
                    break;
                case ("times"):
                    C = A * B;
                    break;
                case ("minus"):
                    C = A - B;
                    break;
                case ("plus"):
                    C = A + B;
                    break;
            }

            A = C;
            B = 0;
        }
    }

    private void modify() {
        modify_at_start = true;

        float num = 0;

        if (last_store)
            num = A;
        else
            num = B;

        switch (modifier_type) {
            case ("negate"): num = num * -1;
                break;
            case ("percent"): num = num / 100;
                break;
        }

        if (last_store) {
            A = num;
            print(isInteger(A));
        }
        else {
            B = num;
            print(isInteger(B));
        }

        modify_at_start = false;
    }

    private int numLookup(Button b) { return Integer.parseInt(b.getText().toString()); }
    private void updateClear(String val) { row1_col1.setText(val); }

    private String isInteger(float num) {
        String screen_txt;

        if (num == 0)
            screen_txt = "0";
        else {
            int test_int = (int) num;

            if (test_int - num == 0)
                screen_txt = Integer.toString(test_int);
            else screen_txt = Float.toString(num);
        }

        return screen_txt;
    }

    public void makeButtonsDetectable(View.OnClickListener detect_button) {
        row1_col1.setOnClickListener(detect_button);
        row1_col2.setOnClickListener(detect_button);
        row1_col3.setOnClickListener(detect_button);
        row1_col4.setOnClickListener(detect_button);
        row2_col1.setOnClickListener(detect_button);
        row2_col2.setOnClickListener(detect_button);
        row2_col3.setOnClickListener(detect_button);
        row2_col4.setOnClickListener(detect_button);
        row3_col1.setOnClickListener(detect_button);
        row3_col2.setOnClickListener(detect_button);
        row3_col3.setOnClickListener(detect_button);
        row3_col4.setOnClickListener(detect_button);
        row4_col1.setOnClickListener(detect_button);
        row4_col2.setOnClickListener(detect_button);
        row4_col3.setOnClickListener(detect_button);
        row4_col4.setOnClickListener(detect_button);
        row5_col1.setOnClickListener(detect_button);
        row5_col3.setOnClickListener(detect_button);
        row5_col4.setOnClickListener(detect_button);
    }

}
