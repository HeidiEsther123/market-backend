package mx.tecdesoftware.market_backend.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.tecdesoftware.market_backend.domain.Purchase;
import mx.tecdesoftware.market_backend.domain.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Purchase", description = "Manage purchases made by clients")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/all")
    @Operation(summary = "Get all purchases", description = "Return a list of all registered purchases")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of purchases")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Purchase>> getAll() {
        return ResponseEntity.ok(purchaseService.getAll());
    }

    @GetMapping("/client/{id}")
    @Operation(summary = "Get purchases by client ID", description = "Return all purchases made by a specific client")
    @ApiResponse(responseCode = "200", description = "Purchases found for the client")
    @ApiResponse(responseCode = "404", description = "No purchases found for the provided client ID")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Purchase>> getByClientId(@Parameter(description = "ID of the client to retrieve purchases for", example = "4546221", required = true)@PathVariable("id") String clientId) {
        List<Purchase> purchases = purchaseService.getByClientId(clientId);
        if (purchases.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(purchases);
    }

    @PostMapping("/save")
    @Operation(
            summary = "Create a new purchase",
            description = "Register a new purchase with its detailed items and return it",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Purchase",
                                    value =
                                            """
                                            {
                                              "clientId": "4546221",
                                              "date": "2026-07-14T11:00:00",
                                              "paymentMethod": "E",
                                              "comment": "Compra de frutas de temporada",
                                              "status": "P",
                                              "items": [
                                                {
                                                  "productId": 1,
                                                  "quantity": 5,
                                                  "total": 1500.00
                                                },
                                                {
                                                  "productId": 2,
                                                  "quantity": 2,
                                                  "total": 4200.00
                                                }
                                              ]
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Purchase created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid purchase data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Purchase conflict (duplicate transaction or reference)")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase) {
        Purchase saved = purchaseService.save(purchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}