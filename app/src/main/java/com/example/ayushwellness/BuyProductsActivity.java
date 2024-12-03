package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayushwellness.models.Product;
import java.util.ArrayList;
import java.util.List;

public class BuyProductsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);

        recyclerView = findViewById(R.id.rv_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize product list
        productList = new ArrayList<>();
        productList.add(new Product("Herbal Tea", "Rs. 500", R.drawable.product7));
        productList.add(new Product("Ayurvedic Oil", "Rs. 300", R.drawable.product8));
        productList.add(new Product("Turmeric Capsules", "Rs. 700", R.drawable.product9));
        productList.add(new Product("Neem Powder", "Rs. 250", R.drawable.product10));

        // Set adapter
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductImageClick(Product product) {
                // Navigate to product info
                Intent intent = new Intent(BuyProductsActivity.this, ProductInfoActivity.class);
                intent.putExtra("productName", product.getName());
                intent.putExtra("productPrice", product.getPrice());
                intent.putExtra("productImage", product.getImageResId());
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Product product) {
                // Handle add-to-cart action
                Toast.makeText(BuyProductsActivity.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBuyNowClick(Product product) {
                // Handle buy-now action
                Toast.makeText(BuyProductsActivity.this, "Proceeding to buy " + product.getName(), Toast.LENGTH_SHORT).show();
                // Navigate to payment or confirmation activity here
            }

            @Override
            public void onDeleteProductClick(Product product) {

            }
        });
        recyclerView.setAdapter(productAdapter);
    }
}
