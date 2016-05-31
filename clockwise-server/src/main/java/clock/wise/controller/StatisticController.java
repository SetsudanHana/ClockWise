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
    public List< StatisticDto > findStatsBetweenDates( @RequestParam( "start" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) final Date startDate, @RequestParam( "end" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) final Date endDate )
    {
        return statisticService.findStatsBetweenDates( startDate, endDate );
    }

    @RequestMapping( value = "/users/{id}", method = RequestMethod.GET )
    public List< StatisticDto > findStatsBetweenDatesForUser( @PathVariable( "id" ) final Long userId, @RequestParam( "start" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) final Date startDate, @RequestParam( "end" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) final Date endDate )
    {
        return statisticService.findStatsBetweenDatesForUser( userId, startDate, endDate );
    }
}
