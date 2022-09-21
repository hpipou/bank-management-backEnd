package io.bank.management.repository;

import io.bank.management.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs,Long> {
}
