package ae.s1ght.projectq.service;

import ae.s1ght.projectq.enums.PaymentMethod;
import ae.s1ght.projectq.enums.PaymentStatus;
import ae.s1ght.projectq.model.Payment;
import ae.s1ght.projectq.model.User;

import java.util.List;

public interface PaymentServiceINT {
    Payment createPayment(Payment payment);
    Payment getPaymentById(Long id);
    Payment updatePayment(Payment payment);
    void deletePayment(Long id);
    List<Payment> getAllPaymentsByUser(User user);
    List<Payment> getAllPayments();
    Payment processPayment(Payment payment);
    Payment refundPayment(Long paymentId);
    PaymentStatus checkPaymentStatus(Long paymentId);
    double calculateTotalRevenue();
}
