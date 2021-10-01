package com.aline.bankmicroservice.service;

import com.aline.bankmicroservice.dto.request.MemberSearchCriteria;
import com.aline.bankmicroservice.dto.response.MemberResponse;
import com.aline.bankmicroservice.specification.MemberSpecification;
import com.aline.core.model.Member;
import com.aline.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service("Member Service")
@RequiredArgsConstructor
@Slf4j(topic = "Member Service")
public class MemberService {
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    /**
     * Searches Database to find members based on Name, Membership ID/Application ID,
     * account status, min/max amounts in accounts, if Members are Primary account holders,
     * if member has checking or savings accounts
     *
     * @param searchCriteria Criteria object that holds the searchable parameters
     * @param pageable       Pageable object
     * @return MemberResponseDTO Paginated response of MemberDTOs
     */
    @PreAuthorize("hasAnyAuthority({'employee', 'administrator'})")
    public Page<MemberResponse> searchMembers(MemberSearchCriteria searchCriteria, Pageable pageable) {
        Specification<Member> specs = Specification.where(MemberSpecification.memberSearch(searchCriteria));
        return memberRepository.findAll(specs, pageable)
                .map(member -> modelMapper.map(member, MemberResponse.class));
    }

}
