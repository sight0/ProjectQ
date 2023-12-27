package ae.s1ght.projectq.service.impl;

import ae.s1ght.projectq.dao.PaymentRepository;
import ae.s1ght.projectq.enums.PaymentStatus;
import ae.s1ght.projectq.model.Payment;
import ae.s1ght.projectq.model.User;
import ae.s1ght.projectq.service.PaymentServiceINT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceIMPL implements PaymentServiceINT {
    private final PaymentRepository paymentRepository;
    @Override
    @Transactional
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.getReferenceById(id);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return null;
    }

    @Override
    public void deletePayment(Long id) {

    }

    @Override
    public List<Payment> getAllPaymentsByUser(User user) {
        return null;
    }

    @Override
    public List<Payment> getAllPayments() {
        return null;
    }

    @Override
    @Transactional
    public Payment processPayment(Payment payment) {
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment refundPayment(Long paymentId) {
        return null;
    }

    @Override
    public PaymentStatus checkPaymentStatus(Long paymentId) {
        return null;
    }

    @Override
    public double calculateTotalRevenue() {
        return 0;
    }
}
