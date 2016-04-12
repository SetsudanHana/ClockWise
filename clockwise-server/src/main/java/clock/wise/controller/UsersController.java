package clock.wise.controller;

import clock.wise.converter.UserModelConverter;
import clock.wise.dtos.UserDto;
import clock.wise.model.User;
import clock.wise.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    @Autowired
    UserService userService;

    ModelMapper modelMapper = new ModelMapper();

    {
        modelMapper.addConverter(new UserModelConverter());
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    UserDto create(@RequestBody UserDto user) {
        User newUser = userService.createUser(user);
        return modelMapper.map(newUser, UserDto.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_TEAM_LEADER', 'ROLE_DEVELOPER')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public
    @ResponseBody
    UserDto currentUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(currentUser, UserDto.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_TEAM_LEADER', 'ROLE_DEVELOPER')")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public
    @ResponseBody
    UserDto update(@RequestBody UserDto user) {
        User updatedUser = userService.updateUser(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_TEAM_LEADER', 'ROLE_DEVELOPER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    UserDto remove(@PathVariable long id) {
        User removedUser = userService.removeUser(id);
        return modelMapper.map(removedUser, UserDto.class);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_TEAM_LEADER', 'ROLE_DEVELOPER')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    UserDto get(@PathVariable long id) {
        User getUser = userService.getUser(id);
        return modelMapper.map(getUser, UserDto.class);
    }
}
