package com.joyce.transfers.controllers;

import com.joyce.transfers.dto.TransactionRequest;
import com.joyce.transfers.dto.TransactionResponse;
import com.joyce.transfers.models.Balance;
import com.joyce.transfers.models.Transaction;
import com.joyce.transfers.repositories.BalanceRepository;
import com.joyce.transfers.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("transaction")
public class TransactionController {
    private TransactionRepository transactionRepository;
    private BalanceRepository balanceRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, BalanceRepository balanceRepository) {
        this.transactionRepository = transactionRepository;
        this.balanceRepository = balanceRepository;
    }


    @GetMapping("/")
    public @ResponseBody List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @PostMapping("/transfer")
    public @ResponseBody TransactionResponse transfer (@RequestBody TransactionRequest transactionRequest) {
        System.out.printf("\n\nTransaction Request -> %s\n\n", transactionRequest);
        TransactionResponse response = new TransactionResponse(true, "Transaction Successful");

        Optional<Balance> fromAccountOpt = balanceRepository.findByAccount(transactionRequest.getFromAccount());
        Optional<Balance> toAccountOpt = balanceRepository.findByAccount(transactionRequest.getToAccount());

        if (fromAccountOpt.isEmpty()) {
            return new TransactionResponse(false, String.format("Account %s does not exist", transactionRequest.getFromAccount()));
        }

        if (toAccountOpt.isEmpty()) {
            return new TransactionResponse(false, String.format("Account %s does not exist", transactionRequest.getToAccount()));
        }

        Optional<Transaction> existingTransaction = transactionRepository.findByReference(transactionRequest.getReference());

        if(existingTransaction.isPresent()) {
            return new TransactionResponse(false, String.format("Transaction with Reference %s already exist", transactionRequest.getReference()));
        }

        Balance fromAccount = fromAccountOpt.get();
        Balance toAccount = toAccountOpt.get();

        Long fromAccountBalance = fromAccount.getBalance();
        Long toAccountBalance = toAccount.getBalance();

        if (fromAccountBalance < transactionRequest.getAmount()) {
            return new TransactionResponse(false, "Insufficient Account Balance");
        }

        fromAccount.setBalance(fromAccountBalance - transactionRequest.getAmount());
        toAccount.setBalance(toAccountBalance + transactionRequest.getAmount());

        Transaction transaction = new Transaction(transactionRequest.getReference(), transactionRequest.getAmount(), transactionRequest.getFromAccount());

        transactionRepository.save(transaction);

        balanceRepository.save(fromAccount);
        balanceRepository.save(toAccount);

        return response;
    }


}
