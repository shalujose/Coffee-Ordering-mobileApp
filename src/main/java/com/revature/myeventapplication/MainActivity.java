package com.revature.myeventapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private Button button, order, addCoffee, minusCoffee, redirect;
    private int noOfCoffee = 2, price;
    private boolean hasWhippedCream;
    private CheckBox whippedCream;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        redirect = (Button) findViewById(R.id.button2);
        whippedCream = (CheckBox) findViewById(R.id.checkBox);

        addCoffee = (Button) findViewById(R.id.button3);
        addCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementOrder();
            }
        });

        minusCoffee = (Button) findViewById(R.id.button4);
        minusCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementOrder();
            }
        });

        order = (Button) findViewById(R.id.button);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder(order);
                redirect.setVisibility(View.VISIBLE);
            }
        });

        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecondActivity();
            }
        });
    }

    public void incrementOrder() {
        if (noOfCoffee == 100) {
            Toast.makeText(this, "You can't make order above 100", Toast.LENGTH_SHORT).show();
        }
        noOfCoffee = noOfCoffee + 1;
        display(noOfCoffee);
    }

    public void decrementOrder() {
        if (noOfCoffee > 1)
            noOfCoffee = noOfCoffee - 1;
        display(noOfCoffee);
    }

    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.editText3);
        String nametext = text.getText().toString();

        whippedCream = (CheckBox) findViewById(R.id.checkBox);
        hasWhippedCream = whippedCream.isChecked();
        int price = displayPrice(noOfCoffee * 5, nametext, hasWhippedCream);
        Log.v("MainActivity", "whipped cream checkbox: " + hasWhippedCream);
        orderSummery(nametext, price, hasWhippedCream);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Ordering " + nametext);
        intent.putExtra(Intent.EXTRA_TEXT, "" + orderSummery(nametext, price, hasWhippedCream));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void display(int orderNo) {
        TextView oderView = (TextView) findViewById(R.id.textView2);
        oderView.setText("" + orderNo);
    }

    private int displayPrice(int coffeePrice, String name, boolean hasWhippedCream) {
        TextView priceView = (TextView) findViewById(R.id.textView3);
        /*priceView.setText(NumberFormat.getCurrencyInstance().format(coffeePrice));*/
        priceView.setText(orderSummery(name, coffeePrice, hasWhippedCream));
        return coffeePrice;
    }

    public void openSecondActivity() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }

    private String orderSummery(String name, int price, boolean whippedCream) {
        String message = "Name: " + name;
        message += "\nQuantity: " + noOfCoffee;
        message += "\nWhipped cream: " + whippedCream;
        message += "\nTotal: $" + price;
        return message;
    }
}
