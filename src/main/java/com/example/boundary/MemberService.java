package com.example.boundary;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.example.entity.Member;

@Stateless
public class MemberService {
	
	@PersistenceContext
	EntityManager em;
	
	public MemberService() {
		//System.out.println("Inside  MemberService Constructor");
	}

	@PostConstruct
	public void setup() {
		//System.out.println("Inside  MemberService @PostConstruct");
	}
	
	public long addMember(Member m) {
		em.persist(m);
		return m.getId();
	}
	
	public Member readMember(long id) {
		return em.find(Member.class, id);
	}
	
}
