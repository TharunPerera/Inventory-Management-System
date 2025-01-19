package com.example.InventoryService.Service;

import com.example.InventoryService.Entity.Inventory;
import com.example.InventoryService.Repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUCT_SERVICE_URL = "http://localhost:8085/api/products";


    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }



//    public Inventory addStock(Long productId, int stockLevel) {
//        Inventory inventory = inventoryRepository.findByProductId(productId)
//                .orElse(new Inventory());
//        inventory.setProductId(productId);
//        inventory.setStockLevel(stockLevel);
//        inventory.setLastUpdated(LocalDateTime.now());
//        return inventoryRepository.save(inventory);
//    }

    public Inventory addStock(Long productId, int stockLevel) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElse(new Inventory());
        inventory.setProductId(productId);
        inventory.setStockLevel(stockLevel);
        inventory.setLastUpdated(LocalDateTime.now());

        // Save inventory
        Inventory savedInventory = inventoryRepository.save(inventory);

        // Update product availability if stock is added
        if (stockLevel > 0) {
            updateProductAvailability(productId, true);
        }

        return savedInventory;
    }

    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }
    public Inventory updateInventory(Long productId, Inventory inventoryRequest) {
        // Find the inventory record by product ID
        Inventory existingInventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product ID: " + productId));

        // Update inventory details
        existingInventory.setStockLevel(inventoryRequest.getStockLevel());

        // Save updated inventory
        Inventory updatedInventory = inventoryRepository.save(existingInventory);

        // Check stock level and update product availability if needed
        if (updatedInventory.getStockLevel() == 0) {
            // Update product availability to false via RestTemplate
            updateProductAvailability(productId, false);
        } else if (updatedInventory.getStockLevel() > 0) {
            // Ensure product availability is set to true if stock is replenished
            updateProductAvailability(productId, true);
        }

        return updatedInventory;
    }

    private void updateProductAvailability(Long productId, boolean availability) {
        // Make a PUT request to ProductService to update availability
        String url = PRODUCT_SERVICE_URL + "/" + productId + "/availability?availability=" + availability;
        try {
            restTemplate.put(url, null);
            System.out.println("Product availability updated: Product ID = " + productId + ", Availability = " + availability);
        } catch (Exception e) {
            System.err.println("Failed to update product availability: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
