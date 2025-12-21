package com.example.studentmanagementsystem.service;

import java.util.List;

import com.example.studentmanagementsystem.dto.request.DepositeRequestDTO;
import com.example.studentmanagementsystem.dto.response.PaymentResponseDTO;
import com.example.studentmanagementsystem.entity.Transaction;

public interface PaymentService {

    List<Transaction> getStudentTransactions(Long studentId);

    Transaction makePayment(Long studentId, DepositeRequestDTO request);

    PaymentResponseDTO getPaymentSummary(Long studentId);

    Transaction payTuitionFromWallet(Long studentId);
}
