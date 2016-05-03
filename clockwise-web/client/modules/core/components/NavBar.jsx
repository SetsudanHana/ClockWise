import React from 'react';
import {Navbar, Nav, NavItem, NavDropdown, MenuItem} from 'react-bootstrap';

class NavBar extends React.Component {
    render() {
        return (
            <Navbar inverse>
                <Navbar.Header>
                    <Navbar.Brand>
                        <a href="#">TV Mainac</a>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <NavItem eventKey={1} href="#">Watchlist</NavItem>
                        <NavItem eventKey={2} href="#">Explore</NavItem>
                    </Nav>
                    <Nav pullRight>
                        <NavDropdown eventKey={3} title="Joe Doe">
                            <MenuItem eventKey={3.1}>Settings</MenuItem>
                            <MenuItem divider />
                            <MenuItem eventKey={3.2}>Log Out</MenuItem>
                        </NavDropdown>
                    </Nav>
                    <Nav pullRight>
                        <NavDropdown title="Notifications" >
                            <MenuItem eventKey="1">
                                <div> <strong>John Smith</strong> <span className="pull-right text-muted"> <em>Yesterday</em> </span> </div>
                                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit.Pellentesque eleifend...</div>

                            </MenuItem>
                            <MenuItem divider />
                            <MenuItem eventKey="2">
                                <div> <strong>John Smith</strong> <span className="pull-right text-muted"> <em>Yesterday</em> </span> </div>
                                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit.Pellentesque eleifend...</div>
                            </MenuItem>
                            <MenuItem divider />
                            <MenuItem eventKey="3">
                                <div> <strong>John Smith</strong> <span className="pull-right text-muted"> <em>Yesterday</em> </span> </div>
                                <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit.Pellentesque eleifend...</div>
                            </MenuItem>
                            <MenuItem divider />
                            <MenuItem eventKey="4">
                                <strong>Read All Messages</strong> <i className="fa fa-angle-right"></i>
                            </MenuItem>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
};

export default NavBar;