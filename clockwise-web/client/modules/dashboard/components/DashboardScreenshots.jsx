import React, {Component} from 'react';

import NavBar from '../../core/components/NavBar.jsx';
import {FlowRouter} from 'meteor/kadira:flow-router-ssr';

import {Meteor} from 'meteor/meteor';
import {Session} from 'meteor/session';

import {LineChart} from 'rd3';

import {Grid, Col, Nav, NavItem, Button, Row} from 'react-bootstrap';

class DashboardScreenshots extends Component {

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

    getScreenshots() {
        let data = Session.get('user_statistics_screenshots');

        let images = _.pluck(data, 'image');
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
                                <NavItem active={true}>Screenshots</NavItem>
                            </Nav>
                        </div>
                    </Col>
                    <Col sm={9}>
                        <div className="dashboard">
                            <div>
                                <img src="scr1.png"/>
                                <img src="scr2.png"/>
                                <img src="scr3.png"/>
                                <img src="scr4.png"/>
                                <img src="scr5.png"/>
                                <img src="scr6.png"/>
                            </div>
                        </div>
                    </Col>
                </Grid>
            </div>
        );
    }
}

export default DashboardScreenshots;
