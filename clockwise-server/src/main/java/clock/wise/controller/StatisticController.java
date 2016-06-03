package clock.wise.controller;

import clock.wise.dto.StatisticDto;
import clock.wise.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping( "/api/statistics" )
public class StatisticController
{
    @Autowired
    private StatisticService statisticService;

    @RequestMapping( method = RequestMethod.GET )
    public List< StatisticDto > findByDateBetween( @RequestParam( "start" ) @DateTimeFormat( iso = DateTimeFormat.ISO.DATE ) final Date startDate, @RequestParam( "end" ) @DateTimeFormat( iso = DateTimeFormat.ISO.DATE ) final Date endDate )
    {
        return statisticService.findByDateBetween(startDate, endDate);
    }

    @RequestMapping( value = "/users/{id}", method = RequestMethod.GET )
    public List< StatisticDto > findByUserIdAndDateBetween( @PathVariable( "id" ) final Long userId, @RequestParam( "start" ) @DateTimeFormat( iso = DateTimeFormat.ISO.DATE ) final Date startDate, @RequestParam( "end" ) @DateTimeFormat( iso = DateTimeFormat.ISO.DATE ) final Date endDate )
    {
        return statisticService.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
}
