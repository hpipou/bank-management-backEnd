package io.bank.management.controller;

import io.bank.management.entity.Account;
import io.bank.management.entity.Client;
import io.bank.management.repository.ClientRepository;
import io.bank.management.service.AccountService;
import io.bank.management.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private AccountService accountService;
    private ClientService clientService;
    private ClientRepository clientRepository;

    public AccountController(AccountService accountService, ClientService clientService, ClientRepository clientRepository) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/accounts")
    public String addNewAccount(@RequestBody Account account){

        if(accountService.showOneAccount(account.getOwner())==null){

            if(clientService.showOneClient(account.getOwner())==null){
                return "Le compte client est introuvable";
            }else{
                accountService.addNewAccount(account);
                Client client=clientRepository.findById(account.getOwner()).orElse(null);
                client.setAccounts(account);
                clientRepository.save(client);
                return "Compte bancaire crée avec succès";
            }

        }else{
            return "Votre compte bancaire existe déjà";
        }
    }

    @PatchMapping("/accounts")
    public String editAccount(@RequestBody Account account){
        if(accountService.showOneAccount(account.getOwner())==null){
            return "Le compte bancaire est introuvable";
        }else{
            accountService.editAccount(account);
            return "Compte bancaire modifié avec succès";
        }
    }

    @DeleteMapping("/accounts/{id}")
    public String deleteAccount(@PathVariable Long id){
        if(accountService.showOneAccount(id)==null){
            return "Le compte bancaire est introuvable";
        }else{
            accountService.deleteAccount(id);
            return "Compte bancaire supprimé avec succès";
        }
    }

    @GetMapping("/accounts/{id}")
    public Account showOneAccount(@PathVariable Long id){
        return accountService.showOneAccount(id);
    }

    @GetMapping("/accounts")
    public List<Account> showAllAccountes(){
        return accountService.showAllAccountes();
    }
}
