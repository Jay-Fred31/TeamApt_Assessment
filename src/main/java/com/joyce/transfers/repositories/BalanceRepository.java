package com.joyce.transfers.repositories;

import com.joyce.transfers.models.Balance;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BalanceRepository extends CrudRepository<Balance, Integer> {

    List<Balance> findAll();
    Optional<Balance> findByAccount(long account);

}
