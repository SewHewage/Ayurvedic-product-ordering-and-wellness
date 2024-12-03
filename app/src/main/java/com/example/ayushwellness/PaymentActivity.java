package com.example.ayushwellness;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private TextView productName, productPrice, totalAmount;
    private String name, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        productName = findViewById(R.id.payment_product_name);
        productPrice = findViewById(R.id.payment_product_price);
        totalAmount = findViewById(R.id.payment_total_amount);

        // Receive data from the previous activity
        if (getIntent() != null) {
            name = getIntent().getStringExtra("productName");
            price = getIntent().getStringExtra("productPrice");

            // Set product details
            productName.setText(name);
            productPrice.setText(price);
            totalAmount.setText(price);  // Assuming total amount is the price for simplicity
        }

        // Simulate a "Pay Now" button click
        findViewById(R.id.pay_now_button).setOnClickListener(v -> proceedToPayment());
    }

    // Simulate the payment process
    private void proceedToPayment() {
        // Here you can integrate with a real payment gateway
        // For now, we just show a Toast message as a mock payment process
        Toast.makeText(this, "Payment Successful! Order Confirmed.", Toast.LENGTH_SHORT).show();

        // You can navigate to the confirmation page or show an order summary
        finish();  // Close the PaymentActivity after "payment"
    }
}
