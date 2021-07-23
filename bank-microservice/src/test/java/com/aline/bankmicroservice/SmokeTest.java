package com.aline.bankmicroservice;

import com.aline.bankmicroservice.controller.BranchController;
import com.aline.bankmicroservice.controller.RootController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SmokeTest {
    @Autowired
    BankMicroserviceApplication application;

    @Autowired
    RootController rootController;

    @Autowired
    BranchController branchController;

    @Test
    void contextLoads() {
        assertNotNull(application);
        assertNotNull(rootController);
        assertNotNull(branchController);
    }
}
