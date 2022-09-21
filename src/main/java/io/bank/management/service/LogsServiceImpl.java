package io.bank.management.service;

import io.bank.management.entity.Logs;
import io.bank.management.repository.LogsRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogsServiceImpl implements LogsService {

    private LogsRepository logsRepository;

    public LogsServiceImpl(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @Override
    public Logs addNewLog(String operation, String email, String userType, Date timeStamp) {
        Logs logs=new Logs();
        logs.setOperation(operation);
        logs.setEmail(email);
        logs.setUserType(userType);
        logs.setTimeStamp(timeStamp);
        return logsRepository.save(logs);
    }
}
