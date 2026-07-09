package mx.tecdesoftware.market_backend.domain.service;

import mx.tecdesoftware.market_backend.domain.Purchase;
import mx.tecdesoftware.market_backend.domain.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getAll() {
        return purchaseRepository.getAll();
    }

    public List<Purchase> getByClientId(String clientId) {
        return purchaseRepository.getByClientId(clientId);
    }

    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }
}