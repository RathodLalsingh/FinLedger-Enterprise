package com.example.MoneyManagement.Repository;

import com.example.MoneyManagement.Entity.CategoryEntity;
import com.example.MoneyManagement.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    List<CategoryEntity> findByUser(UserEntity user);
    CategoryEntity findByNameAndUser(
            String name,
            UserEntity user
    );
    List<CategoryEntity> findByType(String type);

    List<CategoryEntity> findByTypeAndUser(
            String type,
            UserEntity user
    );


}
