package com.aline.bankmicroservice.service;

import com.aline.core.model.Branch;
import com.aline.core.paginated.BranchPaginated;
import com.aline.core.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepository branchRepository;

    public BranchPaginated getBranches(Pageable pageable) {
        Page<Branch> branchPage = branchRepository.findAll(pageable);
        return new BranchPaginated(branchPage.getContent(), pageable, branchPage.getTotalElements());
    }

}
