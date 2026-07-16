package com.example.MoneyManagement.Repository;

import com.example.MoneyManagement.Entity.ExpenseEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByUser(UserEntity user);
    List<ExpenseEntity> findByUserAndExpenseDateBetween(
            UserEntity user,
            LocalDate startDate,
            LocalDate endDate
    );
    List<ExpenseEntity> findByExpenseDateAfter(LocalDate date);
    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM ExpenseEntity e
            WHERE e.user = :user
            """)
    BigDecimal getTotalExpense(@Param("user") UserEntity user);
    List<ExpenseEntity> findTop10ByUserOrderByExpenseDateDesc(UserEntity user);
}