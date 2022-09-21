package io.bank.management.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.bank.management.entity.Account;
import io.bank.management.repository.AccountRepository;
import io.bank.management.service.ClientService;
import io.bank.management.service.LogsService;
import io.bank.management.service.TransferMoneyService;
import io.bank.management.service.UserService;
import io.bank.management.utilsVariables.Variables;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class TransferMoneyController {

    private AccountRepository accountRepository;
    private TransferMoneyService transferMoneyService;
    private LogsService logsService;
    private UserService userService;

    private ClientService clientService;

    public TransferMoneyController(AccountRepository accountRepository, TransferMoneyService transferMoneyService, LogsService logsService, UserService userService, ClientService clientService) {
        this.accountRepository = accountRepository;
        this.transferMoneyService = transferMoneyService;
        this.logsService = logsService;
        this.userService = userService;
        this.clientService = clientService;
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

                    // Ecrire les LOGS
                    String adresseEmailSender = clientService.showOneClient(idSender).getEmail();
                    String adresseEmailSendTo = clientService.showOneClient(idSendTo).getEmail();
                    String operation=adresseEmailSender + " a envoyé " + money + " euros à : " + adresseEmailSendTo;

                    String authToken=request.getHeader("Authorization");
                    String jwtAccessToken=authToken.substring(7);
                    Algorithm algorithm=Algorithm.HMAC256(Variables.SECRET);
                    JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwtAccessToken);
                    String emailUser=decodedJWT.getSubject();
                    String [] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    String userRoles=String.join(",", roles);
                    Date userDateAction=new Date();

                    logsService.addNewLog(operation,emailUser,userRoles,userDateAction);

                    // FIN DES LOGS

                    return "Somme transférée avec succès";
                }
            }
        }
    }
}
