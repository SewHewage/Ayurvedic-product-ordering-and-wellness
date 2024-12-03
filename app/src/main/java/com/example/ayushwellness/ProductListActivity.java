package com.example.ayushwellness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayushwellness.models.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        RecyclerView recyclerView = findViewById(R.id.rv_product_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Create product list
        List<Product> products = new ArrayList<>();
        products.add(new Product("Joint Relief Oil", "Rs. 2300", R.drawable.product1));
        products.add(new Product("Diabsol Tablet", "Rs. 3500", R.drawable.product2));
        products.add(new Product("Amla Face Serum", "Rs. 1250", R.drawable.product3));
        products.add(new Product("Hand Cream", "Rs. 2200", R.drawable.product4));
        products.add(new Product("Night Face Cream", "Rs. 2350", R.drawable.product5));
        products.add(new Product("Ayurveda FootKio", "Rs. 4400", R.drawable.product6));

        // Set Adapter
        ProductAdapter adapter = new ProductAdapter(products, new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductImageClick(Product product) {
                Intent intent = new Intent(ProductListActivity.this, ProductInfoActivity.class);
                intent.putExtra("productName", product.getName());
                intent.putExtra("productPrice", product.getPrice());
                intent.putExtra("productImage", product.getImageResId());
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Product product) {
                Toast.makeText(ProductListActivity.this, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBuyNowClick(Product product) {
                Toast.makeText(ProductListActivity.this, "Proceeding to buy " + product.getName(), Toast.LENGTH_SHORT).show();
                // Navigate to payment/confirmation activity
            }

            @Override
            public void onDeleteProductClick(Product product) {

            }
        });
        recyclerView.setAdapter(adapter);
    }
}
