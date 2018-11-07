package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.repositories.ParticipationRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.services.ParticipationService;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    @RequestMapping(value = "/{questid}", method = RequestMethod.GET)
    public Participation getParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return participationService.findParticipationByQuestIdAndUserId(questid, user.getId());
    }

    @RequestMapping(value = "/{questid}/all", method = RequestMethod.GET)
    public List<Participation> getParticipations(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        return participationService.findParticipationByQuestId(questid);
    }

    @RequestMapping(value = "/{questid}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Participation createParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        final Optional<Quest> quest = questRepository.findById(questid);
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        if (quest.isPresent() && user != null) {
            Quest realQuest = quest.get();
            final Participation existingParticipation = participationRepository.findByQuestAndUser(realQuest, user);
            if (existingParticipation != null) {
                Participation participation = new Participation(realQuest, user);
                return participationRepository.save(participation);
            }
        }
        return null;
    }

    @RequestMapping(value = "/{questid}/{developerid}", method = RequestMethod.DELETE)
    public void deleteDeleteParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final Participation foundParticipation = participationService.findParticipationByQuestIdAndUserId(questid,
                user.getId());
        if (foundParticipation != null) {
            participationRepository.delete(foundParticipation);
        }
    }

}
