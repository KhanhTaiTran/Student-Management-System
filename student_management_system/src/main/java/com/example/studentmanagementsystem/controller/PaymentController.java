package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.dto.request.DepositeRequestDTO;
import com.example.studentmanagementsystem.dto.response.PaymentResponseDTO;
import com.example.studentmanagementsystem.entity.Transaction;
import com.example.studentmanagementsystem.security.CustomUserDetails;
import com.example.studentmanagementsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // summary (in debt, paid)
    @GetMapping("/summary")
    public ResponseEntity<PaymentResponseDTO> getSummary(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(paymentService.getPaymentSummary(user.getId()));
    }

    // transfer history
    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getHistory(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(paymentService.getStudentTransactions(user.getId()));
    }

    // pay (top up)
    @PostMapping("/pay")
    public ResponseEntity<Transaction> pay(@RequestBody DepositeRequestDTO request, Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(paymentService.makePayment(user.getId(), request));
    }

    @PostMapping("/pay-tuition")
    public ResponseEntity<Transaction> payTuition(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(paymentService.payTuitionFromWallet(user.getId()));
    }
}