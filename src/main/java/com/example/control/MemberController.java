package com.example.control;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.example.boundary.MemberService;
import com.example.entity.Member;

@ApplicationScoped
public class MemberController {

	@Inject
	Logger LOG;
	
	@Inject
	MemberService memberService;
	
	public MemberController() {
		//System.out.println("Inside MemberController Contructor");
	}
	
	@PostConstruct
	public void setup() {
		//LOG.warning("Inside MemberController @PostConstruct");
	}
	
	public long addMember(Member m) {
		return memberService.addMember(m);
	}
	
	public Member readMember(long id) {
		return memberService.readMember(id);
	}
	
}
