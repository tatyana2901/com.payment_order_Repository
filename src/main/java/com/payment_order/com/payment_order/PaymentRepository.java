package com.payment_order.com.payment_order;

/*import org.hibernate.mapping.List;*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    //НИЖЕ МЕТОДЫ ВСЕ РАБОТАЮТ:
     /* @Query("SELECT p.recipient, p.sum FROM Payment p")
       List<Object[]> sumTotalSumByRecipient();*/
   /* @Query("SELECT new com.payment_order.com.payment_order.SumRecip(p.recipient,p.sum) FROM Payment p")
    List<SumRecip> sumTotalSumByRecipient();*/

   /* @Query("SELECT new com.payment_order.com.payment_order.CountRecip(p.recipient,COUNT(p)) FROM Payment p GROUP BY p.recipient")
    List<CountRecip> sumTotalSumByRecipient();*/

    @Query("SELECT new com.payment_order.com.payment_order.SumByRecip(p.recipient,SUM(p.sum)) FROM Payment p GROUP BY p.recipient")
    List<SumByRecip> totalSumByRecipient();


    @Query("SELECT new com.payment_order.com.payment_order.SumByPurpose(p.purpose,SUM(p.sum)) FROM Payment p GROUP BY p.purpose")
    List<SumByPurpose> totalSumByPurpose();


}
