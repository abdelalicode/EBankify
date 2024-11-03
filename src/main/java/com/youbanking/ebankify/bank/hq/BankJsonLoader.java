package com.youbanking.ebankify.bank.HQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youbanking.ebankify.role.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
public class BankJsonLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BankJsonLoader.class);
    private final BankRepository bankRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final ObjectMapper objectMapper;

    public BankJsonLoader(BankRepository bankRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, ObjectMapper objectMapper) {
        this.bankRepository = bankRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
            if(bankRepository.count() == 0 ) {
                try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/banks.json")) {
                    Banks banks = objectMapper.readValue(inputStream, Banks.class);
                    bankRepository.saveAll(banks.banks());
                }
                catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }

            }
            else {
                log.info("Found {} banks", bankRepository.count());
            }

            if(roleRepository.count() == 0 ) {
                for (PermissionType permissionType : PermissionType.values()) {
                    Permission permission = new Permission();
                    permission.setName(permissionType);
                    permissionRepository.save(permission);
                }
                Role adminRole = new Role();
                adminRole.setName(String.valueOf(RoleType.ADMIN));
                adminRole.setPermissions(Arrays.asList(
                        permissionRepository.findByName(PermissionType.FULL_ACCESS)
                ));
                roleRepository.save(adminRole);

                Role employeeRole = new Role();
                employeeRole.setName(String.valueOf(RoleType.EMPLOYEE));
                employeeRole.setPermissions(Arrays.asList(
                        permissionRepository.findByName(PermissionType.APPROVE_TRANSACTIONS),
                        permissionRepository.findByName(PermissionType.VIEW_ALL_ACCOUNTS)
                ));
                roleRepository.save(employeeRole);

                Role clientRole = new Role();
                clientRole.setName(String.valueOf(RoleType.CLIENT));
                clientRole.setPermissions(Arrays.asList(
                        permissionRepository.findByName(PermissionType.MANAGE_OWN_ACCOUNT),
                        permissionRepository.findByName(PermissionType.VIEW_OWN_ACCOUNT)
                ));
                roleRepository.save(clientRole);
            }
    }
}
