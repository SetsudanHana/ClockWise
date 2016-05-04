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
    public StatisticDto createOrUpdate( final StatisticDto statisticDto )
    {
        Statistic statistic = statisticModelMapperWrapper.getModelMapper().map( statisticDto, Statistic.class );
        statistic.getUser().setStatistic( statistic );

        Statistic saved = statisticDao.save( statistic );
        if ( statisticDao.exists( saved.getId() ) )
        {
            logger.info( "Statistics for user id: " + saved.getUser().getId() + " has been created" );
        }
        return statisticModelMapperWrapper.getModelMapper().map( saved, StatisticDto.class );
    }

    @Override
    @Transactional
    public StatisticDto findStatisticByUserId( final Long userId )
    {
        if ( userId == null )
        {
            throw new IllegalArgumentException( "User id cannot be null" );
        }

        Statistic statistic = statisticDao.findOneByUserId( userId );

        return statisticModelMapperWrapper.getModelMapper().map( statistic, StatisticDto.class );
    }

    @Override
    @Transactional
    public StatisticDto updateUserStatistics( final Long userId, final StatisticDto statisticDto )
    {
        User user = userDao.findOne( userId );
        Statistic statistic = user.getStatistic();
        if ( statistic == null )
        {
            statistic = new Statistic();
            statistic.setUser( user );
            statistic.getUser().setStatistic( statistic );
            statisticDao.save( statistic );
        }

        statistic.setMouseClickedCount( statisticDto.getMouseClickedCount() );
        statistic.setKeyboardClickedCount( statisticDto.getKeyboardClickedCount() );
        userDao.save( user );

        return statisticModelMapperWrapper.getModelMapper().map( statistic, StatisticDto.class );
    }
}
