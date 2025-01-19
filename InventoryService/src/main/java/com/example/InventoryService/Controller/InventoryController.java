package com.example.InventoryService.Controller;

import com.example.InventoryService.Entity.Inventory;
import com.example.InventoryService.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Inventory> addStock(
            @PathVariable Long productId,
            @RequestBody Inventory inventoryRequest) { // Accept JSON payload
        Inventory inventory = inventoryService.addStock(productId, inventoryRequest.getStockLevel());
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update Inventory endpoint
    @PutMapping("/{productId}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable Long productId,
            @RequestBody Inventory inventoryRequest) {
        // Update the inventory based on product ID
        Inventory updatedInventory = inventoryService.updateInventory(productId, inventoryRequest);
        return ResponseEntity.ok(updatedInventory);
    }





}
