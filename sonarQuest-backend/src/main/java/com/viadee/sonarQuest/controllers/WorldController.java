package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.UserService;
import com.viadee.sonarQuest.services.WorldService;

@RestController
@RequestMapping("/world")
public class WorldController {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private WorldService worldService;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public World getCurrentWorld(final Principal principal) {
        final User user = userService.findByUsername(principal.getName());
        return user.getCurrentWorld();
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public World setCurrentWorld(final Principal principal, @RequestBody final World world) {
        final User user = userService.findByUsername(principal.getName());
        return userService.updateUsersCurrentWorld(user, world.getId());
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public List<World> getWorldsForUser(@PathVariable(value = "id") final Long id) {
        final User user = userService.findById(id);
        return user.getWorlds();
    }

    @RequestMapping(value = "/worlds", method = RequestMethod.GET)
    public List<World> getWorlds(final Principal principal) {
        final User user = userService.findByUsername(principal.getName());
        return user.getWorlds();
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<World> getAllWorlds(final Principal principal) {
        return worldService.findAll();
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public List<World> activeWorlds(final Principal principal) {
        return worldService.findAllActiveWorlds();
    }

    @RequestMapping(value = "/world/{id}", method = RequestMethod.GET)
    public World getWorldById(@PathVariable(value = "id") final Long id) {
        return worldRepository.findById(id).orElse(null);
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(value = "/world", method = RequestMethod.POST)
    public World updateWorld(@RequestBody final World worldToUpdate) {
        Optional<World> world = worldRepository.findById(worldToUpdate.getId());
        if (world.isPresent()) {
            World realWorld = world.get();
            realWorld.setName(worldToUpdate.getName());
            realWorld.setActive(worldToUpdate.getActive());
            return worldRepository.save(realWorld);
        }
        return null;
    }

    @PreAuthorize("hasAuthority('ACTIVE_WORLD_ACCESS')")
    @RequestMapping(value = "/world/{id}/image", method = RequestMethod.PUT)
    public World updateBackground(@PathVariable(value = "id") final Long worldId, @RequestBody final String image) {
        Optional<World> world = worldRepository.findById(worldId);
        if (world.isPresent()) {
            World realWorld = world.get();
            realWorld.setImage(image);
            return worldRepository.save(realWorld);
        }
        return null;
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public List<World> generateWorlds() {
        worldService.updateWorlds();
        return worldRepository.findAll();
    }

}
