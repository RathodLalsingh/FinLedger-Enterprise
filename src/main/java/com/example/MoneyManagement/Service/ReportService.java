package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Dtos.ReportDTO;
import com.example.MoneyManagement.Entity.UserEntity;

public interface ReportService {

    ReportDTO generateUserReport(UserEntity user);
    byte[] generateDailyReport(UserEntity user);

}