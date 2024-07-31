package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.Model.Member;
import com.example.demo.Repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member save(Member member) {
        return memberRepository.save(member);
    }
    
    public List<Member> saveAll(List<Member> members) {
        return memberRepository.saveAll(members);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public Page findAll(Specification<Member> spec, Pageable pageable) {
        return memberRepository.findAll(spec, pageable);
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}