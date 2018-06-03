package com.viadee.sonarQuest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.dtos.AdventureDto;
import com.viadee.sonarQuest.dtos.ArtefactDto;
import com.viadee.sonarQuest.dtos.DeveloperDto;
import com.viadee.sonarQuest.dtos.UiDesignDto;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.UiDesign;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.UiDesignRepository;
import com.viadee.sonarQuest.services.UiDesignService;

@RestController
@RequestMapping("/ui")
public class UiDesignController {
	
	@Autowired
	private UiDesignService uiDesignService;
	
	@Autowired 
	private DeveloperRepository developerRepository;
	
	@Autowired 
	private UiDesignRepository uiDesignRepository;

	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public UiDesignDto getUiDesignById(@PathVariable(value = "id") Long developer_id){
		Developer developer = this.developerRepository.findById(developer_id);
		UiDesign ui = this.uiDesignRepository.findByDeveloper(developer);
		
		UiDesignDto uiDto = null;
        if (ui != null) {
        	uiDto = this.uiDesignService.toUiDesignDto(ui);
        } else {
        	uiDto = this.uiDesignService.createUiDesign(developer, "light");
        }
        return uiDto;
    }
	
	@CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public UiDesignDto updateAdventure(@PathVariable(value = "id") Long developer_id, @RequestBody String designName) {
		Developer developer = this.developerRepository.findById(developer_id);
		UiDesign ui = this.uiDesignRepository.findByDeveloper(developer);
		
        return this.uiDesignService.updateUiDesign(ui, developer, designName);
    }
}