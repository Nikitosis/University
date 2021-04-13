package com.oop.repository;

import com.oop.entities.dao.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    List<CreditCard> findByBankAccountUserId(Long userId);
}
