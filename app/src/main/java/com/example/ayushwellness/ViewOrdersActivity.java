package com.example.ayushwellness;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ayushwellness.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class ViewOrdersActivity extends AppCompatActivity {

    private ListView ordersListView;
    private ArrayList<String> ordersList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        ordersListView = findViewById(R.id.ordersListView);
        ordersList = new ArrayList<>();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Fetch orders from Firestore
        fetchOrders();
    }

    private void fetchOrders() {
        // Reference to the "orders" collection in Firestore
        CollectionReference ordersRef = db.collection("orders");

        // Fetch orders data
        ordersRef.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot documents = task.getResult();
                        if (documents != null) {
                            // Loop through each document and add order details to the list
                            for (QueryDocumentSnapshot document : documents) {
                                String orderTitle = document.getString("orderTitle");
                                String orderDate = document.getString("orderDate");
                                String orderAmount = document.getString("orderAmount");

                                // Format the order details
                                String orderDetails = "Title: " + orderTitle + "\nDate: " + orderDate + "\nAmount: " + orderAmount;
                                ordersList.add(orderDetails);
                            }

                            // Set up the adapter to display orders in the ListView
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ordersList);
                            ordersListView.setAdapter(adapter);
                        }
                    } else {
                        // Handle errors
                        Toast.makeText(ViewOrdersActivity.this, "Error getting orders.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
