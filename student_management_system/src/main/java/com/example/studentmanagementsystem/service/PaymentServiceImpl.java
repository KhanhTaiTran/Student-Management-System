package com.example.studentmanagementsystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.studentmanagementsystem.dto.request.DepositeRequestDTO;
import com.example.studentmanagementsystem.dto.response.PaymentResponseDTO;
import com.example.studentmanagementsystem.dto.response.StudentDashboardDTO;
import com.example.studentmanagementsystem.entity.Transaction;
import com.example.studentmanagementsystem.entity.User;
import com.example.studentmanagementsystem.repository.TransactionRepository;
import com.example.studentmanagementsystem.repository.UserRepository;

@Service
public class PaymentServiceImpl implements PaymentService {
    UserRepository userRepository;
    TransactionRepository transactionRepository;
    UserService userService;

    public PaymentServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository,
            UserService userService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    @Override
    public List<Transaction> getStudentTransactions(Long studentId) {
        // call func from repository has been sorted
        return transactionRepository.findByStudentIdOrderByTransactionDateDesc(studentId);
    }

    @Override
    public Transaction makePayment(Long studentId, DepositeRequestDTO request) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }

        // create transaction
        Transaction transaction = new Transaction();
        transaction.setStudent(student);
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription() != null ? request.getDescription() : "Tuition Payment");

        // update balance
        BigDecimal currentBalance = student.getBalance() != null ? student.getBalance() : BigDecimal.ZERO;
        student.setBalance(currentBalance.add(request.getAmount()));
        userRepository.save(student);

        return transactionRepository.save(transaction);
    }

    @Override
    public PaymentResponseDTO getPaymentSummary(Long studentId) {
        // Calculate the total tuition fee
        StudentDashboardDTO dashboardInfo = userService.getStudentDashboardInfo(studentId);
        BigDecimal totalTuition = dashboardInfo.getTuitionOwed();

        // Get the total amount deposited/paid.
        List<Transaction> transactions = transactionRepository.findByStudentIdOrderByTransactionDateDesc(studentId);
        BigDecimal totalPaid = transactions.stream()
                .map(Transaction::getAmount)
                .filter(amount -> amount.compareTo(BigDecimal.ZERO) < 0) // Only negative numbers are accepted.
                .map(BigDecimal::abs) // Convert to a positive number for calculation.
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate the amount of debt. (Total - Paid)
        BigDecimal debt = totalTuition.subtract(totalPaid);
        if (debt.compareTo(BigDecimal.ZERO) < 0)
            debt = BigDecimal.ZERO;

        // get current balance from wallet
        User user = userRepository.findById(studentId).orElseThrow();
        BigDecimal walletBalance = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;

        return new PaymentResponseDTO(totalTuition, totalPaid, debt, walletBalance);
    }

    @Override
    @Transactional
    public Transaction payTuitionFromWallet(Long studentId) {
        // Get current debt
        PaymentResponseDTO summary = getPaymentSummary(studentId);
        BigDecimal debt = summary.getDebt();

        if (debt.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("You no longer owe tuition fees.!");
        }

        // take user to check wallet balance
        User user = userRepository.findById(studentId).orElseThrow();
        BigDecimal currentBalance = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;

        // enough money??
        if (currentBalance.compareTo(debt) < 0) {
            throw new RuntimeException("Insufficient balance! Please top up your account.");
        }

        // Deduct from your wallet
        user.setBalance(currentBalance.subtract(debt));
        userRepository.save(user);

        // Save transaction history (NEGATIVE numbers to mark as payments made).
        Transaction transaction = new Transaction();
        transaction.setStudent(user);
        transaction.setAmount(debt.negate()); // Store negative numbers (Example: -5,000,000)
        transaction.setDescription("Paid Tuition Fees");

        return transactionRepository.save(transaction);
    }
}
