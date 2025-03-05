package com.mlc.vdsr.repository;


import com.mlc.vdsr.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Invoice JPA repository.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
