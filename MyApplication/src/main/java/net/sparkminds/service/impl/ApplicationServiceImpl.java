package net.sparkminds.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sparkminds.entity.Application;
import net.sparkminds.entity.Project;
import net.sparkminds.repository.ApplicationRepository;
import net.sparkminds.repository.ProjectRepository;
import net.sparkminds.service.ApplicationService;
import net.sparkminds.service.dto.request.ApplicationRequestDTO;
import net.sparkminds.service.dto.response.ApplicationResponseDTO;
import net.sparkminds.service.dto.response.ProjectResponseDTO;

@Service
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<ApplicationResponseDTO> getAllApplication() {
        // TODO Auto-generated method stub
        return applicationRepository.getApplication().stream().map(entity -> {
            List<ProjectResponseDTO> pasProjects = entity.getProject().stream().map(otp -> {
                return ProjectResponseDTO.builder().nameProject(otp.getNameProject())
                        .employmentMode(otp.getEmploymentMode()).capacity(otp.getCapacity()).duration(otp.getDuration())
                        .startYear(otp.getStartYear()).teamSize(otp.getTeamSize()).linkToRepo(otp.getLinkToRepo())
                        .linkToLiveUrl(otp.getLinkToLiveUrl()).role(otp.getRole()).build();
            }).collect(Collectors.toList());
            return ApplicationResponseDTO.builder().name(entity.getName()).emailAdress(entity.getEmailAdress())
                    .githubUser(entity.getGithubUser()).projects(pasProjects).build();
        }).collect(Collectors.toList());

    }

    @Override
    public List<ApplicationResponseDTO> getApplicationById(Long id) {
        return applicationRepository.findById(id).stream().map(entity -> {
            List<ProjectResponseDTO> pasProjects = entity.getProject().stream().map(otp -> {

                return ProjectResponseDTO.builder().nameProject(otp.getNameProject()).role(otp.getRole()).build();
            }).collect(Collectors.toList());
            return ApplicationResponseDTO.builder().name(entity.getName()).emailAdress(entity.getEmailAdress())
                    .githubUser(entity.getGithubUser()).projects(pasProjects).build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO applicationRequestDTO) {
        Application application = applicationRepository.findByEmailAdress(applicationRequestDTO.getEmailAdress())
                .orElse(new Application());
        if (application != null) {
//            List<Project> projects = projectRepository.findByApplicationId(application.getId());
            projectRepository.deleteByApplicationId(application.getId());
        }

        application.setEmailAdress(applicationRequestDTO.getEmailAdress());
        application.setGithubUser(applicationRequestDTO.getGithubUser());
        application.setName(applicationRequestDTO.getName());
        applicationRepository.save(application);
        return ApplicationResponseDTO.builder().name(application.getName()).emailAdress(application.getEmailAdress())
                .githubUser(application.getGithubUser()).build();
    }
}
