package com.backend.librarymanagementsystem.Repository;

import com.backend.librarymanagementsystem.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    @Query(value = "select * from transaction t where t.card_card_no=:cardId AND t.transaction_status='SUCCESS'",
            nativeQuery = true)
    List<Transaction> getAllSuccessfullTxnsWithCardNo(int cardId);
}
