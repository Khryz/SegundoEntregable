package com.wizeline.BO;

import com.wizeline.DTO.BankAccountDTO;
import com.wizeline.enums.AccountType;
import com.wizeline.enums.Country;
import com.wizeline.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankAccountBOImpl implements BankAccountBO{
    @Override
    public List<BankAccountDTO> getAccounts() {
        /* Uso de por lo menos un tipo de dato abstracto: List */
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(buildBankAccount("user1@WIZELINE.com", true, Country.US, LocalDateTime.now().minusDays(7)));
        accountDTOList.add(buildBankAccount("user2@WIZELINE.com", true, Country.MX, LocalDateTime.now().minusMonths(2)));
        accountDTOList.add(buildBankAccount("user3@WIZELINE.com", true, Country.FR, LocalDateTime.now().minusYears(4)));
        return accountDTOList;
    }

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        /* Uso de api de fechas y tiempos en un método */

        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);

        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        /* Uso de dos tipos de datos encapsulados: long, String, double, boolean */
        bankAccountDTO.setAccountNumber(123L);
        bankAccountDTO.setAccountName("Dummy Account");
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(123.5);
        bankAccountDTO.setAccountType(AccountType.NOMINA);
        bankAccountDTO.setCountry(Utils.getCountry(country));
        bankAccountDTO.setAccountActive(isActive);
        bankAccountDTO.getLastUsage();  // Metodo para obtener el ultimo usado

        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);

        return bankAccountDTO;
    }
}
