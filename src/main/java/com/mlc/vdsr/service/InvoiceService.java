package com.mlc.vdsr.service;

import com.mlc.vdsr.dto.InvoiceDTO;

import java.util.List;

/**
 * Invoice service interface.
 */
public interface InvoiceService {
    /**
     * Returns all invoices.
     *
     * @return List of InvoiceDTOs.
     */
    List<InvoiceDTO> getAllInvoices();

    /**
     * Creates an invoice.
     *
     * @param invoiceDTO is the Invoice's DTO.
     */
    void createInvoice(final InvoiceDTO invoiceDTO);

    /**
     * Deletes an invoice.
     *
     * @param id is the invoice's id.
     */
    void deleteInvoice(Long id);
}
