package io.bank.management.service;

import io.bank.management.entity.Logs;

import java.util.Date;

public interface LogsService {
    Logs addNewLog(String operation, String email, String userType, Date timeStamp);
}
