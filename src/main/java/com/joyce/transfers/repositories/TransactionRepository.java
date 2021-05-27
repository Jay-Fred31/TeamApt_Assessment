package com.joyce.transfers.repositories;

import com.joyce.transfers.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findAll();
    Optional<Transaction> findByReference(String reference);
}
