
package com.example.MoneyManagement.Repository;

import com.example.MoneyManagement.Entity.IncomeEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<IncomeEntity, Long> {
    List<IncomeEntity> findByUser(UserEntity user);
    Optional<IncomeEntity> findByIdAndUser(
            Long id,
            UserEntity user
    );
    List<IncomeEntity> findByUserAndIncomeDateBetween(
            UserEntity user,
            LocalDate startDate,
            LocalDate endDate
    );
    List<IncomeEntity> findByIncomeDateAfter(LocalDate date);

    @Query("""
            SELECT COALESCE(SUM(i.amount), 0)
            FROM IncomeEntity i
            WHERE i.user = :user
            """)
    BigDecimal getTotalIncome(
            @Param("user") UserEntity user
    );

    List<IncomeEntity> findTop10ByUserOrderByIncomeDateDesc(
            UserEntity user
    );
    @Query("""
            SELECT i
            FROM IncomeEntity i
            JOIN FETCH i.user
            JOIN FETCH i.category
            WHERE i.user = :user
            ORDER BY i.incomeDate DESC
            """)
    List<IncomeEntity> findAllIncomeForExcel(
            @Param("user") UserEntity user
    );
}
