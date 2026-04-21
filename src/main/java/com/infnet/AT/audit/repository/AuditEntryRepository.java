package com.infnet.AT.audit.repository;

import com.infnet.AT.audit.entity.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {
}
