package com.example.demo.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Member;
import com.example.demo.Service.MemberService;

import jakarta.persistence.criteria.Predicate;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberService.save(member);
    }
    @PostMapping("/batch")
    public List<Member> createMembers(@RequestBody List<Member> members) {
        return memberService.saveAll(members);
    }

    @GetMapping("/{id}")
    public Optional<Member> getMemberById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @GetMapping
    public Page<Member> getMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal balance,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Specification<Member> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if (balance != null) {
                predicates.add(cb.equal(root.get("balance"), balance));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return memberService.findAll(spec, pageable);
    }

    @PutMapping("/{id}")
    public Optional<Member> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        return memberService.findById(id).map(member -> {
            member.setName(memberDetails.getName());
            member.setBalance(memberDetails.getBalance());
            member.setBirthDate(memberDetails.getBirthDate());
            member.setStatus(memberDetails.getStatus());
            return memberService.save(member);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteById(id);
    }
}
