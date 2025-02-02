package com.grupo1.ms_operador.payments.infrastructure.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grupo1.ms_operador.payments.domain.dtos.PurchaseDto;
import com.grupo1.ms_operador.payments.domain.exceptions.PurchaseNotFoundException;
import com.grupo1.ms_operador.payments.domain.repositories.PurchaseRepository;
import com.grupo1.ms_operador.payments.infrastructure.entities.PurchaseEntity;
import lombok.AllArgsConstructor;

interface JpaPurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
}

@AllArgsConstructor
@Repository
public class MySqlPurchaseRepository extends PurchaseRepository {
        @Autowired
        private final JpaPurchaseRepository jpaPurchaseRepository;

        @Override
        public PurchaseDto save(PurchaseDto purchaseDto) {
                var purchaseEntity = new PurchaseEntity(
                                purchaseDto.getId(),
                                purchaseDto.getUserId(),
                                purchaseDto.getBookId(),
                                purchaseDto.getQuantity(),
                                purchaseDto.getTotalPrice(),
                                purchaseDto.getPurchaseDate());

                PurchaseEntity newPurchase = this.jpaPurchaseRepository.save(purchaseEntity);

                return new PurchaseDto(
                                newPurchase.getId(),
                                newPurchase.getUserId(),
                                newPurchase.getBookId(),
                                newPurchase.getQuantity(),
                                newPurchase.getTotalPrice(),
                                newPurchase.getPurchaseDate());
        }

        @Override
        public List<PurchaseDto> findAll() {
                return this.jpaPurchaseRepository.findAll().stream()
                                .map(purchase -> new PurchaseDto(
                                                purchase.getId(),
                                                purchase.getUserId(),
                                                purchase.getBookId(),
                                                purchase.getQuantity(),
                                                purchase.getTotalPrice(),
                                                purchase.getPurchaseDate()))
                                .toList();
        }

        @Override
        public PurchaseDto findById(Long id) {
                var purchase = this.jpaPurchaseRepository.findById(id);

                if (purchase.isEmpty()) {
                        throw new PurchaseNotFoundException("Purchase not found");
                }

                return new PurchaseDto(
                                purchase.get().getId(),
                                purchase.get().getUserId(),
                                purchase.get().getBookId(),
                                purchase.get().getQuantity(),
                                purchase.get().getTotalPrice(),
                                purchase.get().getPurchaseDate());
        }
}
