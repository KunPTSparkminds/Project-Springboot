package net.sparkminds.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import net.sparkminds.entity.enumeration.Capacity;
import net.sparkminds.entity.enumeration.Mode;

@Entity
@Data
@Table(name="project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name_project")
	private String nameProject;
	
	@Column(name="employment_mode")
	@Enumerated(EnumType.STRING)
	private Mode employmentMode;
	
	@Column(name="capacity")
	@Enumerated(EnumType.STRING)
	private Capacity capacity;
	
	@Column(name="duration")
	private String duration;
	
	@Column(name="start_year")
	private Date startYear;
	
	@Column(name="role")
	private String role;
	
	@Column(name="team_size")
	private Long teamSize;
	
	@Column(name="link_to_repo", nullable = true)
	private String linkToRepo;
	
	@Column(name="link_to_live_url", nullable = true)
	private String linkToLiveUrl;
	
	@Column(name="is_deleted", columnDefinition = "boolean default true")
	private Boolean isDeleted = false;
	
	@ManyToOne
	@JoinColumn(name = "application_id", referencedColumnName="id")
    private Application application;
}
