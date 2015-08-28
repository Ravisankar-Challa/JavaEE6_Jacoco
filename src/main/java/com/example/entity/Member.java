package com.example.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Member implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MEMBER_SEQ")
	@SequenceGenerator(name="MEMBER_SEQ",sequenceName="test_seq",allocationSize=1,initialValue=1)
	private long id;
	
	@XmlElement(name="ravi")
	private String name;
	
	private int age;
	
	//@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public static class Builder {
		Member member = new Member();
		public static Builder newMember() {
			return new Builder();
		}
		
		public Builder name(String name) {
			this.member.name = name;
			return this;
		}
		public Builder age(int age) {
			this.member.age = age;
			return this;
		}
		public Builder dateOfBirth(Date dateOfBirth) {
			this.member.dateOfBirth = dateOfBirth;
			return this;
		}
		
		public Member build() {
			return this.member;
		}
	}
}
