package com.viadee.sonarquest.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.EventUserDto;
import com.viadee.sonarquest.entities.MessageDto;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.services.EventService;

@Controller
public class WebSocketController {

    private static final String CHAT = "/chat";

    @Autowired
    private SimpMessagingTemplate template;
    
    @Autowired
    private EventService eventService;

    @MessageMapping("/send/message")
    public void onReceivedMessage(final MessageDto messageDto) {
        Event event = eventService.createEventForNewMessage(messageDto);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
 
    
    
    
    public void onCreateQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForCreatedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onDeleteQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForDeletedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onUpdateQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForUpdatedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onSolveQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForSolvedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onUserJoinQuest(final Quest quest, Principal principal, User user) {
        Event event = eventService.createEventForUserJoinQuest(quest, principal, user);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    
    
    
    public void onCreateAdventure(final Adventure adventure, Principal principal) {
        Event event = eventService.createEventForCreatedAdventure(adventure, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onDeleteAdventure(final Adventure adventure, Principal principal) {
        Event event = eventService.createEventForDeletedAdventure(adventure, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend("/chat", eventUserDto);
    }
    
    
    
    
    public void onCreateArtefact(final Artefact artefact, Principal principal) {
        Event event = eventService.createEventForCreatedArtefact(artefact, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onDeleteArtefact(final Artefact artefact, Principal principal) {
        Event event = eventService.createEventForDeletedArtefact(artefact, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }

	public void onUpdateArtefact(Artefact artefact, Principal principal) {
        Event event = eventService.createEventForUpdatedArtefact(artefact, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
	}

}
