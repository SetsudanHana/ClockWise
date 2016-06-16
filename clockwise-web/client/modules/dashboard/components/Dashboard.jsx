import React, { Component } from 'react';

import { Meteor } from 'meteor/meteor';

import { Session } from 'meteor/session';

import NavBar from '../../core/components/NavBar.jsx';
import {FlowRouter} from 'meteor/kadira:flow-router-ssr';

import { Grid, Col, Nav, NavItem, Panel } from 'react-bootstrap';

class Dashboard extends Component {

      goToCharts() {
            let userInfo = Session.get('user_info');
            Meteor.call('statistics.user', userInfo.id, Session.get('user_token'), function(error, response) {
                  if(error) {
                        console.log("error", error);

                        FlowRouter.go('/');
                  }

                  Session.set('user_statistics', response.data);

                  FlowRouter.go('/statistics');
            });
      }

	render() {
		return (
			<div>
            	<NavBar />
            	<Grid>
             		<Col sm={3}>
             			<div className="sidebar">
             				<Nav stacked={true}>
             					<NavItem active={true}>Dashboard</NavItem>
             					<NavItem onClick={this.goToCharts.bind(this)}>Charts</NavItem>
             				</Nav>
             			</div>
             		</Col>
             		<Col sm={9}>
             			<div className="dashboard">
                                    <Panel header={Dashboard} bsStyle="success">
                                          Panel content
                                    </Panel>
             			</div>
             		</Col>
             	</Grid>
             </div>
		);
	}
}

export default Dashboard;