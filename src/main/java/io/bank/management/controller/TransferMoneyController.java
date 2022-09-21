package io.bank.management.controller;

import io.bank.management.entity.Account;
import io.bank.management.repository.AccountRepository;
import io.bank.management.service.TransferMoneyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TransferMoneyController {

    private AccountRepository accountRepository;
    private TransferMoneyService transferMoneyService;

    public TransferMoneyController(AccountRepository accountRepository, TransferMoneyService transferMoneyService) {
        this.accountRepository = accountRepository;
        this.transferMoneyService = transferMoneyService;
    }

    @PostMapping("/transferMoney")
    public String TransferMoney(HttpServletRequest request, HttpServletResponse response) throws Exception{

        if(request.getParameter("idSender")==null) {return "L'ID SENDER ne peut pas être null";}
        if(request.getParameter("money")==null) {return "La somme ne peut pas être null";}
        if(request.getParameter("idSendTo")==null) {return "L'ID SEND TO ne peut pas être null";}

        Long idSender= Long.valueOf(0);
        Double money=2.0;
        Long idSendTo=Long.valueOf(0);

        try {
            idSender=Long.parseLong(request.getParameter("idSender"));
        }catch (Exception e){
            return "L'ID SENDER ne correspond pas à un chiffre";
        }

        try {
            money=Double.parseDouble(request.getParameter("money"));
        }catch (Exception e){
            return "La somme ne correspond pas à un chiffre";
        }

        try {
            idSendTo=Long.parseLong(request.getParameter("idSendTo"));
        }catch (Exception e){
            return "L'ID SEND TO ne correspond pas à un chiffre";
        }

        if(accountRepository.findByOwner(idSender)==null){
            return "Le compte bancaire de l'envoyeur est introuvable";
        }else{
            if(accountRepository.findByOwner(idSendTo)==null){
                return "Le compte bancaire du récepteur est introuvable";
            }else{

                Account sender=accountRepository.findByOwner(idSender);

                if(sender.getBalance()<money){
                    return "Débit insuffisant, vous avez seulement : " + sender.getBalance() +" euros";
                }else{
                    transferMoneyService.transferMoney(idSender,money,idSendTo);
                    return "Somme transférée avec succès";
                }
            }
        }
    }
}
