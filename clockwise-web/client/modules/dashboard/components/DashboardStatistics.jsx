import React, { Component } from 'react';

import BasicChart from './BasicChart.jsx';
import NavBar from '../../core/components/NavBar.jsx';
import {FlowRouter} from 'meteor/kadira:flow-router-ssr';

import { Meteor } from 'meteor/meteor';
import { Session } from 'meteor/session';

import { Grid, Col, Nav, NavItem, Button, Row } from 'react-bootstrap';

class DashboardStatistics extends Component {

	goToMainDashboard() {
		FlowRouter.go('/dashboard');
	}

	getStatistics() {
		let userInfo = Session.get('user_info');

		Meteor.call('statistics.user', userInfo.id, Session.get('user_token'), function(error, response) {
			if(error) {
				console.log("error", error);
			}

			console.log(response);

			Session.set('user_statistics', response);
		});

		return Session.get('user_statistics');
	}

	handleSelect(selectedKey) {
		let globalSelectedKey = selectedKey;
	}

	render() {
		return (
			<div>
            	<NavBar />
            	<Grid>
             		<Col sm={3}>
             			<div className="sidebar">
             				<Nav stacked={true}>
             					<NavItem onClick={this.goToMainDashboard.bind(this)}>Dashboard</NavItem>
             					<NavItem active={true}>Charts</NavItem>
             				</Nav>
             			</div>
             		</Col>
             		<Col sm={9}>
             			<div className="dashboard">
	             			<Row>
	             				<Button onClick={this.getStatistics.bind(this)}>Load Statistics</Button>
	             			</Row>
	             			<Nav bsStyle="tabs" justified stacked={false} onSelect={this.handleSelect}>
	             				<NavItem eventKey={1}>Mouse Clicked</NavItem>
	             				<NavItem eventKey={2}>Keyboard Clicked</NavItem>
	             				<NavItem eventKey={3}>Mouse Movement</NavItem>
	             			</Nav>
	             			<div className="charts">
	             				<BasicChart />
	             			</div>
             			</div>
             		</Col>
             	</Grid>
             </div>
		);
	}
}

export default DashboardStatistics;
