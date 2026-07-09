package mx.tecdesoftware.market_backend.web.controller;

import mx.tecdesoftware.market_backend.domain.Purchase;
import mx.tecdesoftware.market_backend.domain.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAll());
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<Purchase>> getByClientId(@PathVariable("id") String clientId) {
        List<Purchase> purchases = purchaseService.getByClientId(clientId);
        if (purchases.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/save")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        Purchase saved = purchaseService.save(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}