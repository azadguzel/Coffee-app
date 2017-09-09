package com.example.android.java;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view){
        if(quantity >= 100){
            Toast.makeText(this,"We cannot prepare that much coffee at the same time.Sorry :/",Toast.LENGTH_SHORT).show();
            return;
        }else{
        quantity+=1;
        display(quantity);}
    }

    public void decrement(View view){
        if(quantity <= 1){
            Toast.makeText(this,"You cannot order negative coffees in this universe.Sorry :/",Toast.LENGTH_SHORT).show();
            return;
        }else{
            quantity-=1;
            display(quantity);}
    }

    public void submitOrder(View view) {
        EditText nameText = (EditText) findViewById(R.id.name_field);
        String name = nameText.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean isWhippedCreamChecked = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById (R.id.chocolate_checkbox);
        boolean isChocolateChecked = chocolateCheckBox.isChecked();

        int price = calculatePrice(isChocolateChecked,isWhippedCreamChecked);
        String message=createOrderSummary(name,price,isWhippedCreamChecked,isChocolateChecked);
        String subject="Coffee order for "+name;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
       orderSummaryTextView.setText(message);
    }

    private int calculatePrice(boolean Chocolate, boolean WhippedCreamChecked){
        int price = 5;
        if(Chocolate){
            price+=2;
        }

        if(WhippedCreamChecked){
            price+=1;
        }
        return quantity*price;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String message="Name: "+ name +"\nAdd Whipped Cream? "+addWhippedCream+"\nAdd Chocolate? "+addChocolate;
        message = message+"\nQuantity: "+quantity+"\nTotal: $"+price+"\nThank you";
        return message;
    }
}

