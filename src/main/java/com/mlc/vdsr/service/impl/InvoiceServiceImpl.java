package com.mlc.vdsr.service.impl;

import com.mlc.vdsr.dto.InvoiceDTO;
import com.mlc.vdsr.entity.Invoice;
import com.mlc.vdsr.exception.InvoiceNotFoundException;
import com.mlc.vdsr.repository.InvoiceRepository;
import com.mlc.vdsr.service.InvoiceService;
import com.mlc.vdsr.utils.DTOToEntityConverter;
import com.mlc.vdsr.utils.EntityToDTOConverter;
import com.mlc.vdsr.enums.InvoiceStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Production implementation for {@link com.mlc.vdsr.service.InvoiceService}.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    /**
     * Invoice repository.
     */
    private final InvoiceRepository invoiceRepository;

    /**
     * Constructor.
     *
     * @param invoiceRepository is the invoice repository.
     */
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Returns all invoices.
     *
     * @return List of InvoiceDTO.
     */
    @Override
    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = this.invoiceRepository.findAll();

        List<InvoiceDTO> invoiceDTOS = new ArrayList<>();

        for (Invoice invoice : invoices) {
            invoiceDTOS.add(EntityToDTOConverter.convertToInvoiceDTO(invoice));
        }

        return invoiceDTOS;
    }

    /**
     * Creates an invoice.
     *
     * @param invoiceDTO is the invoice's DTO.
     */
    @Override
    public void createInvoice(final InvoiceDTO invoiceDTO) {
        Invoice invoice = DTOToEntityConverter.convertToInvoiceEntity(invoiceDTO);

        this.invoiceRepository.save(invoice);
    }

    /**
     * Deletes an invoice.
     *
     * @param id is the invoice's id.
     */
    @Override
    public void deleteInvoice(Long id) {
        Invoice invoice = this.invoiceRepository.findById(id)
                .orElseThrow(InvoiceNotFoundException::new);

        this.invoiceRepository.delete(invoice);
    }

    /**
     * Updates invoice status.
     *
     * @param id is the invoice's id.
     * @param status is the invoice's status.
     */
    @Override
    public void updateInvoiceStatus(Long id, InvoiceStatus status) {
        Invoice invoice = this.invoiceRepository.findById(id)
                .orElseThrow(InvoiceNotFoundException::new);

        invoice.setStatus(status);

        this.invoiceRepository.save(invoice);
    }
}
