package com.payment_order.Repository;

import com.payment_order.Entity.Payment;
import com.payment_order.Entity.SumByPurpose;
import com.payment_order.Entity.SumByRecip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    //НИЖЕ МЕТОДЫ ВСЕ РАБОТАЮТ:
     /* @Query("SELECT p.recipient, p.sum FROM Payment p")
       List<Object[]> sumTotalSumByRecipient();*/
   /* @Query("SELECT new com.payment_order.com.payment_order.Entity.SumRecip(p.recipient,p.sum) FROM Payment p")
    List<SumRecip> sumTotalSumByRecipient();*/

   /* @Query("SELECT new com.payment_order.com.payment_order.Entity.CountRecip(p.recipient,COUNT(p)) FROM Payment p GROUP BY p.recipient")
    List<CountRecip> sumTotalSumByRecipient();*/

    @Query("SELECT new com.payment_order.Entity.SumByRecip(p.recipient,SUM(p.sum)) FROM Payment p GROUP BY p.recipient")
    List<SumByRecip> totalSumByRecipient();

    @Query("SELECT new com.payment_order.Entity.SumByPurpose(p.purpose,SUM(p.sum)) FROM Payment p GROUP BY p.purpose")
    List<SumByPurpose> totalSumByPurpose();

    @Query("SELECT case when count(p)>0 then true else false end FROM Payment p WHERE p.number = :number")
    boolean isNumberExists(@Param("number") int number); //проверка уникальности вводимого номера платежа

}
