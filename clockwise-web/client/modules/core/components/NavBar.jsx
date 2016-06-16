import React from 'react';
import {Navbar, Nav, NavItem, NavDropdown, MenuItem} from 'react-bootstrap';

import {FlowRouter} from 'meteor/kadira:flow-router-ssr';

class NavBar extends React.Component {
    signout() {
        Session.set('user_token', undefined);
        FlowRouter.go('/');
    }

    render() {
        return (
            <Navbar inverse>
                <Navbar.Header>
                    <Navbar.Brand>
                        <a href="#">Clockwise</a>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav pullRight>
                        <NavDropdown eventKey={3}>
                            <MenuItem eventKey={3.1}>Settings</MenuItem>
                            <MenuItem divider />
                            <MenuItem eventKey={3.2} onClick={this.signout.bind(this)}>Log Out</MenuItem>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
};

export default NavBar;