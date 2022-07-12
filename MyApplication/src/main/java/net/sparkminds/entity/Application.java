package net.sparkminds.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
@Table(name="application")
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Email
	@Column(name="email_address")
	private String emailAdress;
	
	@Column(name="name")
	private String name;
	
	@Column(name="github_user")
	private String githubUser;
	
	@OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
	List<Project> project;
}
