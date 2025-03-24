package com.kiran.springboot_sso_app.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
class BankController {
    private final Map<Long, Double> accounts = new HashMap<>();
    private long accountCounter = 1;

    @GetMapping("/contact")
    public String getContactInfo() {
        return "<h2>Contact us at support@gmail.com</h2>";
    }

    @GetMapping("/user")
    public Map<String, Object> getUserDetails(@AuthenticationPrincipal OidcUser user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("name", user.getFullName());
        userInfo.put("email", user.getEmail());
        return userInfo;
    }

    @GetMapping("/account/create")
    public String createAccount() {
        accounts.put(accountCounter, 0.0);
        return "Account created with ID: " + accountCounter++;
    }

    @GetMapping("/account/balance")
    public String checkBalance(@RequestParam Long accountId) {
        return "Account Balance: " + accounts.getOrDefault(accountId, 0.0);
    }

    @GetMapping("/account/deposit")
    public String deposit(@RequestParam Long accountId, @RequestParam double amount) {
        accounts.put(accountId, accounts.getOrDefault(accountId, 0.0) + amount);
        return "Deposited " + amount + ". New Balance: " + accounts.get(accountId);
    }

    @GetMapping("/account/withdraw")
    public String withdraw(@RequestParam Long accountId, @RequestParam double amount) {
        double balance = accounts.getOrDefault(accountId, 0.0);
        if (balance >= amount) {
            accounts.put(accountId, balance - amount);
            return "Withdrawn " + amount + ". New Balance: " + accounts.get(accountId);
        }
        return "Insufficient Balance!";
    }

    @GetMapping("/account/transfer")
    public String transfer(@RequestParam Long fromAccount, @RequestParam Long toAccount, @RequestParam double amount) {
        if (accounts.getOrDefault(fromAccount, 0.0) >= amount) {
            accounts.put(fromAccount, accounts.get(fromAccount) - amount);
            accounts.put(toAccount, accounts.getOrDefault(toAccount, 0.0) + amount);
            return "Transferred " + amount + " to Account " + toAccount;
        }
        return "Insufficient Funds!";
    }
}