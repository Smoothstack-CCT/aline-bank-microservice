package com.aline.bankmicroservice.controller;

import com.aline.bankmicroservice.service.BranchService;
import com.aline.core.paginated.BranchPaginated;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/branches")
@Slf4j(topic = "branch Controller")
@AllArgsConstructor
@Api(value = "/branches")
public class BranchController {

    private final BranchService branchService;
    private static final int DEFAULT_PAGE_SIZE = 15;

    @GetMapping("")
    public ResponseEntity<BranchPaginated> getBranches(
            @PageableDefault(size = DEFAULT_PAGE_SIZE) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {

        BranchPaginated branches = branchService.getBranches(pageable);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(branches);
    }

}
