package com.example.studentmanagementsystem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

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
        return transactionRepository.findByStudentId(studentId);
    }

    @Override
    public Transaction makePayment(Long studentId, DepositeRequestDTO request) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Số tiền phải lớn hơn 0");
        }

        Transaction transaction = new Transaction();
        transaction.setStudent(student);
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription() != null ? request.getDescription() : "Tuition Payment");

        // TransactionDate được set tự động trong @PrePersist của Entity
        return transactionRepository.save(transaction);
    }

    @Override
    public PaymentResponseDTO getPaymentSummary(Long studentId) {

        // take the total of the tuition have to pay (call from logic calculate credit
        // from UserService)
        StudentDashboardDTO dashboardInfo = userService.getStudentDashboardInfo(studentId);
        BigDecimal totalTuition = dashboardInfo.getTuitionOwed();

        // calculate total tuition have to pay from table Transaction
        List<Transaction> transactions = transactionRepository.findByStudentId(studentId);
        BigDecimal totalPaid = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // calculate balance
        BigDecimal balance = totalTuition.subtract(totalPaid);
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            balance = BigDecimal.ZERO;

        return new PaymentResponseDTO(totalTuition, totalPaid, balance);

    }
}
