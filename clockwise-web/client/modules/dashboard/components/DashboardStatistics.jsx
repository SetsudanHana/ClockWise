import React, { Component } from 'react';

import NavBar from '../../core/components/NavBar.jsx';
import {FlowRouter} from 'meteor/kadira:flow-router-ssr';

import { Meteor } from 'meteor/meteor';
import { Session } from 'meteor/session';

import { LineChart } from 'rd3';

import { Grid, Col, Nav, NavItem, Button, Row } from 'react-bootstrap';

class DashboardStatistics extends Component {

	constructor(props) {
		super(props);

		this.state = {
			lineData: [],
			selectedTab: 1
		};
	}

	goToMainDashboard() {
		FlowRouter.go('/dashboard');
	}

	getMouseClickedCount() {
		let data = Session.get('user_statistics');

		let dates = _.pluck(data, 'date');
		let mouseClickedCounts = _.pluck(data, 'mouseClickedCount');

		let lineData = [];

		for(var i = 0; i<data.length; i++) {
			lineData.push({x: dates[i], y: mouseClickedCounts[i]});
		}

		let series = [{ 
                name: 'series',
                values: lineData
            }];

		this.setState({lineData: series});
	}

	getKeyboardClickedCount() {
		let data = Session.get('user_statistics');

		let dates = _.pluck(data, 'date');
		let counts = _.pluck(data, 'keyboardClickedCount');

		let lineData = [];

		for(var i = 0; i<data.length; i++) {
			lineData.push({x: dates[i], y: counts[i]});
		}

		let series = [{ 
                name: 'series',
                values: lineData
            }];

		this.setState({lineData: series});
	}

	getMouseMovement() {
		let data = Session.get('user_statistics');

		let dates = _.pluck(data, 'date');
		let counts = _.pluck(data, 'mouseMovementCount');

		let lineData = [];

		for(var i = 0; i<data.length; i++) {
			lineData.push({x: dates[i], y: counts[i]});
		}

		let series = [{ 
                name: 'series',
                values: lineData
            }];

		this.setState({lineData: series});
	}

	handleSelect(selectedKey) {
		
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
	             			<Nav bsStyle="tabs" justified stacked={false} onSelect={this.handleSelect}>
	             				<NavItem eventKey={1} onClick={this.getMouseClickedCount.bind(this)}>Mouse Clicked</NavItem>
	             				<NavItem eventKey={2} onClick={this.getKeyboardClickedCount.bind(this)}>Keyboard Clicked</NavItem>
	             				<NavItem eventKey={3} onClick={this.getMouseMovement.bind(this)}>Mouse Movement</NavItem>
	             			</Nav>
	             			<div className="charts">
	             				<LineChart
					                  data={this.state.lineData}
					                  width='100%'
					                  height={500}
					                  viewBoxObject={{
					                    x: 0,
					                    y: 0,
					                    width: 700,
					                    height: 500
					                  }}
					                  xAxisLabel="days"
					                  domain={{x: [,], y: [0,]}}
					                  gridHorizontal={true}
					                  xAccessor={(d)=> {
					                      return new Date(d.x);
					                    }     
					                  }
					                  yAccessor={(d)=>d.y}
					                  xAxisTickInterval={{unit: 'month', interval: 3}}
					                />
	             			</div>
             			</div>
             		</Col>
             	</Grid>
             </div>
		);
	}
}

export default DashboardStatistics;
