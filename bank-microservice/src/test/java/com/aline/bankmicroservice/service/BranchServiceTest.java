package com.aline.bankmicroservice.service;

import com.aline.core.model.Bank;
import com.aline.core.model.Branch;
import com.aline.core.paginated.BranchPaginated;
import com.aline.core.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Branch Service test")
@Slf4j(topic = "Branch Service test")
class BranchServiceTest {
    @Autowired
    BranchService branchService;

    @MockBean
    BranchRepository branchRepository;

    private List<Branch> branches;
    private BranchPaginated testPage;

    @BeforeEach
    void setUp() {
        Bank testBank = Bank.builder()
                .id(1L)
                .routingNumber("125000")
                .address("12345 MyStreet Ave")
                .city("MyCity")
                .state("Washington")
                .zipcode("55301")
                .build();

        branches = Arrays.asList(
                Branch.builder()
                        .id(1L)
                        .name("First Branch")
                        .address("1111 Main St")
                        .city("Denver")
                        .state("Colorado")
                        .zipcode("10101")
                        .phone("(555) 555-5551")
                        .bank(testBank)
                        .build(),
                Branch.builder()
                        .id(2L)
                        .name("Second Branch")
                        .address("999 Main St")
                        .city("Sacramento")
                        .state("California")
                        .zipcode("10101")
                        .phone("(555) 555-5550")
                        .bank(testBank)
                        .build(),
                Branch.builder()
                        .id(3L)
                        .name("Third Branch")
                        .address("9990 Main St")
                        .city("Austin")
                        .state("Texas")
                        .zipcode("10101")
                        .phone("(555) 555-5552")
                        .bank(testBank)
                        .build()
        );

        Pageable pageable = PageRequest.of(0, 2);
        testPage = new BranchPaginated(branches, pageable, 3);

    }

    @Test
    void getPaginatedBranches() {
        Pageable pageable = PageRequest.of(0, 2);

        when(branchRepository.findAll(pageable)).thenReturn(testPage);

        BranchPaginated myTestPage = branchService.getBranches(pageable);

        assertEquals(branches, myTestPage.getContent());
        assertEquals(3, myTestPage.getContent().size());
        assertEquals(2, myTestPage.getSize());
    }
}
