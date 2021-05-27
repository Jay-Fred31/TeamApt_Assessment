package com.joyce.transfers.controllers;

import com.joyce.transfers.models.Balance;
import com.joyce.transfers.repositories.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("balance")
public class BalanceController {

    private BalanceRepository balanceRepository;

    @Autowired
    public BalanceController(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    @PostConstruct
    public void loadData() {
        var sampleCustomerBalance = new Balance(10000001, 50_000L);
        var sampleMovieBalance = new Balance(10000002, 0L);

        balanceRepository.save(sampleCustomerBalance);
        balanceRepository.save(sampleMovieBalance);
    }


    @GetMapping("/")
    public @ResponseBody List<Balance> getAllBalances() {
        return balanceRepository.findAll();
    }


}
