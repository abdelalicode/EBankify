package com.youbanking.ebankify.loanApp;

import com.youbanking.ebankify.common.BaseController;
import com.youbanking.ebankify.exception.UnAuthorizedException;
import com.youbanking.ebankify.permission.PermissionService;
import com.youbanking.ebankify.permission.PermissionType;
import com.youbanking.ebankify.user.User;
import com.youbanking.ebankify.user.UserRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanAppController extends BaseController {

    private final PermissionService permissionService;
    private final LoanAppService loanAppService;

    public LoanAppController(PermissionService permissionService, LoanAppService loanAppService) {
        this.permissionService = permissionService;
        this.loanAppService = loanAppService;
    }


    @PostMapping("/apply")
    public LoanApp apply(@RequestBody LoanApp loanApp , HttpServletRequest request) {
        Long authId = getUserId(request);

        if(permissionService.hasPermission(authId, PermissionType.MANAGE_OWN_ACCOUNT)) {
            return loanAppService.applyForLoan(loanApp, authId);
        }
        else {
            throw new UnAuthorizedException("You do not have permission to apply this loan app");
        }
    }


    @PostMapping("/approve")
    public LoanApp approve(@RequestBody ApproveLoanDTO loanDTO, HttpServletRequest request) {
        Long authId = getUserId(request);

        if(permissionService.hasPermission(authId, PermissionType.APPROVE_TRANSACTIONS)) {
            System.out.println(loanDTO);

            return  null;
        }
        else {
            throw new UnAuthorizedException("You do not have permission to approve this loan app");
        }
    }
}
