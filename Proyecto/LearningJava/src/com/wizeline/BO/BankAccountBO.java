package com.wizeline.BO;

import com.wizeline.DTO.BankAccountDTO;

import java.util.List;

/* Uso de por lo menos una interfaz propia: BankAccountBO */
public interface BankAccountBO {
    List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);
}
