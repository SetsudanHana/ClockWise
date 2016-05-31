package clock.wise.service.impl;

import clock.wise.dao.StatisticDao;
import clock.wise.dao.UserDao;
import clock.wise.dto.StatisticDto;
import clock.wise.mapper.StatisticModelMapperWrapper;
import clock.wise.model.Statistic;
import clock.wise.model.User;
import clock.wise.service.StatisticService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService
{
    private final static Logger logger = Logger.getLogger( StatisticServiceImpl.class );

    @Autowired
    private StatisticDao statisticDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private StatisticModelMapperWrapper statisticModelMapperWrapper;

    @Override
    @Transactional
    public StatisticDto createOrUpdateStatistic( final StatisticDto statisticDto, final Long userId )
    {
        User user = userDao.findOne( userId );
        Statistic statistic = statisticModelMapperWrapper.getModelMapper().map( statisticDto, Statistic.class );

        statistic.setUser( user );
        Statistic saved = statisticDao.save( statistic );
        if ( statisticDao.exists( saved.getId() ) )
        {
            logger.info( "Statistics for user id: " + userId + " has been created" );
        }
        return statisticModelMapperWrapper.getModelMapper().map( saved, StatisticDto.class );
    }

    @Override
    @Transactional
    public List< StatisticDto > createOrUpdateStatistics( final List< StatisticDto > statistics, final Long userId )
    {
        List< StatisticDto > created = statistics.stream().map( statisticDto -> createOrUpdateStatistic( statisticDto, userId ) ).collect( Collectors.toList() );
        return created;
    }

    @Override
    @Transactional
    public List< StatisticDto > findStatisticsByUserId( final Long userId )
    {
        if ( userId == null )
        {
            throw new IllegalArgumentException( "User id cannot be null" );
        }

        List< StatisticDto > statisticsDtoList = new ArrayList<>();
        Iterable< Statistic > statistics = statisticDao.findByUserId( userId );
        convertStatisticsToDto( statisticsDtoList, statistics );

        return statisticsDtoList;
    }

    @Override
    @Transactional
    public List< StatisticDto > findStatsBetweenDates( final Date startDate, final Date endDate )
    {
        if ( startDate == null || endDate == null )
        {
            throw new IllegalArgumentException( "Dates cannot be null" );
        }

        List< StatisticDto > statisticsDtoList = new ArrayList<>();
        List< Statistic > statistics = statisticDao.findStatsBetweenDates( startDate, endDate );
        convertStatisticsToDto( statisticsDtoList, statistics );

        logger.info( "Stats between " + startDate + " and " + endDate + " received" );

        return statisticsDtoList;
    }

    @Override
    @Transactional
    public List< StatisticDto > findStatsBetweenDatesForUser( final Long userId, final Date startDate, final Date endDate )
    {
        if ( userId == null )
        {
            throw new IllegalArgumentException( "User id cannot be null" );
        }

        if ( startDate == null || endDate == null )
        {
            throw new IllegalArgumentException( "Dates cannot be null" );
        }

        List< StatisticDto > statisticsDtoList = new ArrayList<>();
        List< Statistic > statistics = statisticDao.findStatsBetweenDatesForUser( userId, startDate, endDate );
        convertStatisticsToDto( statisticsDtoList, statistics );

        logger.info( "Stats for user " + userId + ", between " + startDate + " and " + endDate + " received" );

        return statisticsDtoList;
    }

    private void convertStatisticsToDto( List< StatisticDto > statisticDtoList, Iterable< Statistic > statistics )
    {
        for ( final Statistic statistic : statistics )
        {
            StatisticDto statisticDto = statisticModelMapperWrapper.getModelMapper().map( statistic, StatisticDto.class );
            statisticDtoList.add( statisticDto );
        }
    }
}
